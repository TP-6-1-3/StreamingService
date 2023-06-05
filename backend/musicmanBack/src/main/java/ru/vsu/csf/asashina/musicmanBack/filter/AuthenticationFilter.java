package ru.vsu.csf.asashina.musicmanBack.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vsu.csf.asashina.musicmanBack.exception.TokenValidationException;
import ru.vsu.csf.asashina.musicmanBack.service.TokenService;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (StringUtils.startsWithIgnoreCase(authHeader, TokenService.TOKEN_PREFIX)) {
            try {
                tokenService.authenticate(authHeader);
            } catch (Exception e) {
                throw new TokenValidationException("Во время валидации токена произошла ошибка");
            }
        }
        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        filterChain.doFilter(request, response);
    }
}
