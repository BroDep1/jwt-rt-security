package com.broded.jwtrtsecurity.service;

import com.broded.jwtrtsecurity.entity.User;
import com.broded.jwtrtsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void update(User user){
        userRepository.save(user);
    }

}
