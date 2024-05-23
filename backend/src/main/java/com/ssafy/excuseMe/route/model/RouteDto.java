package com.ssafy.excuseMe.route.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "RouteDto : 여행경로정보", description = "저장한 여행 경로의 정보를 나타낸다.")
public class RouteDto {
	
	private String user_id;
	private int id, len;
	private String title;
	private List<Integer> list;
	
}
