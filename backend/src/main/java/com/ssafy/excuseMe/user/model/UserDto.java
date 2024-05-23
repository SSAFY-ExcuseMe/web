package com.ssafy.excuseMe.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "UserDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class UserDto {
	
	@Schema(description = "아이디")
	private String userId;
	@Schema(description = "이름")
	private String userName;
	@Schema(description = "비밀번호")
	private String userPwd;
	@Schema(description = "이메일")
	private String email;
	@Schema(description = "핸드폰 번호")
	private String phone;
	@Schema(description = "권한")
	private String role;
	@Schema(description = "refreshToken")
	private String refreshToken;

}
