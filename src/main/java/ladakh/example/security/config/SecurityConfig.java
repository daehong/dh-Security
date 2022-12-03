package ladakh.example.security.config;

import ladakh.example.security.auth.JwtTokenProvider;
import ladakh.example.security.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final AuthenticationConfiguration config;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtToken) throws Exception {
        return http.cors().disable()
                .csrf().disable()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login/**").permitAll();
                    auth.requestMatchers("/api/*").hasAnyRole("ROLE_USER", "USER");
                    auth.requestMatchers("/api/**").hasAuthority("abc");
                    auth.anyRequest().authenticated();
                })
                .formLogin().disable()
                .sessionManagement().disable()
                .addFilter(new JwtAuthSecurityFilter(auth(config), jwtToken))
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(Customizer.withDefaults())
                .build();

    }

//    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        return new ProviderManager(authProvider);
    }

    @Bean
    public AuthenticationManager auth(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtTokenProvider jwtToken() {
        return new JwtTokenProvider();
    }
}
