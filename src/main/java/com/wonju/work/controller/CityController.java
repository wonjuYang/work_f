package com.wonju.work.controller;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wonju.work.dao.CityDAO;
import com.wonju.work.dto.CityDTO;

@RestController
@MapperScan(basePackages="com.wonju.work.dao")
public class CityController {

	
	@Autowired
	private CityDAO cityDAO;
	
	@RequestMapping("/cities")
	public List<CityDTO> Cities(@RequestParam(value="country", defaultValue ="")String name) throws Exception{
		
		final CityDTO param = new CityDTO();
		final List<CityDTO> cityList = cityDAO.selectCities(param);
		System.out.println(cityList);
		return cityList;
		
		
	}
}
