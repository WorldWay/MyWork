package com.sqkj.znyj.thread;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sqkj.znyj.service.DeviceService;

import net.sf.json.JSONObject;

public class AddZJQTask extends Thread{

	private final static Log log = LogFactory.getLog(AddZJQTask.class);
	private DeviceService service;
	private JSONObject json;
	
	public AddZJQTask(DeviceService service,JSONObject json){
		this.service = service;
		this.json = json;
	}
	
	@Override
	public void run(){
		
		try {
			int count = service.getZJQCount(json);
			if (count == 0)
				service.addZJQ(json);
			else if(count > 0)
				log.warn("中继器已添加");
			else 
				log.error("请检查网络连接是否正常");
		} catch (Exception e) {
			log.warn(e);
		}
		
	}
}
