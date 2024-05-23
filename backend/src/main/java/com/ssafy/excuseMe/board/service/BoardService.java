package com.ssafy.excuseMe.board.service;

import java.util.List;
import java.util.Map;

import com.ssafy.excuseMe.board.model.BoardDto;
import com.ssafy.excuseMe.board.model.BoardListDto;
import com.ssafy.excuseMe.board.model.CommentDto;

public interface BoardService {
	
	void writeArticle(BoardDto boardDto) throws Exception;
	BoardListDto listArticle(Map<String, Object> map) throws Exception;
	BoardListDto mylistArticle(Map<String,Object> map) throws Exception;

	BoardDto getArticle(int articleNo) throws Exception;
	List<CommentDto> getComment(int artilceNo) throws Exception;
	
	void updateHit(int articleNo) throws Exception;
	
	void modifyArticle(BoardDto boardDto) throws Exception;
	
	void beforedeleteArticle(int articleNo) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	
	void likeArticle(String user_id, int articleno) throws Exception;
	void likeHit(int articleno) throws Exception;
	
	boolean check(String user_id, int articleno) throws Exception;
	
	//댓글
	void writeComment(CommentDto commentDto) throws Exception;
	void deleteComment(int commentNo) throws Exception;

}
