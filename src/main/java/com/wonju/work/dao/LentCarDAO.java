package com.wonju.work.dao;

import java.util.List;

import com.wonju.work.dto.LentcarDTO;

public interface LentCarDAO {
	List<LentcarDTO> selectLentCar(int begin, int end) throws Exception;
	int insertLentCar(LentcarDTO param) throws Exception;
	int deleteLentCar() throws Exception;
	int totalCount() throws Exception;
	int searchtotalCount(String subject, String keyword) throws Exception;
	List<LentcarDTO> searchLentCar(int begin, int end, String subject, String keyword) throws Exception;

}
