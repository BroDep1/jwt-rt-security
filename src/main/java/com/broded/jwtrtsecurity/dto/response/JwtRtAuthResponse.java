package com.broded.jwtrtsecurity.dto.response;

public record JwtRtAuthResponse(
    String jwt,
    String refreshToken
) {
}
