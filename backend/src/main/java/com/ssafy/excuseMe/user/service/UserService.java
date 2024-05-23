package com.ssafy.excuseMe.user.service;

import java.util.List;
import java.util.Map;

import com.ssafy.excuseMe.user.model.UserDto;

public interface UserService {
	
	int idCheck(String userId) throws Exception;
	void joinUser(UserDto userDto) throws Exception;
	UserDto loginUser(UserDto userDto) throws Exception;
	UserDto userInfo(String userId)  throws Exception;
	
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	
	void updateUser(UserDto userDto) throws Exception;
	void deleteUser(String userId) throws Exception;
	
	/* Admin */
	List<UserDto> listUser(Map<String, Object> map) throws Exception;
	UserDto getUser(String userId) throws Exception;

	
}
