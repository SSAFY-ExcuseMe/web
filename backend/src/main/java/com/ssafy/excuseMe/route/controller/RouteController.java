package com.ssafy.excuseMe.route.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ssafy.excuseMe.route.model.RouteDto;
import com.ssafy.excuseMe.route.service.RouteService;
import com.ssafy.excuseMe.tour.model.TourDto;
import com.ssafy.excuseMe.util.JWTUtil;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/route")
@CrossOrigin("*")
public class RouteController {
	
	private RouteService routeService;
	private final JWTUtil jwtUtil;

	
	public RouteController(RouteService routeService, JWTUtil jwtUtil) {
		this.routeService = routeService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping
	public ResponseEntity<?> addTour(
			@RequestBody @Parameter(description = "등록 경로 정보.", required = true) RouteDto routeDto, 
			HttpServletRequest request) {
		try {
			String userId = getUserIdFromToken(request.getHeader("Authorization"));
			routeDto.setUser_id(userId);
			routeService.addRoute(routeDto);
			routeService.addRouteList(routeDto.getId(), routeDto.getList());
//			return ResponseEntity.ok().build();
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping
	public Map<String,Object> myRouteList(HttpServletRequest request){
		Map<String, Object> data = new HashMap<>();
		String userId = getUserIdFromToken(request.getHeader("Authorization"));
		List<RouteDto> list;
		try {
			list = routeService.myRouteList(userId);
			data.put("routeDto", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
		
	}
	
	@GetMapping("/{routeId}")
	public Map<String, Object> getRoute(
			@PathVariable("routeId") @Parameter(name = "routeId", description = "얻어올 경로 ID.", required = true) int routeId) 
					throws Exception{
		
		Map<String, Object> data = new HashMap<>();
		RouteDto routedto = routeService.getRoute(routeId);
		List<TourDto> list = routeService.getTourList(routeId);
		data.put("routeDto", routedto); 
		data.put("tourList", list);
		return data;
	}
	
	@DeleteMapping("/{routeId}")
	public ResponseEntity<String> deleteRoute(@PathVariable("routeId") @Parameter(name = "routeId", description = "살제할 경로의 번호.", required = true) int routeId) throws Exception {
		routeService.deleteTourList(routeId);
		routeService.deleteRoute(routeId);
		return ResponseEntity.ok().build();

	}

	
	private ResponseEntity<?> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private String getUserIdFromToken(String token) {
	    // Extract userId from the token using your JWTUtil or any JWT parsing library
	    return jwtUtil.getUserId(token);
	}
	

}
