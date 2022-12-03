package ladakh.example.security.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import ladakh.example.security.rest.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtTokenProvider {

    private String issuer = "Ladakh";
    private String secretKey = "ae23242e4b0646df2690b9761beb9a3829b15c16827";
    private long expireTime = 3600;

    public String generateToken(User user) {
        LocalDateTime now = LocalDateTime.now();

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getId())
                .withClaim("name", user.getName())
                .withIssuedAt(Date.from(Instant.now())) // 토큰 발급시간
                .withExpiresAt(Date.from(Instant.now().plusSeconds(expireTime))) // 토큰만료시간
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String generateToken(String userId, String role) {
        LocalDateTime now = LocalDateTime.now();

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userId)
                .withClaim("roles", role)
                .withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) // 토큰 발급시간
                .withExpiresAt(Date.from(now.plusSeconds(expireTime).atZone(ZoneId.systemDefault()).toInstant())) // 토큰만료시간
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void validateToken(String token) {
        JWT.require(Algorithm.HMAC512(secretKey)).withIssuer(issuer).build().verify(token);
    }

   public User getAuthentication(String token) {
        DecodedJWT jwt = JWT.decode(token);

        User user = new User();
        user.setId(jwt.getSubject());
        return user;
    }

}
