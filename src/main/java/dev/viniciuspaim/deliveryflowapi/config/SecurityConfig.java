package dev.viniciuspaim.deliveryflowapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers("/actuator/health", "/actuator/prometheus").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                // Guest read-only access
                .requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("GUEST", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/restaurants/**").hasAnyRole("GUEST", "ADMIN")

                // Admin only - Products
                .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                // Admin only - Restaurants
                .requestMatchers(HttpMethod.POST, "/restaurants/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/restaurants/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/restaurants/**").hasRole("ADMIN")

                // Admin only - Customers & Orders
                .requestMatchers("/customers/**").hasRole("ADMIN")
                .requestMatchers("/orders/**").hasRole("ADMIN")

                // Require auth for all other endpoints
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {});

        return http.build();
    }
}
