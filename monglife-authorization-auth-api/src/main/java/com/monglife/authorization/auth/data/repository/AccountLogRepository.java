package com.monglife.authorization.auth.data.repository;

import com.monglife.authorization.auth.data.customRepository.CustomAccountLogRepository;
import com.monglife.authorization.auth.data.entity.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountLogRepository extends JpaRepository<AccountLog, Long>, CustomAccountLogRepository {
}
