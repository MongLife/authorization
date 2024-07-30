package com.monglife.authorization.auth.vo;

import lombok.Builder;

@Builder
public record LogoutVo(
        Long accountId
) {
}
