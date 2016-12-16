package com.sqkj.znyj.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.sqkj.znyj.model.PD;

@Repository
public interface CheckDao {

	public PD getPDbyId(Map<String, Object> map);
	
	public List<PD> getPDList(Map<String, Object> map);

	public int getPDCount(Map<String, Object> map);

	public int updatePD(Map<String, Object> map);
	
	public int resetPD();
}
