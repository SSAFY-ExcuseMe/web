package com.ssafy.excuseMe.board.controller;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.excuseMe.board.model.BoardDto;
import com.ssafy.excuseMe.board.model.BoardListDto;
import com.ssafy.excuseMe.board.model.CommentDto;
import com.ssafy.excuseMe.board.service.BoardService;
import com.ssafy.excuseMe.tour.model.TourDto;
import com.ssafy.excuseMe.util.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Tag(name = "게시판 컨트롤러", description = "게시판에 글을 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
@Slf4j
@CrossOrigin("*")
public class BoardController {

	private final BoardService boardService;
	private final JWTUtil jwtUtil;

	public BoardController(BoardService boardService, JWTUtil jwtUtil) {
		super();
		this.boardService = boardService;
		this.jwtUtil = jwtUtil;
	}
	
	@Operation(summary = "게시판 글작성", description = "새로운 게시글 정보를 입력한다.")
	@PostMapping
	public ResponseEntity<?> writeArticle(
			@RequestBody @Parameter(description = "작성글 정보.", required = true) BoardDto boardDto,
			HttpServletRequest request) {
		log.info("writeArticle boardDto - {}", boardDto);
		try {
			boardDto.setUser_id(getUserIdFromToken(request.getHeader("Authorization")));
			boardService.writeArticle(boardDto);
//			return ResponseEntity.ok().build();
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Operation(summary = "게시판 글목록", description = "모든 게시글의 정보를 반환한다.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "게시글목록 OK!!"),
			@ApiResponse(responseCode = "404", description = "페이지없어!!"),
			@ApiResponse(responseCode = "500", description = "서버에러!!") })
	@GetMapping
	public Map<String,Object> listArticle(
			@RequestParam(value = "page", defaultValue = "1") int page) throws Exception {
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("page", (page-1)*20);
		BoardListDto list = boardService.listArticle(map);
		System.out.println(list);
		data.put("totalCount", list.getArticles().size());
		data.put("tourList", list);
		return data;
		
	}
	
	@GetMapping("/myArticle")
	public Map<String,Object> mylistArticle(
			@RequestParam(value = "page", defaultValue = "1") int page,
			HttpServletRequest request) throws Exception {
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("page", (page-1)*20);
		map.put("user_id",getUserIdFromToken(request.getHeader("Authorization")));
		BoardListDto list = boardService.mylistArticle(map);
		System.out.println(list);
		data.put("totalCount", list.getArticles().size());
		data.put("tourList", list);
		return data;
	}
	
	
	@Operation(summary = "게시판 글보기", description = "글번호에 해당하는 게시글의 정보를 반환한다.")
	@GetMapping("/{articleno}")
	public ResponseEntity<BoardDto> getArticle(
			@PathVariable("articleno") @Parameter(name = "articleno", description = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		log.info("getArticle - 호출 : " + articleno);
		boardService.updateHit(articleno);
		BoardDto bd = boardService.getArticle(articleno);
		bd.setComments(boardService.getComment(articleno));
		return new ResponseEntity<BoardDto>(bd, HttpStatus.OK);
	}
	
	@Operation(summary = "수정 할 글 얻기", description = "글번호에 해당하는 게시글의 정보를 반환한다.")
	@GetMapping("/modify/{articleno}")
	public ResponseEntity<BoardDto> getModifyArticle(
			@PathVariable("articleno") @Parameter(name = "articleno", description = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		log.info("getModifyArticle - 호출 : " + articleno);
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
	}
	
	@Operation(summary = "게시판 글수정", description = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.")
	@PutMapping("/{articleno}")
	public ResponseEntity<String> modifyArticle(
			@PathVariable("articleno") @Parameter(name = "articleno", description = "살제할 글의 글번호.", required = true) int articleno,
			@RequestBody @Parameter(description = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
		log.info("modifyArticle - 호출 {}", boardDto);
		boardDto.setId(articleno);
		boardService.modifyArticle(boardDto);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "게시판 글삭제", description = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.")
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") @Parameter(name = "articleno", description = "살제할 글의 글번호.", required = true) int articleno) throws Exception {
		log.info("deleteArticle - 호출");
		boardService.beforedeleteArticle(articleno);
		boardService.deleteArticle(articleno);
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/{articleno}/like")
	public ResponseEntity<?> likeArticle(@PathVariable("articleno") @Parameter(name = "articleno", description = "글의 번호.", required = true) int articleno,
			HttpServletRequest request) throws Exception{
		String user_id = getUserIdFromToken(request.getHeader("Authorization"));
		boardService.likeArticle(user_id,articleno);
		if(boardService.check(user_id,articleno))boardService.likeHit(articleno);
		return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/{articleno}/comment")
	public ResponseEntity<?> writeComment(
			@PathVariable("articleno") @Parameter(name = "articleno", description = "살제할 글의 글번호.", required = true) int articleno,
			@RequestBody @Parameter(description = "작성글 정보.", required = true) CommentDto commentDto, HttpServletRequest request) {
		try {
			commentDto.setPost_id(articleno);
			commentDto.setUser_id(getUserIdFromToken(request.getHeader("Authorization")));
			boardService.writeComment(commentDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/comment/{commentno}")
	public ResponseEntity<String> deleteComment(@PathVariable("commentno") @Parameter(name = "commentno", description = "살제할 댓글의 번호.", required = true) int commentno) throws Exception {
		boardService.deleteComment(commentno);
		log.info("finsih");
		return ResponseEntity.ok().build();

	}

	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private String getUserIdFromToken(String token) {
	    return jwtUtil.getUserId(token);
	}
	
	
}