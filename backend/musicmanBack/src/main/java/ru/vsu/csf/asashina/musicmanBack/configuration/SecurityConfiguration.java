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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.vsu.csf.asashina.musicmanBack.filter.AuthenticationFilter;

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
                        "/swagger-ui.html", "/singer/**", "/genres/**").permitAll()

                .requestMatchers(GET, "/songs/*/file", "/library/**", "/playlists/**").hasAnyAuthority(USER)
                .requestMatchers(POST, "/auth/resend-code", "/library/*",
                        "/playlists/**").hasAnyAuthority(USER)
                .requestMatchers(DELETE, "/library/*").hasAnyAuthority(USER)

                .requestMatchers(POST, "/songs/**", "/singers", "/genres").hasAnyAuthority(ADMIN)
                .requestMatchers(PUT, "/singers/*").hasAnyAuthority(ADMIN)
                .requestMatchers(DELETE, "/songs/*", "/singers/*", "/genres/*").hasAnyAuthority(ADMIN)

                .anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }
}
