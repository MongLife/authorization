package com.monglife.authentication.auth.presentation.dto.res;

import lombok.Builder;

@Builder
public record ReissueResDto(
        String accessToken,
        String refreshToken
) {
}