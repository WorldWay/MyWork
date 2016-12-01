package com.sqkj.znyj.action;

import java.util.Iterator;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.*;

import com.sqkj.znyj.mina.SessionMap;
import com.sqkj.znyj.model.PM;
import com.sqkj.znyj.service.DeviceService;
import com.sqkj.znyj.tools.Orders;
import com.sqkj.znyj.tools.Tool;

@Controller
@RequestMapping("/pages")
public class DeviceAction {

	@Autowired
	private DeviceService deviceService;
	
	//中继器Begin
	/**
	 * 获取中继器列表
	 * @param keywords
	 * @param draw
	 * @param start
	 * @param length
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getZJQs.json",method = RequestMethod.POST)
	public JSONObject getZJQs(@RequestParam(required = false,value="search[value]") String keywords, 
			  @RequestParam(required = false) String draw,  
		      @RequestParam(required = false) Integer start,  
		      @RequestParam(required = false) Integer length){
		JSONArray arr = new JSONArray();
		//获取数据源总数
		JSONObject json = new JSONObject();					
		json.put("keywords", keywords);
		int total = deviceService.getZJQCount(json);
		//获取数据源
		json.put("start", start);
		json.put("length", Math.min(length, total-start));		
		arr = JSONArray.fromObject(deviceService.getZJQList(json));
		if (arr.contains(null)) arr = JSONArray.fromObject("[]");
		//为数据源添加按钮组
		if (!arr.isEmpty())
		for (@SuppressWarnings("unchecked")
		Iterator<JSONObject> i = arr.iterator();i.hasNext();){
			JSONObject j = i.next();
			int id = j.getInt("id");
			String ZJQIP = j.getString("ZJQIP").trim();
			String ZJQMC = j.getString("ZJQMC").trim();
			j.put("btns", "<div class='btn-group'><a href='#edit_ZJQ' "
					+"data_id='"+id+"'"+"data_ZJQIP='"+ZJQIP+"'"+"data_ZJQMC='"+ZJQMC+"'"
					+" type='button' class='btn btn-default bg-blue' data-toggle='modal'><i class='fa fa-edit'>编辑</i></a>"
                    +"<a href='#remove_ZJQ' "
					+"data_id='"+id+"' "+"data_ZJQIP='"+ZJQIP+"'"
					+"type='button' class='btn btn-default bg-red' data-toggle='modal'><i class='fa fa-remove'>删除</i></a></div>");
				boolean isOn = SessionMap.newInstance().containsKey(ZJQIP);
				j.put("isOn", isOn);
		}		
		//发送数据到页面
		JSONObject obj = new JSONObject();
		obj.put("data", arr);
		obj.put("draw", draw);
		obj.put("recordsTotal", total);
		obj.put("recordsFiltered", total);		
		return obj;
	}
	
	/**
	 * 更新中继器数据
	 * @param jsonStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/upZJQ.json",method=RequestMethod.POST)
	public JSONObject upZJQ(@RequestParam(required = false,value="json") String jsonStr){
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject obj = new JSONObject();
		if (deviceService.updateZJQ(json)) obj.put("data", true);
		else obj.put("error", false);
		return obj;
	}
	
	/**
	 * 删除中继器数据
	 * @param jsonStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delZJQ.json",method=RequestMethod.POST)
	public JSONObject delZJQ(@RequestParam(required = false,value="json") String jsonStr){
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject obj = new JSONObject();
		String ip = json.getString("ZJQIP").trim();
		obj.put("data",false);
		if (SessionMap.newInstance().containsKey(ip)){
			IoSession session = SessionMap.newInstance().getSession(ip);
			SessionMap.newInstance().delSession(ip);
			session.closeNow();
		}
		if (deviceService.deleteZJQ(json)) obj.put("data",true);		
		return obj;
	}
	
	//中继器End
	
	//屏幕Begin
	
	/**
	 * 获取屏幕列表
	 * @param keywords
	 * @param draw
	 * @param start
	 * @param length
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getPMs.json",method = RequestMethod.POST)
	public JSONObject getPMs(@RequestParam(required = false,value="search[value]") String keywords, 
			  @RequestParam(required = false) String draw,  
		      @RequestParam(required = false) Integer start,  
		      @RequestParam(required = false) Integer length){
		JSONArray arr = new JSONArray();
		//获取数据源总数
		JSONObject json = new JSONObject();					
		json.put("keywords", keywords);
		int total = deviceService.getPMCount(json);
		//获取数据源
		json.put("start", start);
		json.put("length", Math.min(length, total-start));		
		arr = JSONArray.fromObject(deviceService.getPMList(json));
		if (arr.contains(null)) arr = JSONArray.fromObject("[]");
		//为数据源添加按钮组
		if (!arr.isEmpty())
		for (@SuppressWarnings("unchecked")
		Iterator<JSONObject> i = arr.iterator();i.hasNext();){
			JSONObject j = i.next();
			PM pm = new PM(j);
			j.put("btns", "<div class='btn-group'><a href='#edit_PM' "
					+"data_PMID='"+pm.getPMID()+"'"+"data_PMDZ='"+pm.getPMDZ()+"'"+"data_ZJQIP='"+pm.getZJQIP()+"'"+"data_YPBH='"+pm.getYPBH()+"'"+"data_KW1='"+pm.getKW1()+"'"+"data_KW2='"+pm.getKW2()+"'"
					+" type='button' class='btn btn-default bg-blue' data-toggle='modal'><i class='fa fa-edit'>编辑</i></a>"
                    +"<a href='#remove_PM' "
					+"data_PMID='"+pm.getPMID()+"' "
					+"type='button' class='btn btn-default bg-red' data-toggle='modal'><i class='fa fa-remove'>删除</i></a></div>");
				
		}		
		//发送数据到页面
		JSONObject obj = new JSONObject();
		obj.put("data", arr);
		obj.put("draw", draw);
		obj.put("recordsTotal", total);
		obj.put("recordsFiltered", total);		
		return obj;
	}
	
	/**
	 * 更新屏幕数据
	 * @param jsonStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/upPM.json",method=RequestMethod.POST)
	public JSONObject upPM(@RequestParam(required = false,value="json") String jsonStr){
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject obj = new JSONObject();
		if (deviceService.updatePM(json)) obj.put("data", true);
		else obj.put("error", false);
		return obj;
	}
	
	/** 
	 * 删除屏幕数据
	 * @param jsonStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delPM.json",method=RequestMethod.POST)
	public JSONObject delPM(@RequestParam(required = false,value="json") String jsonStr){
		JSONObject json = JSONObject.fromObject(jsonStr);
		JSONObject obj = new JSONObject();
		obj.put("data",false);
		if (deviceService.deletePM(json)) obj.put("data",true);		
		return obj;
	}
	
	/**
	 * 分配地址开始
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/PMstart.json",method=RequestMethod.POST)
	public JSONObject PMstart(){
		JSONObject obj = new JSONObject();		
		JSONArray arr =SessionMap.newInstance().getList();
		for(@SuppressWarnings("unchecked")
		Iterator<JSONObject> i=arr.iterator();i.hasNext();){
			JSONObject j = i.next();
			j.put("orderType", "59");
			Orders.sendOrder(j);
		}		
		obj.put("data",true);
		return obj;
	}
	
	/**
	 * 分配地址结束
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/PMend.json",method=RequestMethod.POST)
	public JSONObject PMend(){
		JSONObject obj = new JSONObject();		
		JSONArray arr =SessionMap.newInstance().getList();
		for(@SuppressWarnings("unchecked")
		Iterator<JSONObject> i=arr.iterator();i.hasNext();){
			JSONObject j = i.next();
			j.put("orderType", "5A");
			Orders.sendOrder(j);
		}		
		obj.put("data",true);
		return obj;
	}
	//屏幕End
	
	
	
}
