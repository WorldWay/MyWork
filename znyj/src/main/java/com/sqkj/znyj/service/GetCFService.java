package com.sqkj.znyj.service;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.sqkj.znyj.dao.CFDao;
import com.sqkj.znyj.dao.CFYPDao;
import com.sqkj.znyj.hisdao.CheckDao;
import com.sqkj.znyj.model.CFXX;
import com.sqkj.znyj.model.CFYP;
import com.sqkj.znyj.model.PD;
import com.sqkj.znyj.serviceimpl.IGetCFService;
import com.sqkj.znyj.tools.Orders;
import com.sqkj.znyj.tools.Tool;

@Service
public class GetCFService implements IGetCFService {

	private final static Log log = LogFactory.getLog(GetCFService.class);

	@Autowired
	private CFDao cfdao;

	@Autowired
	private CFYPDao cfypdao;
	
	@Autowired
	private CheckDao checkdao;

	@Override
	public boolean QYstart(JSONObject json) {
		try {
			JSONObject obj = new JSONObject();
			json.put("RGWCZT", 2);
			CFXX hascf = cfdao.getQYCF(Tool.Json2Map(json));
			if (hascf != null) return false;			
			json.put("RGWCZT", 1);
			CFXX cf = cfdao.getQYCF(Tool.Json2Map(json));
			if (cf!=null){
				json.put("CFXH", cf.getCFXH().trim());
				obj.put("medcf", cf.getCFXH().trim());
				obj.put("docname", cf.getHZMZ().trim());				
				List<CFYP> list = cfypdao.getCFYPList(Tool.Json2Map(json));
				if (list != null){
					for (Iterator<CFYP> i = list.iterator(); i.hasNext();){
						CFYP cfyp = i.next();
						obj.put("mednum", cfyp.getYPSL());
						obj.put("ip", cfyp.getZJQIP());
						obj.put("addr", cfyp.getPMDZ());
						json.put("YPBH", cfyp.getYPBH().trim());
						PD yp = checkdao.getPDbyId(Tool.Json2Map(json));
						if (yp!=null){
							obj.put("medtotal", yp.getYPSL());
							obj.put("orderType", "52");
							Orders.sendOrder(obj);
						}
					}
					//更新处方状态
					cf.setRGWCZT(2);
					json = JSONObject.fromObject(cf);
					System.out.println(json);
					cfdao.updateQYCF(Tool.Json2Map(json));
					
					MyEndTask endtask = new MyEndTask(json,cfdao);
					Tool.endtpool.schedule(endtask,  5 * Tool.delayTime, TimeUnit.SECONDS);
					
					return true;
				}
			}			
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}

	@Override
	public boolean QYend(JSONObject json) {
		try {
			JSONObject obj = new JSONObject();
			json.put("RGWCZT", 2);
			CFXX cf = cfdao.getQYCF(Tool.Json2Map(json));
			if (cf!=null){
				obj.put("medcf", cf.getCFXH().trim());
				json.put("CFXH", cf.getCFXH().trim());
				List<CFYP> list = cfypdao.getCFYPList(Tool.Json2Map(json));
				if (list != null){
					for (Iterator<CFYP> i = list.iterator(); i.hasNext();){
						CFYP cfyp = i.next();
						obj.put("ip", cfyp.getZJQIP());
						obj.put("addr", cfyp.getPMDZ());
						obj.put("orderType", "54");
						Orders.sendOrder(obj);
					}
				}
				//更新处方状态
				cf.setRGWCZT(3);
				json = JSONObject.fromObject(cf);
				cfdao.updateQYCF(Tool.Json2Map(json));
				//if (QYstart(new JSONObject()))
				return true;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}
	private static class MyEndTask implements Runnable {

		private JSONObject json;
		private CFDao cfDao;

		public MyEndTask(JSONObject json,CFDao cfdao) {
			this.json = json;
			this.cfDao = cfdao;
		}

		@Override
		public void run() {
			System.out.println(1);
			json.put("RGWCZT", 3);
			cfDao.updateQYCF(Tool.Json2Map(json));
		}
	}
}
