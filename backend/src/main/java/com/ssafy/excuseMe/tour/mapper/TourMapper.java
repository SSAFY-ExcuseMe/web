package com.ssafy.excuseMe.tour.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.excuseMe.tour.model.TourDto;

@Mapper
public interface TourMapper {
	List<TourDto> listTour(Map<String, Object> map);
	TourDto getTour(int tourId) throws SQLException;
	
}
