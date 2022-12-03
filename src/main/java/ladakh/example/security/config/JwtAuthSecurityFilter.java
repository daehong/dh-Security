package ladakh.example.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ladakh.example.security.auth.JwtTokenProvider;
import ladakh.example.security.rest.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Slf4j
public class JwtAuthSecurityFilter extends BasicAuthenticationFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthSecurityFilter(AuthenticationManager manager, JwtTokenProvider jwtToken) {
        super(manager);
        this.jwtTokenProvider = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        log.info("Jwt Token: {}", token);

        if (token != null) {
            jwtTokenProvider.validateToken(token);
            User authUser = jwtTokenProvider.getAuthentication(token);

            Collection<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    authUser.getId(), null, authorities
            );

            log.info("인증 {}", auth.getPrincipal());
            log.info("인증 {}", auth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
