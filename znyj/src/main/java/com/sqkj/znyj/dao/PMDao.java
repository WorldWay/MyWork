package com.sqkj.znyj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sqkj.znyj.model.PM;

@Repository
public interface PMDao {

	public List<PM> getPMList(Map<String, Object> map);

	public PM getPMById(Map<String, Object> map);

	public int getPMCount(Map<String, Object> map);

	public int addPM(Map<String, Object> map);

	public int updatePM(Map<String, Object> map);

	public int deletePM(Map<String, Object> map);
	
	public int deletePMbyZJQ(Map<String, Object> map);

}
