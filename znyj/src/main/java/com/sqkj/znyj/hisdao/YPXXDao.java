package com.sqkj.znyj.hisdao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sqkj.znyj.model.YPXX;


@Repository
public interface YPXXDao {
		
	public List<YPXX> getYPXXList();
	
	public YPXX getYPXXbyID(Map<String, Object> map);
}
