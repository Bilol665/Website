package uz.bilol.website.service.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import uz.bilol.website.domain.entity.user.UserEntity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access.expiry}")
    private long accessTokenExpiry;

    @Value("${jwt.refresh.expiry}")
    private long refreshTokenExpiry;

    /**
     * Generates an access token for the given user entity.
     *
     * @param userEntity The user entity to generate the token for.
     * @return A JWT access token as a String.
     */
    public String generateAccessToken(UserEntity userEntity) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiry);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .addClaims(Map.of("roles", getRoles(userEntity.getAuthorities())))
                .compact();
    }

    /**
     * Generates a refresh token for the given user entity.
     *
     * @param userEntity The user entity to generate the token for.
     * @return A JWT refresh token as a String.
     */
    public String generateRefreshToken(UserEntity userEntity) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpiry);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }

    /**
     * Extracts roles from the user's authorities.
     *
     * @param roles The collection of GrantedAuthority objects representing user roles.
     * @return A list of role names as strings.
     */
    public List<String> getRoles(Collection<? extends GrantedAuthority> roles) {
        return roles.stream()
                .map(GrantedAuthority::getAuthority)
                .toList(); // Use toList() from Java 16+
    }

    /**
     * Extracts claims from a JWT token.
     *
     * @param token The JWT token as a string.
     * @return Parsed Jws<Claims> object.
     */
    public Jws<Claims> extractToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }
}
