package com.monglife.authentication.auth.data.repository;

import com.monglife.authentication.auth.data.customRepository.CustomAccountRepository;
import com.monglife.authentication.auth.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, CustomAccountRepository {
}
