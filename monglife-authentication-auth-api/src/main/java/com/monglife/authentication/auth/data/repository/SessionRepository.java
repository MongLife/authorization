package com.monglife.authentication.auth.data.repository;

import com.monglife.authentication.auth.data.entity.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, String> {

    Optional<Session> findByDeviceIdAndAccountId(String deviceId, Long accountId);
}
