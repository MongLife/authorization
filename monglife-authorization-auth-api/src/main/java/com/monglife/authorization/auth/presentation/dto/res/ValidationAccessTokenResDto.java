package com.monglife.authorization.auth.presentation.dto.res;

import lombok.Builder;

@Builder
public record ValidationAccessTokenResDto(
        String accessToken
) {
}