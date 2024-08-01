package com.monglife.authorization.auth.presentation.controller;

import com.monglife.authorization.auth.business.vo.ValidationAccessTokenVo;
import com.monglife.authorization.auth.presentation.dto.req.LoginReqDto;
import com.monglife.authorization.auth.presentation.dto.req.LogoutReqDto;
import com.monglife.authorization.auth.presentation.dto.req.ReissueReqDto;
import com.monglife.authorization.auth.presentation.dto.res.LoginResDto;
import com.monglife.authorization.auth.presentation.dto.res.LogoutResDto;
import com.monglife.authorization.auth.presentation.dto.res.ReissueResDto;
import com.monglife.authorization.auth.business.service.AuthService;
import com.monglife.authorization.auth.business.vo.LoginVo;
import com.monglife.authorization.auth.business.vo.LogoutVo;
import com.monglife.authorization.auth.business.vo.ReissueVo;
import com.monglife.authorization.auth.presentation.dto.res.ValidationAccessTokenResDto;
import com.monglife.core.vo.passport.PassportDataAccountVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody @Validated LoginReqDto loginReqDto) {

        LoginVo loginVo = authService.login(loginReqDto.deviceId(), loginReqDto.email(), loginReqDto.name());

        return ResponseEntity.ok().body(LoginResDto.builder()
                .accountId(loginVo.accountId())
                .accessToken(loginVo.accessToken())
                .refreshToken(loginVo.refreshToken())
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResDto> logout(@RequestBody @Validated LogoutReqDto logoutReqDto) {

        LogoutVo logoutVo = authService.logout(logoutReqDto.refreshToken());

        return ResponseEntity.ok().body(LogoutResDto.builder()
                .accountId(logoutVo.accountId())
                .build());
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResDto> reissue(@RequestBody @Validated ReissueReqDto reissueReqDto) {

        ReissueVo reissueVo = authService.reissue(reissueReqDto.refreshToken());

        return ResponseEntity.ok().body(ReissueResDto.builder()
                .accessToken(reissueVo.accessToken())
                .refreshToken(reissueVo.refreshToken())
                .build());
    }

    @GetMapping("/internal/validation/{accessToken}")
    public ResponseEntity<ValidationAccessTokenResDto> validationAccessToken(@PathVariable("accessToken") @NotBlank String accessToken) {

        ValidationAccessTokenVo validationAccessTokenVo = authService.validationAccessToken(accessToken);

        return ResponseEntity.ok().body(ValidationAccessTokenResDto.builder()
                .accessToken(validationAccessTokenVo.accessToken())
                .build());
    }

    @GetMapping("/internal/passport/{accessToken}")
    public ResponseEntity<PassportDataAccountVo> passport(@PathVariable("accessToken") @NotBlank String accessToken) {

        PassportDataAccountVo passportAccountVo = authService.findPassportDataAccount(accessToken);

        return ResponseEntity.ok().body(PassportDataAccountVo.builder()
                .id(passportAccountVo.id())
                .deviceId(passportAccountVo.deviceId())
                .email(passportAccountVo.email())
                .name(passportAccountVo.name())
                .role(passportAccountVo.role())
                .build());
    }
}
