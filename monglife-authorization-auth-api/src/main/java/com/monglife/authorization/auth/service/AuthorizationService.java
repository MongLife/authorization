package com.monglife.authorization.auth.service;

import com.monglife.authorization.auth.vo.LoginVo;
import com.monglife.authorization.auth.vo.LogoutVo;
import com.monglife.authorization.auth.vo.ReissueVo;
import com.monglife.core.vo.passport.PassportDataAccountVo;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public LoginVo login(String deviceId, String email, String name) {
        return  null;
    }
    public LogoutVo logout(String refreshToken) {
        return  null;
    }
    public ReissueVo reissue(String refreshToken) {
        return  null;
    }
    public PassportDataAccountVo findPassportDataAccount(String accessToken) {
        return  PassportDataAccountVo.builder().build();
    }
}
