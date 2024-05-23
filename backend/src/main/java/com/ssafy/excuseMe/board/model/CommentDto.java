package com.ssafy.excuseMe.board.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {
	
	int id;
	int post_id;
	String contents;
	String user_id;
	String created_date;

}
