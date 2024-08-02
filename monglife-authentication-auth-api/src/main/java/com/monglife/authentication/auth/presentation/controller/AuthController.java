package com.monglife.authentication.auth.presentation.controller;

import com.monglife.authentication.auth.business.vo.ValidationAccessTokenVo;
import com.monglife.authentication.auth.presentation.dto.req.LoginReqDto;
import com.monglife.authentication.auth.presentation.dto.req.LogoutReqDto;
import com.monglife.authentication.auth.presentation.dto.req.ReissueReqDto;
import com.monglife.authentication.auth.presentation.dto.res.LoginResDto;
import com.monglife.authentication.auth.presentation.dto.res.LogoutResDto;
import com.monglife.authentication.auth.presentation.dto.res.ReissueResDto;
import com.monglife.authentication.auth.business.service.AuthService;
import com.monglife.authentication.auth.business.vo.LoginVo;
import com.monglife.authentication.auth.business.vo.LogoutVo;
import com.monglife.authentication.auth.business.vo.ReissueVo;
import com.monglife.authentication.auth.presentation.dto.res.ValidationAccessTokenResDto;
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

    @GetMapping("/validation/accessToken")
    public ResponseEntity<ValidationAccessTokenResDto> validationAccessToken(@RequestParam("accessToken") @NotBlank String accessToken) {

        ValidationAccessTokenVo validationAccessTokenVo = authService.validationAccessToken(accessToken);

        return ResponseEntity.ok().body(ValidationAccessTokenResDto.builder()
                .accessToken(validationAccessTokenVo.accessToken())
                .build());
    }

    @GetMapping("/passport")
    public ResponseEntity<PassportDataAccountVo> passport(@RequestParam("accessToken") @NotBlank String accessToken) {

        PassportDataAccountVo passportAccountVo = authService.findPassportDataAccount(accessToken);

        return ResponseEntity.ok().body(PassportDataAccountVo.builder()
                .accountId(passportAccountVo.accountId())
                .deviceId(passportAccountVo.deviceId())
                .email(passportAccountVo.email())
                .name(passportAccountVo.name())
                .role(passportAccountVo.role())
                .build());
    }
}
