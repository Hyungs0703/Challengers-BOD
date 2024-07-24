package com.bod.bod.user.controller;

import com.bod.bod.global.dto.CommonResponseDto;
import com.bod.bod.global.jwt.security.UserDetailsImpl;
import com.bod.bod.user.dto.EditPasswordRequestDto;
import com.bod.bod.user.dto.LoginRequestDto;
import com.bod.bod.user.dto.EditProfileRequestDto;
import com.bod.bod.user.dto.SignUpRequestDto;
import com.bod.bod.user.dto.UserResponseDto;
import com.bod.bod.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final UserServiceImpl userService;

	@PostMapping("/signup")
	public ResponseEntity<CommonResponseDto<Void>> signUp(
		@RequestBody @Valid SignUpRequestDto signUpRequestDto
	) {
		userService.signUp(signUpRequestDto);
		return ResponseEntity.ok().body(new CommonResponseDto<>
			(HttpStatus.OK.value(), "회원가입이 완료되었습니다.", null));
	}

	@PostMapping("/login")
	public ResponseEntity<CommonResponseDto<Void>> login(
		@RequestBody @Valid LoginRequestDto loginRequestDto,
		HttpServletResponse response
	) {
		userService.login(loginRequestDto, response);
		return ResponseEntity.ok().body(new CommonResponseDto<>(
			HttpStatus.OK.value(), "로그인이 완료되었습니다.", null));
	}

	@DeleteMapping("/logout")
	public ResponseEntity<CommonResponseDto<Void>> logout(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		userService.logout(request, response, userDetails.getUser());
		return ResponseEntity.ok().body(new CommonResponseDto<>(
			HttpStatus.OK.value(), "로그아웃이 완료되었습니다.", null));
	}

	@DeleteMapping("/withdraw")
	public ResponseEntity<CommonResponseDto<Void>> withdraw(
		@RequestBody @Valid LoginRequestDto loginRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		HttpServletResponse response
	) {
		userService.withdraw(loginRequestDto, userDetails.getUser(), response);
		return ResponseEntity.ok().body(new CommonResponseDto<>(
			HttpStatus.OK.value(), "회원탈퇴가 완료되었습니다.", null));
	}

	@GetMapping("/users/profile")
	public ResponseEntity<CommonResponseDto<UserResponseDto>> getProfile(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		UserResponseDto userResponseDto = userService.getProfile(userDetails.getUser());
		return ResponseEntity.ok().body(new CommonResponseDto<>(
			HttpStatus.OK.value(), "프로필 조회가 완료되었습니다.", userResponseDto));
	}

	@PutMapping("/users/profile")
	public ResponseEntity<CommonResponseDto<UserResponseDto>> editProfile(
		@RequestBody @Valid EditProfileRequestDto editProfileRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		UserResponseDto userResponseDto = userService.editProfile(editProfileRequestDto, userDetails.getUser());
		return ResponseEntity.ok().body(new CommonResponseDto<>(
			HttpStatus.OK.value(), "회원정보 수정이 완료되었습니다.", userResponseDto));
	}

	@PutMapping("/users/profile/password")
	public ResponseEntity<CommonResponseDto<UserResponseDto>> editPassword(
		@RequestBody @Valid EditPasswordRequestDto editPasswordRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		UserResponseDto userResponseDto = userService.editPassword(editPasswordRequestDto, userDetails.getUser());
		return ResponseEntity.ok().body(new CommonResponseDto<>(
			HttpStatus.OK.value(), "비밀번호 수정이 완료되었습니다.", userResponseDto));
	}
}
