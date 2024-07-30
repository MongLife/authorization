package com.monglife.authorization.auth.dto.res;

import lombok.Builder;

@Builder(toBuilder = true)
public record PassportAccountResDto(
        Long id,
        String deviceId,
        String email,
        String name,
        String role
) {
}
