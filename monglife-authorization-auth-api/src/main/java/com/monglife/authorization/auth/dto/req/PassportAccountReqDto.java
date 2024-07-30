package com.monglife.authorization.auth.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record PassportAccountReqDto(
        @NotEmpty
        @NotBlank
        String accessToken
) {}
