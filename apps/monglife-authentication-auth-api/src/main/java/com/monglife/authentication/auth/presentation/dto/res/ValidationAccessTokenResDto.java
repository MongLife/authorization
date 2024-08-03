package com.monglife.authentication.auth.presentation.dto.res;

import lombok.Builder;

@Builder
public record ValidationAccessTokenResDto(
        String accessToken
) {
}