package com.monglife.authorization.auth.controller;

import com.monglife.authorization.auth.dto.req.LoginReqDto;
import com.monglife.authorization.auth.dto.req.LogoutReqDto;
import com.monglife.authorization.auth.dto.req.ReissueReqDto;
import com.monglife.authorization.auth.dto.res.LoginResDto;
import com.monglife.authorization.auth.dto.res.LogoutResDto;
import com.monglife.authorization.auth.dto.res.ReissueResDto;
import com.monglife.authorization.auth.service.AuthorizationService;
import com.monglife.authorization.auth.vo.LoginVo;
import com.monglife.authorization.auth.vo.LogoutVo;
import com.monglife.authorization.auth.vo.ReissueVo;
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

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody @Validated LoginReqDto loginReqDto) {

        LoginVo loginVo = authorizationService.login(loginReqDto.deviceId(), loginReqDto.email(), loginReqDto.name());

        return ResponseEntity.ok().body(LoginResDto.builder()
                .accountId(loginVo.accountId())
                .accessToken(loginVo.accessToken())
                .refreshToken(loginVo.refreshToken())
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResDto> logout(@RequestBody @Validated LogoutReqDto logoutReqDto) {

        LogoutVo logoutVo = authorizationService.logout(logoutReqDto.refreshToken());

        return ResponseEntity.ok().body(LogoutResDto.builder()
                .accountId(logoutVo.accountId())
                .build());
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResDto> reissue(@RequestBody @Validated ReissueReqDto reissueReqDto) {

        ReissueVo reissueVo = authorizationService.reissue(reissueReqDto.refreshToken());

        return ResponseEntity.ok().body(ReissueResDto.builder()
                .accessToken(reissueVo.accessToken())
                .refreshToken(reissueVo.refreshToken())
                .build());
    }

    @GetMapping("/passport/{accessToken}")
    public ResponseEntity<PassportDataAccountVo> passport(@PathVariable("accessToken") @NotBlank String accessToken) {

        PassportDataAccountVo passportAccountVo = authorizationService.findPassportDataAccount(accessToken);

        return ResponseEntity.ok().body(PassportDataAccountVo.builder()
                .id(passportAccountVo.id())
                .deviceId(passportAccountVo.deviceId())
                .email(passportAccountVo.email())
                .name(passportAccountVo.name())
                .role(passportAccountVo.role())
                .build());
    }
}
