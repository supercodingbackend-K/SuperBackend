package com.github.superconding.repository.users;

public interface UserRepository {
    UserEntity findUserById(Integer userId);
}
