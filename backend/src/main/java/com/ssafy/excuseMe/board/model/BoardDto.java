package com.ssafy.excuseMe.board.model;


import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(title = "BoardDto : 게시글정보", description = "게시글의 상세 정보를 나타낸다.")
public class BoardDto {
	
	@Schema(description = "작성자 ID")
	String user_id;
	@Schema(description = "글번호")
	int id;
	@Schema(description = "제목")
	String title;
	@Schema(description = "내용")
	String contents;
	@Schema(description = "작성일")
	String created_date;
	@Schema(description = "조회수")
	int view;
	@Schema(description = "좋아요수")
	int like;
	@Schema(description = "사진위치")
	String img;
	@Schema(description = "댓글")
	List<CommentDto> comments;
	
	
}
