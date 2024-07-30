package com.monglife.authorization.auth.dto.res;

import lombok.Builder;

@Builder
public record LogoutResDto(
        Long accountId
) {
}
