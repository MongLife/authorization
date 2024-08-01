package com.monglife.authorization.auth.business.service;

import com.monglife.authorization.auth.business.vo.LoginVo;
import com.monglife.authorization.auth.business.vo.LogoutVo;
import com.monglife.authorization.auth.business.vo.ReissueVo;
import com.monglife.authorization.auth.business.vo.ValidationAccessTokenVo;
import com.monglife.authorization.auth.data.repository.AccountRepository;
import com.monglife.core.vo.passport.PassportDataAccountVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;

    @Transactional
    public LoginVo login(String deviceId, String email, String name) {
        return  null;
    }

    @Transactional
    public LogoutVo logout(String refreshToken) {
        return  null;
    }

    @Transactional
    public ReissueVo reissue(String refreshToken) {
        return  null;
    }

    @Transactional
    public ValidationAccessTokenVo validationAccessToken(String accessToken) {
        return null;
    }

    @Transactional
    public PassportDataAccountVo findPassportDataAccount(String accessToken) {
        return  PassportDataAccountVo.builder().build();
    }
}
