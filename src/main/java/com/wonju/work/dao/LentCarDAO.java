package com.wonju.work.dao;

import java.util.List;

import com.wonju.work.dto.LentcarDTO;

public interface LentCarDAO {
	List<LentcarDTO> selectLentCar(LentcarDTO param) throws Exception;
	int insertLentCar(LentcarDTO param) throws Exception;

}
