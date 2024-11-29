package com.broded.jwtrtsecurity.service;

import com.broded.jwtrtsecurity.dto.response.JwtRtAuthResponse;
import com.broded.jwtrtsecurity.entity.RefreshToken;
import com.broded.jwtrtsecurity.entity.User;
import com.broded.jwtrtsecurity.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();
    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Transactional
    public String create(User user, HttpServletRequest httpServletRequest){
        Optional.ofNullable(user.getRefreshToken()).ifPresent(refreshTokenRepository::delete);
        var generatedToken = generateToken();
        var refreshToken = RefreshToken.builder()
                .token(passwordEncoder.encode(generatedToken))
                .ip(getCurrentIp(httpServletRequest))
                .user(user)
                .build();
        user.setRefreshToken(refreshToken);
        refreshTokenRepository.save(refreshToken);
        userService.update(user);
        return generatedToken;
    }

    @Transactional
    public JwtRtAuthResponse update(String oldRefreshToken, HttpServletRequest httpServletRequest){
        for (RefreshToken existingToken : refreshTokenRepository.findAll()) {
            if (passwordEncoder.matches(oldRefreshToken, existingToken.getToken())) {
                if (!getCurrentIp(httpServletRequest).equals(existingToken.getIp())){
                    emailService.sendAlert(getCurrentIp(httpServletRequest));
                }
                User user = existingToken.getUser();
                refreshTokenRepository.delete(existingToken);
                var generatedToken = generateToken();
                var refreshToken = RefreshToken.builder()
                        .token(passwordEncoder.encode(passwordEncoder.encode(generatedToken)))
                        .ip(getCurrentIp(httpServletRequest))
                        .user(user)
                        .build();
                user.setRefreshToken(refreshToken);
                refreshTokenRepository.save(refreshToken);
                userService.update(user);
                var jwt = jwtService.generateToken(user);
                return new JwtRtAuthResponse(jwt, generatedToken);
            }
        }
        throw new RuntimeException("token.not.found");
    }

    private String getCurrentIp(HttpServletRequest httpServletRequest){
        return httpServletRequest.getRemoteAddr();
    }

    private String generateToken() {
        byte[] randomBytes = new byte[48];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
