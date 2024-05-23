package com.ssafy.excuseMe.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.excuseMe.board.model.BoardDto;
import com.ssafy.excuseMe.board.model.BoardListDto;
import com.ssafy.excuseMe.board.model.CommentDto;
import com.ssafy.excuseMe.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	@Transactional
	public void writeArticle(BoardDto boardDto) throws Exception {
		boardMapper.writeArticle(boardDto);
	}

	@Override
	public BoardListDto listArticle(Map<String, Object> map) throws Exception {
		List<BoardDto> list = boardMapper.listArticle(map);

		int totalArticleCount = boardMapper.getTotalArticleCount(map);
		int totalPageCount = (totalArticleCount - 1) / 20 + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
	}
	
	@Override
	public BoardListDto mylistArticle(Map<String, Object> map) throws Exception {
		List<BoardDto> list = boardMapper.mylistArticle(map);

		int totalArticleCount = boardMapper.getTotalArticleCount(map);
		int totalPageCount = (totalArticleCount - 1) / 20 + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws Exception {
		boardMapper.modifyArticle(boardDto);
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		boardMapper.deleteArticle(articleNo);
	
	}

	@Override
	public void writeComment(CommentDto commentDto) throws Exception {
		boardMapper.writeComment(commentDto);
	}

	@Override
	public List<CommentDto> getComment(int articleNo) throws Exception {
		return boardMapper.getComment(articleNo);
	}

	@Override
	public void deleteComment(int commentNo) throws Exception {
		boardMapper.deleteComment(commentNo);
		
	}

	@Override
	public void beforedeleteArticle(int articleNo) throws Exception {
		boardMapper.beforedeleteArticle(articleNo);
	}

	@Override
	public void likeArticle(String user_id, int articleno) throws Exception {
		boardMapper.likeArticle(user_id, articleno);
		
	}

	@Override
	public void likeHit(int articleno) throws Exception {
		boardMapper.likeHit(articleno);
		
	}

	@Override
	public boolean check(String user_id, int articleno) throws Exception {
		if(boardMapper.check(user_id, articleno)<=1) return true;
		return false;
	}



}
