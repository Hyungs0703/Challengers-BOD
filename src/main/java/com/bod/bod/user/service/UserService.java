package com.bod.bod.user.service;

import com.bod.bod.user.dto.LoginRequestDto;
import com.bod.bod.user.dto.ProfileRequestDto;
import com.bod.bod.user.dto.SignUpRequestDto;
import com.bod.bod.user.dto.UserResponseDto;
import com.bod.bod.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

	@Transactional
	void signUp(SignUpRequestDto signUpRequestDto);

	@Transactional
	void login(LoginRequestDto loginRequestDto, HttpServletResponse response);


	@Transactional
	void logout(HttpServletRequest request, HttpServletResponse response, User user);

	@Transactional
	void withdraw(LoginRequestDto loginRequestDto, User user, HttpServletResponse response);

	@Transactional
	UserResponseDto editProfile(ProfileRequestDto profileRequestDto, User user);
}
