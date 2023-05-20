package ru.vsu.csf.asashina.musicmanBack.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.model.dto.TokensDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.RefreshToken;
import ru.vsu.csf.asashina.musicmanBack.model.request.RefreshTokenRequest;
import ru.vsu.csf.asashina.musicmanBack.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String NICKNAME_CLAIM = "nickname";
    private static final String MUSICMAN_SERVER = "musicman-server";
    private static final String EXPIRATION_CLAIM = "exp";
    private static final String ROLES_CLAIM = "roles";
    private static final String EMAIL_CLAIM = "email";
    private static final String FULL_NAME_CLAIM = "fullName";

    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access-token.expire-date-ms}")
    private Integer accessTokenExpireTimeInMs;

    @Value("${security.jwt.refresh-token.expire-date-days}")
    private Integer refreshTokenExpireTimeInDays;

    public void authenticate(String authHeader) {
        DecodedJWT decodedJWT = decodeJWT(authHeader);
        Map<String, Claim> claims = decodedJWT.getClaims();
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get(EMAIL_CLAIM).asString());
        checkIfTokenIsExpired(claims.get(EXPIRATION_CLAIM).asDate());
        setAuthToken(userDetails);
    }

    private DecodedJWT decodeJWT(String authHeader) {
        String token = authHeader;
        if (authHeader.startsWith(TOKEN_PREFIX)) {
            token = authHeader.substring(TOKEN_PREFIX.length());
        }
        var algorithm = getAlgorithm();
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey.getBytes());
    }

    private void checkIfTokenIsExpired(Date tokenExpireDate) {
        if (tokenExpireDate.before(new Date())) {
            throw new TokenExpiredException("Старый невалидный токен", Instant.now());
        }
    }

    private void checkIfTokenIsExpired(Instant tokenExpireDate) {
        if (tokenExpireDate.isBefore(Instant.now())) {
            throw new TokenExpiredException("Старый невалидный токен", Instant.now());
        }
    }

    private void setAuthToken(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Transactional
    public TokensDTO createTokens(UserDTO user) {
        return new TokensDTO(generateAccessToken(user), generateRefreshToken(user).toString());
    }

    private String generateAccessToken(UserDTO user) {
        return JWT.create()
                .withSubject(user.getUserId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpireTimeInMs))
                .withIssuer(MUSICMAN_SERVER)
                .withClaim(ROLES_CLAIM, user.getRoles().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
                )
                .withClaim(EMAIL_CLAIM, user.getEmail())
                .withClaim(NICKNAME_CLAIM, user.getNickname())
                .withClaim(FULL_NAME_CLAIM, user.getFirstName().concat(" ").concat(user.getLastName()))
                .sign(getAlgorithm());
    }

    private UUID generateRefreshToken(UserDTO user) {
        UUID refreshToken = UUID.randomUUID();
        refreshTokenRepository.saveNewRefreshToken(
                refreshToken,
                Instant.now().plusSeconds(fromDaysToSeconds(refreshTokenExpireTimeInDays)),
                user.getUserId());
        return refreshToken;
    }

    private static int fromDaysToSeconds(int days) {
        return days * 24 * 60 * 60;
    }

    public TokensDTO refreshAccessToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = findRefreshTokenById(request.getRefreshToken());
        checkIfTokenIsExpired(refreshToken.getValidTill());
        return new TokensDTO(
                generateAccessToken(userService.getUserByEmail(refreshToken.getUser().getEmail())),
                refreshToken.getToken().toString()
        );
    }

    private RefreshToken findRefreshTokenById(String refreshToken) {
        return refreshTokenRepository.findById(UUID.fromString(refreshToken)).orElseThrow(
                () -> new EntityDoesNotExistException("Токен не существует")
        );
    }
}
