package com.user.profile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.user.profile.constant.CommonConstants;
import com.user.profile.dto.UserDto;
import com.user.profile.entity.User;
import com.user.profile.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserProfileController extends CommonConstants {

	@Autowired
	private UserService userService;

	public UserProfileController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(INDEX_ACTION)
	public String home() {
		return INDEX_PAGE;
	}

	@GetMapping(LOGIN_ACTION)
	public String loginForm() {
		return LOGIN_PAGE;
	}

	// handler method to handle user first password update request
	@GetMapping(UPDATE_ACTION)
	public String showRegistrationForm(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return UPDATE_PAGE;
	}

	// handler method to handle password update form submit request
	@PostMapping(UPDATE_SAVE_ACTION)
	public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {

		userService.saveUser(user);
		return REDIRECT + PROFILE_ACTION;
	}

	@GetMapping(PROFILE_ACTION)
	public String listRegisteredUsers(Model model) {
		List<UserDto> users = new ArrayList<>();
		User user = new User();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			user = userService.findByEmail(username);
			if (Optional.ofNullable(user).isPresent() && (user.getChange_type() == ONE)
					&& Optional.ofNullable(user.getRoles()).isPresent() && user.getRoles().size()>0
					&& (user.getRoles().get(0).getName().equals("ROLE_USER"))) {
				return REDIRECT + UPLOAD_ACTION;
			} else if (Optional.ofNullable(user).isPresent() && !(user.getChange_type() == ONE)) {
				return REDIRECT + UPDATE_ACTION;
			}
			users.add(userService.convertEntityToDto(user));
		}
		model.addAttribute("users", users);
		return PROFILE_PAGE;
	}

}
