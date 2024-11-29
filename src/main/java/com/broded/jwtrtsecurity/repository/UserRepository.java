package com.broded.jwtrtsecurity.repository;

import com.broded.jwtrtsecurity.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> getUserByGuid(UUID guid);
}
