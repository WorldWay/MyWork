package com.sqkj.znyj.action;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqkj.znyj.mina.SessionMap;
import com.sqkj.znyj.model.PD;
import com.sqkj.znyj.service.CheckService;
import com.sqkj.znyj.tools.Orders;

@Controller
@RequestMapping("/pages")
public class CheckAction {
	
	private final static Log log = LogFactory.getLog(CheckAction.class);
	
	@Autowired
	private CheckService checkService;
	
	/**
	 * 获取盘点结果列表
	 * 
	 * @param keywords
	 * @param draw
	 * @param start
	 * @param length
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getChecks.json",method = RequestMethod.POST)
	public JSONObject getChecks(@RequestParam(required = false,value="search[value]") String keywords, 
			  @RequestParam(required = false) String draw,  
		      @RequestParam(required = false) Integer start,  
		      @RequestParam(required = false) Integer length){
		JSONArray arr = new JSONArray();
		//获取数据源总数
		JSONObject json = new JSONObject();
		json.put("keywords", keywords);
		int total = checkService.getPDCount(json);
		//获取数据源
		json.put("start", start);
		json.put("length", Math.min(length, total-start));		
		arr = JSONArray.fromObject(checkService.getPDList(json));
		if (arr.contains(null)) arr = JSONArray.fromObject("[]");
		//为数据源添加按钮组
		if (!arr.isEmpty())
		for (@SuppressWarnings("unchecked")
		Iterator<JSONObject> i = arr.iterator();i.hasNext();){
			JSONObject j = i.next();
			PD pd = new PD(j);
			switch (pd.getIsRight()) {
			case "正确":
				j.put("isRight", "<div class='bg-green' style='width:100%;height:25px' align='center'>正确</div>");		
				break;
			case "错误":
				j.put("isRight", "<div class='bg-red' style='width:100%;height:25px' align='center'>错误</div>");		
				break;
			default:
				j.put("isRight", "<div class='bg-white' style='width:100%;height:25px' align='center'>未确认</div>");		
				break;
			}
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
	 * 开始盘点
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/PDstart.json",method = RequestMethod.POST)
	public JSONObject PDstart(){
		JSONObject obj = new JSONObject();
		//初始化盘点状态
		boolean reset = checkService.resetPD();
		if (!reset) {
			obj.put("msg", "初始化数据失败，请联系管理员");
			obj.put("data", false);
			return obj;
		}				
		//发送盘点命令
		JSONArray arr =SessionMap.newInstance().getList();
		for(@SuppressWarnings("unchecked")
		Iterator<JSONObject> i=arr.iterator();i.hasNext();){
			JSONObject j = i.next();
			j.put("orderType", "58");
			Orders.sendOrder(j);
		}
		obj.put("data", true);
		return obj;		
	}
	
	/**
	 * 结束盘点
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/PDend.json",method = RequestMethod.POST)
	public JSONObject PDend(){
		JSONObject obj = new JSONObject();
		JSONArray arr =SessionMap.newInstance().getList();
		for(@SuppressWarnings("unchecked")
		Iterator<JSONObject> i=arr.iterator();i.hasNext();){
			JSONObject j = i.next();
			j.put("orderType", "5A");
			Orders.sendOrder(j);
		}
		obj.put("data", true);
		return obj;		
	}
}
