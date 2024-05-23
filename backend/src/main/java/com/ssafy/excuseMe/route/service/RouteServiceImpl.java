package com.ssafy.excuseMe.route.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.excuseMe.route.mapper.RouteMapper;
import com.ssafy.excuseMe.route.model.RouteDto;
import com.ssafy.excuseMe.tour.model.TourDto;

@Service
public class RouteServiceImpl implements RouteService {
	
	private final RouteMapper routeMapper;
	
	public RouteServiceImpl(RouteMapper routeMapper) {
		this.routeMapper = routeMapper;
	}

	@Override
	public void addRoute(RouteDto routeDto) throws Exception {
		routeMapper.addRoute(routeDto);
	}

	@Override
	public void addRouteList(int routeId, List<Integer> list) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("routeId", routeId);
		params.put("list", list);
		routeMapper.addRouteList(params);
	}

	@Override
	public List<RouteDto> myRouteList(String userId) throws Exception {
		return routeMapper.myRouteList(userId);
	}

	@Override
	public RouteDto getRoute(int routeId) throws Exception {
		// TODO Auto-generated method stub
		return routeMapper.getRoute(routeId);
	}

	@Override
	public List<TourDto> getTourList(int routeId) throws Exception {
		return routeMapper.getTourList(routeId);
	}

	@Override
	public void deleteTourList(int routeId) throws SQLException {
		routeMapper.deleteTourList(routeId);
	}

	@Override
	public void deleteRoute(int routeId) throws SQLException {
		routeMapper.deleteRoute(routeId);
	}

	

}
