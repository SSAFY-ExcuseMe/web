package com.ssafy.excuseMe.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.excuseMe.user.model.UserDto;
import com.ssafy.excuseMe.user.service.UserService;
import com.ssafy.excuseMe.util.JWTUtil;
import com.ssafy.excuseMe.user.controller.UserController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/user")
@Tag(name = "회원 인증 컨트롤러", description = "로그인 로그아웃, 토큰처리등 회원의 인증관련 처리하는 클래스.")
@Slf4j
@CrossOrigin("*")
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;
	private final JWTUtil jwtUtil;

	public UserController(UserService userService, JWTUtil jwtUtil) {
		super();
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("/join")
	public String join() {
		return "user/join";
	}
	
//	@Hidden
//	@Operation(hidden = true)
	@GetMapping("/{userid}")
	@ResponseBody
	public String idCheck(@PathVariable("userid") String userId) throws Exception {
		logger.debug("idCheck userid : {}", userId);
		int cnt = userService.idCheck(userId);
		return cnt + "";
	}
	
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserDto userDto) {
		logger.debug("userDto info : {}", userDto);
		System.out.println(userDto);
		try {
			userService.joinUser(userDto);
			if(userDto!=null) {
				//회원 가입 성공 시
				return ResponseEntity.ok().build(); //성공 응답
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("중복 확인 !");
				//실패 응답 (HTTP 상태 코드 401)
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 문제 발생 !!!");
			// 서버 오류 응답 (HTTP 상태 코드 500)
		}
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@Operation(summary = "로그인", description = "아이디와 비밀번호를 이용하여 로그인 처리.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @Parameter(description = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) UserDto userDto) {
		log.debug("login user : {}", userDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			UserDto loginUser = userService.loginUser(userDto);
			if(loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);
				log.debug("refresh token : {}", refreshToken);
				
//				발급받은 refresh token 을 DB에 저장.
				userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
				
//				JSON 으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해 주세요.");
				status = HttpStatus.UNAUTHORIZED;
			} 
			
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@Operation(summary = "회원인증", description = "회원 정보를 담은 Token 을 반환한다.")
	@GetMapping
	public ResponseEntity<Map<String, Object>> getInfo(
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			log.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				String userId = getUserIdFromToken(request.getHeader("Authorization"));
				UserDto memberDto = userService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@Operation(summary = "로그아웃", description = "회원 정보를 담은 Token 을 제거한다.")
	@GetMapping("/logout/{userId}")
	@Hidden
	public ResponseEntity<?> removeToken(@PathVariable ("userId") @Parameter(description = "로그아웃 할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@Operation(summary = "Access Token 재발급", description = "만료된 access token 을 재발급 받는다.")
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, userDto : {}", token, userDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(userDto.getUserId());
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 access token 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("refresh token 도 사용 불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@Operation(summary = "회원정보 수정", description = "회원의 정보를 수정합니다.")
	@PostMapping("/update")
	public ResponseEntity<?> updateUser(
	        @RequestBody @Parameter(description = "수정할 회원 정보", required = true) UserDto updatedUserDto,
	        HttpServletRequest request) {
	    Map<String, Object> resultMap = new HashMap<>();
	    HttpStatus status = HttpStatus.ACCEPTED;
	    try {
	        // Get the userId from the token
	        String userId = getUserIdFromToken(request.getHeader("Authorization"));
	        // Set the userId to the updated user DTO
	        updatedUserDto.setUserId(userId);
	        
	        // Update the user information
	        userService.updateUser(updatedUserDto);
	        
	        resultMap.put("message", "회원정보가 성공적으로 수정되었습니다.");
	        status = HttpStatus.OK;
	    } catch (Exception e) {
	        log.error("회원정보 수정 실패 : {}", e);
	        resultMap.put("message", e.getMessage());
	        status = HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	    return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@Operation(summary = "회원탈퇴", description = "회원을 탈퇴시킵니다.")
	@DeleteMapping("/delete")
	public ResponseEntity<?> withdrawUser(HttpServletRequest request) {
	    Map<String, Object> resultMap = new HashMap<>();
	    HttpStatus status = HttpStatus.ACCEPTED;
	    try {
	        // Get the userId from the token
	        String userId = getUserIdFromToken(request.getHeader("Authorization"));
	        
	        // Perform withdrawal operation
	        userService.deleteUser(userId);
	        
	        resultMap.put("message", "회원탈퇴가 성공적으로 처리되었습니다.");
	        status = HttpStatus.OK;
	    } catch (Exception e) {
	        log.error("회원탈퇴 실패 : {}", e);
	        resultMap.put("message", e.getMessage());
	        status = HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	    return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}


	// Helper method to extract userId from JWT token
	private String getUserIdFromToken(String token) {
	    // Extract userId from the token using your JWTUtil or any JWT parsing library
	    return jwtUtil.getUserId(token);
	}

}
