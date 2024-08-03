package com.monglife.authentication.auth.business.vo;

import lombok.Builder;

@Builder
public record ValidationAccessTokenVo(
        String accessToken
) {
}
