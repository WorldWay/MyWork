package com.sqkj.znyj.action;

import net.sf.json.JSONObject;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sqkj.znyj.service.GetCFService;

@Controller
@RequestMapping(value="/pages")
public class GetCFAction {
	
	private final static Log log = LogFactory.getLog(GetCFAction.class);
	
	@Autowired
	private GetCFService cfservice;
	
	@ResponseBody
	@RequestMapping(value="/QYstart.json",method = RequestMethod.POST)
	public JSONObject QYstart(){
		JSONObject obj = new JSONObject();
		
		obj.put("data", cfservice.QYstart(new JSONObject()));
		
		return obj;
	}
	
	@ResponseBody
	@RequestMapping(value="/QYend.json",method = RequestMethod.POST)
	public JSONObject QYend(){
		JSONObject obj = new JSONObject();
		
		obj.put("data", cfservice.QYend(new JSONObject()));
		
		return obj;
	}
}
