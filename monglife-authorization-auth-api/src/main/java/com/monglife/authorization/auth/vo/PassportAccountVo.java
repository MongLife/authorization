package com.monglife.authorization.auth.vo;

import lombok.Builder;

@Builder(toBuilder = true)
public record PassportAccountVo(
        Long id,
        String deviceId,
        String email,
        String name,
        String role
) {
}
