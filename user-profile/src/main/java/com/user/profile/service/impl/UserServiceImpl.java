package com.user.profile.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.profile.constant.CommonConstants;
import com.user.profile.dto.UserDto;
import com.user.profile.entity.Role;
import com.user.profile.entity.User;
import com.user.profile.repository.RoleRepository;
import com.user.profile.repository.UserRepository;
import com.user.profile.service.UserService;

@Service
public class UserServiceImpl extends CommonConstants implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
//    private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(UserDto user) {
		// encrypt the password once we integrate spring security
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
		User existingUser = findByEmail(user.getEmail());
		if (Optional.ofNullable(existingUser).isPresent()) {
			existingUser.setPassword(user.getPassword());
			existingUser.setChange_type(ONE);
			existingUser.setFatherName(user.getFatherName());
			existingUser.setMotherName(user.getMotherName());
			existingUser.setStudentId(user.getStudentId());
			existingUser.setEnrollment(user.getEnrollment());
			existingUser.setUniversityName(user.getUniversityName());
			if (Optional.ofNullable(user.getFirstName()).isPresent()
					&& Optional.ofNullable(user.getLastName()).isPresent()) {
				existingUser.setName(user.getFirstName() + " " + user.getLastName());
			}
			existingUser.setPassword(user.getPassword());
		} else {
			existingUser = convertDtoTOEntity(user);
		}

		Role role = roleRepository.findByName("ROLE_ADMIN");
		if (role == null) {
			role = checkRoleExist();
		}
		userRepository.save(existingUser);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> convertEntityToDto(user)).collect(Collectors.toList());
	}

	@Override
	public UserDto convertEntityToDto(User user) {
		UserDto userDto = new UserDto();
		/*
		 * if (Optional.ofNullable(user.getName()).isPresent()) { String[] name =
		 * user.getName().split(" ");
		 * 
		 * userDto.setFirstName(Optional.ofNullable(name[0]).isPresent() ? name[0] :
		 * ""); userDto.setLastName(Optional.ofNullable(name[1]).isPresent() ? name[1] :
		 * ""); }
		 */
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setChange_type(user.getChange_type());
		userDto.setPassword(user.getPassword());
		userDto.setId(user.getId());
		userDto.setFatherName(user.getFatherName());

		userDto.setMotherName(user.getMotherName());
		userDto.setStudentId(user.getStudentId());
		userDto.setEnrollment(user.getEnrollment());
		userDto.setUniversityName(user.getUniversityName());

		return userDto;
	}

	private User convertDtoTOEntity(UserDto user) {
		User userDto = new User();
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setChange_type(Integer.valueOf(0));
		userDto.setPassword(user.getPassword());
		userDto.setFatherName(user.getFatherName());
		userDto.setMotherName(user.getMotherName());
		userDto.setStudentId(user.getStudentId());
		userDto.setEnrollment(user.getEnrollment());
		userDto.setUniversityName(user.getUniversityName());
		return userDto;
	}

	private Role checkRoleExist() {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		return roleRepository.save(role);
	}
}
