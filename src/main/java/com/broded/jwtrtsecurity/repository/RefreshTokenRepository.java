package com.broded.jwtrtsecurity.repository;

import com.broded.jwtrtsecurity.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
