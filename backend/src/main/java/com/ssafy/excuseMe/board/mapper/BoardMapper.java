package com.ssafy.excuseMe.board.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.excuseMe.board.model.BoardDto;
import com.ssafy.excuseMe.board.model.CommentDto;

@Mapper
public interface BoardMapper {
	
	void writeArticle(BoardDto boardDto) throws SQLException;

	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;
	
	List<BoardDto> mylistArticle(Map<String, Object> param) throws SQLException;

	int getTotalArticleCount(Map<String, Object> param) throws SQLException;

	BoardDto getArticle(int articleNo) throws SQLException;
	
	List<CommentDto> getComment(int articleNo) throws SQLException;

	void updateHit(int articleNo) throws SQLException;

	void modifyArticle(BoardDto boardDto) throws SQLException;
	
	void deleteArticle(int articleNo) throws SQLException;
	
	void writeComment(CommentDto commentDto) throws Exception;
	
	void deleteComment(int commentNo) throws SQLException;
	
	void beforedeleteArticle(int articleNo) throws SQLException;
	
	void likeArticle(String user_id, int articleno) throws SQLException;
	
	void likeHit(int aritlceno)throws SQLException;
	
	int check(String user_id, int articleno) throws SQLException;


}
