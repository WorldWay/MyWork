package com.sqkj.znyj.serviceimpl;

import java.util.List;

import net.sf.json.JSONObject;

import com.sqkj.znyj.model.PD;

public interface ICheckService {

	public List<PD> getPDList(JSONObject json);
	
	public int getPDCount(JSONObject json);
	
	public boolean updatePD(JSONObject json);
	
	public boolean resetPD();
}
