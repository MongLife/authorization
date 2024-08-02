package com.monglife.authentication.auth.business.service;

import com.monglife.authentication.auth.business.vo.LoginVo;
import com.monglife.authentication.auth.business.vo.LogoutVo;
import com.monglife.authentication.auth.business.vo.ReissueVo;
import com.monglife.authentication.auth.business.vo.ValidationAccessTokenVo;
import com.monglife.authentication.auth.data.entity.Account;
import com.monglife.authentication.auth.data.entity.AccountLog;
import com.monglife.authentication.auth.data.entity.Session;
import com.monglife.authentication.auth.data.repository.AccountLogRepository;
import com.monglife.authentication.auth.data.repository.AccountRepository;
import com.monglife.authentication.auth.data.repository.SessionRepository;
import com.monglife.authentication.auth.global.code.error.AuthErrorCode;
import com.monglife.authentication.auth.global.exception.error.ExpiredException;
import com.monglife.authentication.auth.global.exception.error.NotFoundException;
import com.monglife.authentication.auth.global.provider.AuthorizationTokenProvider;
import com.monglife.authentication.auth.global.code.role.RoleCode;
import com.monglife.core.vo.passport.PassportDataAccountVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final AccountLogRepository accountLogRepository;
    private final SessionRepository sessionRepository;

    private final AuthorizationTokenProvider tokenProvider;

    @Transactional
    public LoginVo login(String deviceId, String email, String name) {

        // 회원 조회 or 회원 생성
        Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> {
                    Account newAccount = Account.builder()
                            .email(email)
                            .name(name)
                            .role(RoleCode.NORMAL.getRole())
                            .build();

                     return accountRepository.save(newAccount);
                });

        // 존재하는 세션 삭제
        sessionRepository.findByDeviceIdAndAccountId(deviceId, account.getAccountId())
                .ifPresent(session -> sessionRepository.deleteById(session.getRefreshToken()));

        // AccessToken 및 RefreshToken 발급
        Long refreshTokenExpiration = tokenProvider.getRefreshTokenExpiration();
        String refreshToken = tokenProvider.generateRefreshToken();
        String accessToken = tokenProvider.generateAccessToken(account.getAccountId(), deviceId);

        // 새로운 세션 등록
        sessionRepository.save(Session.builder()
                .refreshToken(refreshToken)
                .deviceId(deviceId)
                .accountId(account.getAccountId())
                .createdAt(LocalDateTime.now())
                .expiration(refreshTokenExpiration)
                .build());

        // 로그인 로그 등록
        LocalDate today = LocalDate.now();
        AccountLog accountLog = accountLogRepository.findByAccountIdAndDeviceIdAndLoginAt(account.getAccountId(), deviceId, today)
                .orElseGet(() -> accountLogRepository.save(AccountLog.builder()
                        .accountId(account.getAccountId())
                        .deviceId(deviceId)
                        .loginAt(today)
                        .build()));

        // 로그인 카운트 1 증가
        accountLog.increaseLoginCount();

        return  LoginVo.builder()
                .accountId(account.getAccountId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public LogoutVo logout(String refreshToken) {

        Session session = sessionRepository.findById(refreshToken)
                .orElseThrow(() -> new NotFoundException(AuthErrorCode.NOT_FOUND_SESSION));

        // 존재하는 세션 삭제
        sessionRepository.deleteById(session.getRefreshToken());

        return LogoutVo.builder()
                .accountId(session.getAccountId())
                .build();
    }

    @Transactional
    public ReissueVo reissue(String refreshToken) {

        Session session = sessionRepository.findById(refreshToken)
                .orElseThrow(() -> new NotFoundException(AuthErrorCode.NOT_FOUND_SESSION));

        // 존재하는 세션 삭제
        sessionRepository.deleteById(session.getRefreshToken());

        // AccessToken 및 RefreshToken 발급
        Long refreshTokenExpiration = tokenProvider.getRefreshTokenExpiration();
        refreshToken = tokenProvider.generateRefreshToken();
        String accessToken = tokenProvider.generateAccessToken(session.getAccountId(), session.getDeviceId());

        // 새로운 세션 등록
        sessionRepository.save(Session.builder()
                .refreshToken(refreshToken)
                .deviceId(session.getDeviceId())
                .accountId(session.getAccountId())
                .createdAt(LocalDateTime.now())
                .expiration(refreshTokenExpiration)
                .build());

        return ReissueVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public ValidationAccessTokenVo validationAccessToken(String accessToken) {

        if (tokenProvider.isTokenExpired(accessToken)) {
            throw new ExpiredException(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        return ValidationAccessTokenVo.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public PassportDataAccountVo findPassportDataAccount(String accessToken) {

        if (tokenProvider.isTokenExpired(accessToken)) {
            throw new ExpiredException(AuthErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        Long accountId = tokenProvider.getMemberId(accessToken)
                .orElseThrow(() -> new ExpiredException(AuthErrorCode.ACCESS_TOKEN_EXPIRED));
        String deviceId = tokenProvider.getDeviceId(accessToken)
                .orElseThrow(() -> new ExpiredException(AuthErrorCode.ACCESS_TOKEN_EXPIRED));

        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow();

        return PassportDataAccountVo.builder()
                .accountId(accountId)
                .deviceId(deviceId)
                .email(account.getEmail())
                .name(account.getName())
                .role(account.getRole())
                .build();
    }
}
