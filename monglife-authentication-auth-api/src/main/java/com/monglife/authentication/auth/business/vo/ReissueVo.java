package com.monglife.authentication.auth.business.vo;

import lombok.Builder;

@Builder
public record ReissueVo(
        String accessToken,
        String refreshToken
) {
}
