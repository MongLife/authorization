package com.monglife.authentication.auth.data.customRepository;

import com.monglife.authentication.auth.data.entity.Account;

import java.util.Optional;

public interface CustomAccountRepository {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByAccountId(Long accountId);
}
