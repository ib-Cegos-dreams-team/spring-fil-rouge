package fr.pafz.spring.ittraining.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtConst.JWT_HEADER);

        if (jwt != null && jwt.startsWith("Bearer ")) {
            try {
                // Extract token without assuming its validity initially
                String token = jwt.substring(7);

                // Use jjwt for robust validation and exception handling
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(JwtConst.JWT_SECRET.getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String email = String.valueOf(claims.get("email"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);

                // Set authentication only if token is valid
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (MalformedJwtException ex) {
                logger.error("Invalid JWT structure: {}");
                throw new BadCredentialsException("Invalid token structure");
            } catch (SignatureException ex) {
                logger.error("Invalid JWT signature: {}");
                throw new BadCredentialsException("Invalid token signature");
            } catch (ExpiredJwtException ex) {
                logger.error("Expired JWT: {}");
                throw new BadCredentialsException("Token expired");
            } catch (UnsupportedJwtException ex) {
                logger.error("Unsupported JWT format: {}");
                throw new BadCredentialsException("Unsupported JWT format");
            } catch (IllegalArgumentException ex) {
                logger.error("JWT claims issue: {}");
                throw new BadCredentialsException("Invalid JWT claims");
            } catch (Exception ex) {
                logger.error("JWT validation error: {}");
                throw new RuntimeException("JWT validation failed"); // Consider a more specific exception
            }
        }

        // Allow other filters to process the request even if no token is present
        filterChain.doFilter(request, response);
    }
}
