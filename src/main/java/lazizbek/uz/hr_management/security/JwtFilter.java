package lazizbek.uz.hr_management.security;


import lazizbek.uz.hr_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;

    @Lazy
    @Autowired
    AuthService authService;
    public String username = "";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // GET TOKEN FROM REQUEST
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {

            // SUBSTRING BEARER WORD FROM TOKEN
            token = token.substring(7);

            // VALIDATE TOKEN
            boolean validateToken = jwtProvider.validateToken(token);

            if (validateToken) {
                // GET USERNAME FROM TOKEN
                username = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = authService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

