package com.ssafy.excuseMe.tour.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TourDto {
	
	int id;
	String name, addr, tel, img, type;
	float map_x, map_y;

}
