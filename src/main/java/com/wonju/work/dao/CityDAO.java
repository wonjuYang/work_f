package com.wonju.work.dao;

import java.util.List;

import com.wonju.work.dto.CityDTO;

public interface CityDAO {
	List<CityDTO> selectCities(CityDTO param) throws Exception;
}
