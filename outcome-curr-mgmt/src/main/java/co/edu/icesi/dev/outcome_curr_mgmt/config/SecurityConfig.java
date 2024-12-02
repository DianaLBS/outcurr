package co.edu.icesi.dev.outcome_curr_mgmt.config;

import co.edu.icesi.dev.outcome_curr_mgmt.saamfi.filters.SaamfiAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.http.HttpHeaders;
import java.util.Collections;
import java.util.regex.Pattern;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final SaamfiAuthenticationFilter saamfiAuthenticationFilter;

    public SecurityConfig(SaamfiAuthenticationFilter saamfiAuthenticationFilter) {
        this.saamfiAuthenticationFilter = saamfiAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        String contextPath = "/actuator/";

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(new AntPathRequestMatcher(contextPath + "/actuator/**")).permitAll());

        http.authorizeHttpRequests(
                authz -> authz.requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll());
        http.authorizeHttpRequests(
                authz -> authz.requestMatchers(new AntPathRequestMatcher("/outcurrapi/**")).permitAll());
        http.authorizeHttpRequests(
                authz -> authz.requestMatchers(new AntPathRequestMatcher(contextPath + "/h2-console/**")).permitAll());
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(new AntPathRequestMatcher(contextPath + "/v1/auth/users/login")).permitAll());
        http.authorizeHttpRequests(
                authz -> authz.requestMatchers(new AntPathRequestMatcher(contextPath + "/swagger-ui/**")).permitAll());
        http.authorizeHttpRequests(
                authz -> authz.requestMatchers(new AntPathRequestMatcher(contextPath + "/v3/api-docs/**")).permitAll());

        http.authorizeHttpRequests(
                authz -> authz.requestMatchers(new AntPathRequestMatcher(contextPath + "/v1/**")).permitAll());
        http.authorizeHttpRequests(
                authz -> authz.requestMatchers(new AntPathRequestMatcher(contextPath + "/**")).permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(saamfiAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            String origin = request.getHeader(HttpHeaders.ORIGIN);
            Pattern pattern = Pattern.compile("https://([A-Za-z0-9-]+)\\.jcmunoz\\.net");
            CorsConfiguration configuration = new CorsConfiguration();

            if (origin == null) {
                return configuration;
            }

            if (!pattern.matcher(origin).matches()) {
                return configuration;
            }

            configuration.setAllowedOrigins(Collections.singletonList(origin));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            return configuration;

        };
    }

}
