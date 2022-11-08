package com.user.profile.service;

import java.util.List;

import com.user.profile.dto.UserDto;
import com.user.profile.entity.User;

public interface UserService {
    void saveUser(UserDto user);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

	UserDto convertEntityToDto(User user);
}
