package com.ssafy.excuseMe.tour.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.excuseMe.board.model.BoardDto;
import com.ssafy.excuseMe.tour.model.TourDto;
import com.ssafy.excuseMe.tour.service.TourService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/tour")
@CrossOrigin("*")
public class TourController {
	private final TourService tourService;

	public TourController(TourService tourService) {
		this.tourService = tourService;
	}
	
	@GetMapping
	public Map<String,Object> listTour(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "region", required = false) String region,
			@RequestParam(value = "page", defaultValue = "1") int page
			){
		Map<String,Object> data = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		// Decode parameters
	    keyword = keyword != null ? URLDecoder.decode(keyword, StandardCharsets.UTF_8) : null;
	    type = type != null ? URLDecoder.decode(type, StandardCharsets.UTF_8) : null;
	    region = region != null ? URLDecoder.decode(region, StandardCharsets.UTF_8) : null;
		map.put("keyword", keyword);
		map.put("type", type);
		map.put("region",region);
		map.put("page", (page-1)*20);
		List<TourDto> list = tourService.listTour(map);
		System.out.println(list);
		data.put("totalCount", list.size());
		data.put("tourList", list);
		return data;
	}
	
	@GetMapping("/{tourId}")
	public ResponseEntity<TourDto> getTour(
			@PathVariable("tourId") @Parameter(name = "tourId", description = "얻어올 여행 ID.", required = true) int tourId)
			throws Exception {
		return new ResponseEntity<TourDto>(tourService.getTour(tourId), HttpStatus.OK);
	}
}
