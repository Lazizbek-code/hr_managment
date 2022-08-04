package lazizbek.uz.hr_management.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lazizbek.uz.hr_management.entity.Role;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private long expireTime = 10_000_000l;
    private String secretKey = "Hech kim bilmasin!";

    public String generateToken(String username, Set<Role> roles){
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Date expiration = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
