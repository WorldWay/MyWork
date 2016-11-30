package com.sqkj.znyj.action;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import net.sf.json.*;
import com.sqkj.znyj.mina.SessionMap;
import com.sqkj.znyj.service.HisService;
import com.sqkj.znyj.tools.Orders;
import com.sqkj.znyj.tools.Tool;

@Controller
@RequestMapping("/pages")
public class TestAction {

	@Autowired
	private HisService hisservice;

	@ResponseBody
	@RequestMapping(value = "/sendOrder.json", method = RequestMethod.POST)
	public String sendOrder(HttpServletRequest req,
			@RequestParam(value = "text") String text,
			@RequestParam(value = "ip") String ip, Model model)
			throws InterruptedException {
		SessionMap.newInstance().send(ip, text);
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/sendMedInfo.json", method = RequestMethod.POST)
	public String sendMedInfo(HttpServletRequest req,
			@RequestParam(value="json") String json, Model model)
			throws InterruptedException {
		JSONObject obj = JSONObject.fromObject(json);
		try{
			Tool.delayTime = obj.getInt("delaytime");
		} catch(Exception e) {
			Tool.delayTime = 0;
		}
		Orders.sendOrder(obj);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getIps.json", method = RequestMethod.GET)
	public JSONArray getIps(HttpServletRequest req, Model model) {
		
		JSONArray arr = SessionMap.newInstance().getList();
		return arr;
	}

	
	
}
