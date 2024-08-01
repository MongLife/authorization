package com.monglife.authorization.auth.error;

import com.monglife.core.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "AUTH-100", "access token expired."),
    PASSPORT_GENERATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), "AUTH_INTERNAL-102", "passport generate fail."),
    ;

    private final Integer httpStatus;
    private final String code;
    private final String message;
}
