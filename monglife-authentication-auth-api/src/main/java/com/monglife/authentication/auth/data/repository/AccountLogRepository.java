package com.monglife.authentication.auth.data.repository;

import com.monglife.authentication.auth.data.customRepository.CustomAccountLogRepository;
import com.monglife.authentication.auth.data.entity.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountLogRepository extends JpaRepository<AccountLog, Long>, CustomAccountLogRepository {
}
