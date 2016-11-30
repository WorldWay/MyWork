package com.sqkj.znyj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sqkj.znyj.model.CFYP;

@Repository
public interface CFYPDao {

	public List<CFYP> getCFYPList(Map<String, Object> map);
	
}
