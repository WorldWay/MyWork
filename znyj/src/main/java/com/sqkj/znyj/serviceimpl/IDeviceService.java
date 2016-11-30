package com.sqkj.znyj.serviceimpl;

import java.util.List;

import net.sf.json.JSONObject;

import com.sqkj.znyj.model.PM;
import com.sqkj.znyj.model.ZJQ;

public interface IDeviceService {
	
	//中继器 Begin
	public List<ZJQ> getZJQList(JSONObject json);
	public ZJQ getZJQById(JSONObject json);
	public int getZJQCount(JSONObject json);
	public boolean addZJQ(JSONObject json);
	public boolean updateZJQ(JSONObject json);
	public boolean deleteZJQ(JSONObject json);
	//中继器 End
	
	//屏幕 Begin
	public List<PM> getPMList(JSONObject json);
	public PM getPMById(JSONObject json);
	public int getPMCount(JSONObject json);
	public boolean addPM(JSONObject json);
	public boolean updatePM(JSONObject json);
	public boolean deletePM(JSONObject json);
	//屏幕 End
}
