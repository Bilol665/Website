package uz.bilol.website.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.bilol.website.exception.NotAcceptableException;
import uz.bilol.website.service.user.AuthenticationService;
import uz.bilol.website.service.user.JwtService;

import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
public class JwtFilterToken extends OncePerRequestFilter {
    private JwtService jwtService;
    private AuthenticationService authenticationService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(7);
        Jws<Claims> claims = jwtService.extractToken(token);
        Date expiration = claims.getBody().getExpiration();
        if(expiration.before(new Date())){throw new NotAcceptableException("Token is expired");}
        authenticationService.Authenticate(claims.getBody(),request);

        filterChain.doFilter(request, response);
    }
}
