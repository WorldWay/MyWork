package com.sqkj.znyj.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqkj.znyj.dao.PMDao;
import com.sqkj.znyj.dao.ZJQDao;
import com.sqkj.znyj.hisdao.CheckDao;
import com.sqkj.znyj.hisdao.YPXXDao;
import com.sqkj.znyj.model.PM;
import com.sqkj.znyj.model.YPXX;
import com.sqkj.znyj.model.ZJQ;
import com.sqkj.znyj.serviceimpl.IDeviceService;
import com.sqkj.znyj.tools.Orders;
import com.sqkj.znyj.tools.Tool;

@Service
public class DeviceService implements IDeviceService {

	private static Log log = LogFactory.getLog(DeviceService.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private ZJQDao zjqdao;
	
	@Autowired
	private PMDao pmdao;
	
	@Autowired
	private YPXXDao ypxxdao;
	
	@Autowired
	private CheckDao checkdao;

	//中继器Begin
	@Override
 	public List<ZJQ> getZJQList(JSONObject json) {
		try {
			Map<String, Object> map = Tool.Json2Map(json);
			List<ZJQ> list = zjqdao.getZJQList(map);
			if (list != null & list.size() != 0)
				return list;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		return null;
	}

	@Override
	public ZJQ getZJQById(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			ZJQ result = zjqdao.getZJQById(map);
			if (result != null)
				return result;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		return null;
	}

	@Override
	public int getZJQCount(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = zjqdao.getZJQCount(map);
			return result;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}		
		return -1;
	}

	@Override
	public boolean addZJQ(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = zjqdao.addZJQ(map);
			if (result != 0)
				return true;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		return false;
	}

	@Override
	public boolean updateZJQ(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = zjqdao.updateZJQ(map);
			if (result != 0)
				return true;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		
		return false;
	}

	@Override
	public boolean deleteZJQ(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = zjqdao.deleteZJQ(map);
			if (result != 0) {
				result = pmdao.deletePMbyZJQ(map);
				if (result != 0)
					return true;
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		
		return false;
	}
	//中继器End
	
	//屏幕Begin
	@Override
	public List<PM> getPMList(JSONObject json) {
		try {
			Map<String, Object> map = Tool.Json2Map(json);
			List<PM> list = pmdao.getPMList(map);
			if (list != null & list.size() != 0)
				return list;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		return null;
	}

	@Override
	public PM getPMById(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			PM result = pmdao.getPMById(map);
			if (result != null)
				return result;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		return null;
	}

	@Override
	public int getPMCount(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = pmdao.getPMCount(map);
			return result;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}		
		return -1;
	}

	@Override
	public boolean addPM(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = pmdao.addPM(map);
			if (result != 0)
				return true;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		return false;
	}

	@Override
	@SuppressWarnings({ "deprecation", "unchecked" })
	public boolean updatePM(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = pmdao.updatePM(map);
			if (result != 0){
				String YPBH = json.getString("YPBH").trim();
				if (YPBH !=null & YPBH!="") {
					//获取药品信息
					JSONObject ypjson = new JSONObject();
					ypjson.put("YPBH", YPBH);
					YPXX ypxx = ypxxdao.getYPXXbyID(ypjson);
					if (ypxx != null){									
						int count = checkdao.getPDbyId(Tool.Json2Map(ypjson)).getYPSL();
						//发送药品名称、效期、剂型、库位和数量
						JSONObject obj = new JSONObject();
						
						obj.put("addr", json.getString("PMDZ").trim());
						obj.put("ip",json.getString("ZJQIP").trim());
						obj.put("medcf", "");
						obj.put("medtotal", count);
						obj.put("orderType", "52");
						Orders.sendOrder(obj);
						obj.put("orderType", "54");
						Orders.sendOrder(obj);
						obj.put("orderType", "53");
						obj.put("medpos1", json.getInt("KW1"));
						obj.put("medpos2", json.getInt("KW2"));
						obj.put("medstan", ypxx.getFL());
						obj.put("medname", ypxx.getYPMC());
						Date date = sdf.parse(ypxx.getXQ());
						obj.put("medy", date.getYear()%100);
						obj.put("medm", date.getMonth());
						Orders.sendOrder(obj);
						obj.put("orderType", "56");
						Orders.sendOrder(obj);
					}
				}
				return true;
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
		}		
		return false;
	}

	@Override
	public boolean deletePM(JSONObject json) {

		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = pmdao.deletePM(map);
			if (result != 0) 
				return true;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}				
		return false;
	}	
	//屏幕End
}
