package com.rbikiteev.user_subscriptions.service;

import com.rbikiteev.user_subscriptions.entity.UserEntity;
import com.rbikiteev.user_subscriptions.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity user) {
        UserEntity createdUser = userRepository.save(user);
        log.info(String.format("User %s was created", createdUser));
        return createdUser;
    }

    public Optional<UserEntity> getUser(UUID id) {
        return userRepository.findById(id);
    }

    public UserEntity updateUser(UUID id, UserEntity updated) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        user.setAge(updated.getAge());
        user.setAddress(updated.getAddress());
        user.setBirthDate(updated.getBirthDate());
        UserEntity updatedUser = userRepository.save(user);
        log.info(String.format("User %s was updated", updatedUser));
        return updatedUser;
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
        log.info(String.format("User with id %s was deleted", id));
    }
}
