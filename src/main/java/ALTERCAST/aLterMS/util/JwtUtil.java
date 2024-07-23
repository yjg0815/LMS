package ALTERCAST.aLterMS.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String USER_ID = "userId";
    private static final String IS_ADMIN = "isAdmin";
    private static final int MINUTES_TO_MILLISECONDS = 60 * 1000;

    private final SecretKey secretKey;
    private final long expirationMinutes;

    public JwtUtil(@Value("${auth.jwt.secret-key}") final String secretKey,
                   @Value("${auth.jwt.expiration-minutes}") final long expirationMinutes) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    public String createToken(final String accountId, final boolean isAdmin) {
        Date now = new Date();
        return Jwts.builder()
                .claim(USER_ID, accountId)
                .claim(IS_ADMIN, isAdmin)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMinutes * MINUTES_TO_MILLISECONDS))
                .signWith(secretKey)
                .compact();
    }

    public AccessAccount extractAccountData(final String token) {
        try {
            final Claims payload = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            final String userId = payload.get(USER_ID, String.class);
            final Boolean isAdmin = payload.get(IS_ADMIN, Boolean.class);
            return new AccessAccount(userId, isAdmin);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("만료된 토큰입니다");
        }
        //todo: 예외처리
    }
}
