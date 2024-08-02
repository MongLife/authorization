package com.monglife.authentication.auth.presentation.dto.res;

import lombok.Builder;

@Builder
public record LoginResDto(
        Long accountId,
        String accessToken,
        String refreshToken
) {
}
