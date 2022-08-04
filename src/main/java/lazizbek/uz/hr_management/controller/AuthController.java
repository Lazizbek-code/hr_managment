package lazizbek.uz.hr_management.controller;

import lazizbek.uz.hr_management.payload.ApiResponse;
import lazizbek.uz.hr_management.payload.LoginDto;
import lazizbek.uz.hr_management.payload.RegisterDto;
import lazizbek.uz.hr_management.payload.UserEditorDto;
import lazizbek.uz.hr_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;



    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.register(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201: 409).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PostMapping("/verifyAccount")
    public ResponseEntity<?> verify(@RequestParam String emailCode, @RequestParam String email, @RequestBody LoginDto loginDto) {
        ApiResponse apiResponse = authService.verifyAccount(email, emailCode, loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        ApiResponse apiResponse = authService.logout();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody UserEditorDto userEditorDto) {
        ApiResponse apiResponse = authService.edit(userEditorDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
