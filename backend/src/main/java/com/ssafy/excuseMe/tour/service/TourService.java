package com.ssafy.excuseMe.tour.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.excuseMe.tour.model.TourDto;

public interface TourService{
	List<TourDto> listTour(Map<String, Object> map);
	TourDto getTour(int tourId) throws Exception;
}
