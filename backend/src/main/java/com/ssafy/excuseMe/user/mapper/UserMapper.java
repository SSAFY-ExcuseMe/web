package com.ssafy.excuseMe.user.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.excuseMe.user.model.UserDto;

@Mapper
public interface UserMapper {
	int idCheck(String userId) throws SQLException;
	void joinUser(UserDto userDto) throws SQLException;
	UserDto loginUser(UserDto userDto) throws SQLException;
	UserDto userInfo(String userId) throws SQLException;
	
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userId) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
	void updateUser(UserDto userDto) throws SQLException;
	void deleteUser(String userId) throws SQLException;
	
	//작성한 글
	//저장한 여행지
	
	/* Admin */
	List<UserDto> listUser(Map<String, Object> map) throws SQLException;
	UserDto getUser(String userId) throws SQLException;


}
