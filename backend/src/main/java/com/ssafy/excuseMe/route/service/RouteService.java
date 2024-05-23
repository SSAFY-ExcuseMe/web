package com.ssafy.excuseMe.route.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.excuseMe.route.model.RouteDto;
import com.ssafy.excuseMe.tour.model.TourDto;

public interface RouteService {
	
	void addRoute(RouteDto routeDto) throws Exception;
	void addRouteList(int routeId, List<Integer> list) throws Exception;
	
	List<RouteDto> myRouteList(String userId) throws Exception;
	
	RouteDto getRoute(int routeId) throws Exception;
	List<TourDto> getTourList(int routeId) throws Exception;
	
	void deleteTourList(int routeId) throws SQLException;
	void deleteRoute(int routeId) throws SQLException;

}
