package com.sqkj.znyj.thread;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sqkj.znyj.dao.CFDao;
import com.sqkj.znyj.tools.Tool;


public class MyEndTask extends Thread{

	private final static Log log = LogFactory.getLog(MyEndTask.class);
	
	private JSONObject json;

	private CFDao cfDao;

	public MyEndTask(JSONObject json,CFDao cfDao) {
		this.json = json;
		this.cfDao = cfDao;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000*Tool.delayTime);
		} catch (InterruptedException e) {
			log.error(e);
		}
		json.put("RGWCZT", 3);
		log.info(json);
		cfDao.updateQYCF(Tool.Json2Map(json));
	}
}
