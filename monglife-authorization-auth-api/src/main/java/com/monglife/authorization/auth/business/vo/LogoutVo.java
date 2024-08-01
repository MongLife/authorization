package com.monglife.authorization.auth.business.vo;

import lombok.Builder;

@Builder
public record LogoutVo(
        Long accountId
) {
}
