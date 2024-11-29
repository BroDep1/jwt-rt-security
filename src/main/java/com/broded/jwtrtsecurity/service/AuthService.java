package com.broded.jwtrtsecurity.service;

import com.broded.jwtrtsecurity.dto.request.RefreshRequest;
import com.broded.jwtrtsecurity.dto.request.SignInRequest;
import com.broded.jwtrtsecurity.dto.response.JwtRtAuthResponse;
import com.broded.jwtrtsecurity.entity.RefreshToken;
import com.broded.jwtrtsecurity.entity.User;
import com.broded.jwtrtsecurity.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public JwtRtAuthResponse signIn(SignInRequest signInRequest, HttpServletRequest httpServletRequest){
        var user = userRepository.getUserByGuid(signInRequest.guid())
                .orElseThrow(() -> new RuntimeException("user.with.guid.%s.not.found".formatted(signInRequest.guid())));
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.create(user, httpServletRequest);
        return new JwtRtAuthResponse(jwt, refreshToken);
    }

    public JwtRtAuthResponse refresh(RefreshRequest refreshRequest, HttpServletRequest httpServletRequest){
        return refreshTokenService.update(
                refreshRequest.refreshToken(),
                httpServletRequest
        );
    }
}
