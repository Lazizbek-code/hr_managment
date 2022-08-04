package lazizbek.uz.hr_management.service;

import lazizbek.uz.hr_management.entity.Role;
import lazizbek.uz.hr_management.entity.Turnstile;
import lazizbek.uz.hr_management.entity.User;
import lazizbek.uz.hr_management.entity.enums.RoleName;
import lazizbek.uz.hr_management.payload.ApiResponse;
import lazizbek.uz.hr_management.payload.LoginDto;
import lazizbek.uz.hr_management.payload.RegisterDto;
import lazizbek.uz.hr_management.payload.UserEditorDto;
import lazizbek.uz.hr_management.repository.RoleRepository;
import lazizbek.uz.hr_management.repository.TurnstileRepository;
import lazizbek.uz.hr_management.repository.UserRepository;
import lazizbek.uz.hr_management.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    TurnstileRepository turnstileRepository;
    @Autowired
    JavaMailSender javaMailSender;


    public ApiResponse register(RegisterDto registerDto) {

        // tizimda shunday email yoqligini tekshiradi
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("This Email already exist", false);
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setSalary(registerDto.getSalary());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmailCode(UUID.randomUUID().toString());

        // shunday role bor yoqligini tekshiradi
        Optional<Role> optionalRole = roleRepository.findById(registerDto.getRoleId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found", false);
        }
        Role signUpRole = optionalRole.get();

        // role direktor emasligini tekshiradi (faqat bitta direktor bo'ladi)
        if (signUpRole.getRoleName().equals(RoleName.DIRECTOR)) {
            return new ApiResponse("Director already exist", false);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userInSystem = (User) authentication.getPrincipal();

        // qo'shilayotgan userning roli HR_MANAGER bo'lsa
        if (signUpRole.getRoleName().equals(RoleName.HR_MANAGER)) {
            boolean roleIsDirector = false;

            // qo'shayotgan user DIRECTOR ligini tekshiradi
            for (Role role : userInSystem.getRoles()) {
                if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                    user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.HR_MANAGER)));
                    roleIsDirector = true;
                    break;
                }
            }

            if (!roleIsDirector) {
                return new ApiResponse("Only DIRECTOR can add HR_MANAGER", false);
            }
        } else { // qo'shilayotgan userning roli EMPLOYEE bo'lsa
            boolean roleIsEmployee = false;

            // qo'shayotgan user EMPLOYEE emasligligini tekshiradi
            for (Role role : userInSystem.getRoles()) {
                if (role.getRoleName().equals(RoleName.EMPLOYEE)) {
                    roleIsEmployee = true;
                    break;
                }
            }

            if (roleIsEmployee) {
                return new ApiResponse("EMPLOYEE can not add Users", false);
            } else {
                user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.EMPLOYEE)));
            }
        }
        User savedUser = userRepository.save(user);

        // send message to email
        sendEmail(savedUser.getEmail(), savedUser.getEmailCode());
        return new ApiResponse("Successfully registered, verify your account", true);
    }


    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()));

            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getUsername(), user.getRoles());

            Turnstile turniket = new Turnstile();
            turniket.setUser(user);
            turniket.setActive(true);
            turnstileRepository.save(turniket);

            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException e) {
            return new ApiResponse("Password or login is incorrect", false);
        }
    }


    public ApiResponse verifyAccount(String email, String emailCode, LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
            userRepository.save(user);

            Turnstile turnstile = new Turnstile();
            turnstile.setUser(user);
            turnstile.setActive(true);
            turnstileRepository.save(turnstile);

            return new ApiResponse("Account is verified", true);
        }
        return new ApiResponse("Account already verified", false);
    }


    public ApiResponse logout() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User user = (User) authentication.getPrincipal();

            Optional<Turnstile> optionalTurnstile = turnstileRepository.findByUserId(user.getId());
            if (!optionalTurnstile.isPresent()) {
                return new ApiResponse("Tunuket not found", false);
            }
            Turnstile turnstile = optionalTurnstile.get();
            turnstile.setActive(false);
            turnstileRepository.save(turnstile);

            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return new ApiResponse("Logged out", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    public ApiResponse edit(UserEditorDto userEditorDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User editedUser = optionalUser.get();
            editedUser.setFirstName(userEditorDto.getFirstName());
            editedUser.setLastName(userEditorDto.getLastName());
            editedUser.setPassword(passwordEncoder.encode(userEditorDto.getPassword()));
            userRepository.save(editedUser);
            return new ApiResponse("Successfully edited", true);
        } else {
            return new ApiResponse("Error", false);
        }
    }

    public void sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("email.sender.hr@gmail.com");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setSubject("Verify account");
            simpleMailMessage.setText("http://localhost:8050/api/auth/verifyAccount?emailCode=" + emailCode + "&email=" + sendingEmail);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
