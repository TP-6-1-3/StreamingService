package ru.vsu.csf.asashina.musicmanBack.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.vsu.csf.asashina.musicmanBack.filter.AuthenticationFilter;

import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Role.ADMIN;
import static ru.vsu.csf.asashina.musicmanBack.model.constant.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration implements WebMvcConfigurer {

    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(GET, "/v3/api-docs/**", "/songs/**", "/swagger-ui/**",
                        "/swagger-ui.html", "/singers/**", "/genres/**", "/actuator/**").permitAll()

                .requestMatchers(GET, "/songs/*/file", "/library/**", "/playlists/**",
                        "/history", "/friends", "/recommendations", "/auth/credentials").hasAnyAuthority(USER)
                .requestMatchers(POST, "/auth/resend-code", "/library/*",
                        "/playlists/**", "/friends/*", "/recommendations/**",
                        "/auth/profile/photo").hasAnyAuthority(USER)
                .requestMatchers(PUT, "/playlists/*", "/auth/profile").hasAnyAuthority(USER)
                .requestMatchers(DELETE, "/library/*", "/playlists/**", "/friends/*").hasAnyAuthority(USER)

                .requestMatchers(GET, "/statistics/*").hasAnyAuthority(ADMIN)
                .requestMatchers(POST, "/songs/**", "/singers", "/genres").hasAnyAuthority(ADMIN)
                .requestMatchers(PUT, "/singers/*").hasAnyAuthority(ADMIN)
                .requestMatchers(DELETE, "/songs/*", "/singers/*", "/genres/*",
                        "/auth/*").hasAnyAuthority(ADMIN)

                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
