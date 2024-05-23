package com.ssafy.excuseMe.tour.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.excuseMe.tour.mapper.TourMapper;
import com.ssafy.excuseMe.tour.model.TourDto;
@Service
public class TourServiceImpl implements TourService{

	private final TourMapper tourMapper;
	
	public TourServiceImpl(TourMapper tourMapper) {
		this.tourMapper = tourMapper;
	}

	@Override
	@Transactional
	public List<TourDto> listTour(Map<String, Object> map) {
		 return tourMapper.listTour(map);
	}

	@Override
	public TourDto getTour(int tourId) throws Exception {
		return tourMapper.getTour(tourId);
	}

}
