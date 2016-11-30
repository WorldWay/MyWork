package com.sqkj.znyj.serviceimpl;

import java.util.List;

import net.sf.json.JSONObject;

import com.sqkj.znyj.model.YPXX;

public interface IHisService {
	
	public List<YPXX> getYPXXList();
	
	public YPXX getYPXXbyID(JSONObject json);
}
