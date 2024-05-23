package com.ssafy.excuseMe.route.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.excuseMe.route.model.RouteDto;
import com.ssafy.excuseMe.tour.model.TourDto;

@Mapper
public interface RouteMapper {
	
	void addRoute(RouteDto routeDto) throws SQLException;
	void addRouteList(Map<String,Object> params) throws SQLException;
	
	List<RouteDto> myRouteList(String userId) throws SQLException;
	
	RouteDto getRoute(int routeId) throws SQLException;
	List<TourDto> getTourList(int routeId) throws SQLException;
	
	void deleteTourList(int routeId) throws SQLException;
	void deleteRoute(int routeId) throws SQLException;

}
