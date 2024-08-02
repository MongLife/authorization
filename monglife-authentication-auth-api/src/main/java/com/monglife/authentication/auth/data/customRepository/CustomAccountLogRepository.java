package com.monglife.authentication.auth.data.customRepository;

import com.monglife.authentication.auth.data.entity.AccountLog;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CustomAccountLogRepository {

    Optional<AccountLog> findByAccountIdAndDeviceIdAndLoginAt(@Param("accountId") Long accountId, @Param("deviceId") String deviceId, @Param("loginAt") LocalDate loginAt);
}
