package com.sqkj.znyj.thread;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sqkj.znyj.dao.CFDao;
import com.sqkj.znyj.dao.CFYPDao;
import com.sqkj.znyj.hisdao.YPXXDao;
import com.sqkj.znyj.model.CFXX;
import com.sqkj.znyj.model.CFYP;
import com.sqkj.znyj.model.YPXX;
import com.sqkj.znyj.tools.Orders;
import com.sqkj.znyj.tools.Tool;

@Component
public class Init implements InitializingBean {

	private final static Log log = LogFactory.getLog(Init.class);
	
	@Autowired
	private CFDao cfdao;

	@Autowired
	private CFYPDao cfypdao;
	
	@Autowired
	private YPXXDao ypxxdao;

	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	@SuppressWarnings("unchecked")
	@Scheduled(fixedRate=100)
	public void QYstart() {
		try {
			JSONObject json = new JSONObject();
			JSONObject obj = new JSONObject();
			json.put("RGWCZT", 2);
			CFXX hascf = cfdao.getQYCF(Tool.Json2Map(json));
			if (hascf != null) {
				json.put("CFXH", hascf.getCFXH().trim());
				obj.put("medcf", hascf.getCFXH().trim());
				obj.put("docname", hascf.getHZMZ().trim());
				List<CFYP> list = cfypdao.getCFYPList(Tool.Json2Map(json));
				if (list != null & list.size() != 0) {
					for (Iterator<CFYP> i = list.iterator(); i.hasNext();) {
						CFYP cfyp = i.next();
						if (cfyp.getFYCS() > 0)
							continue;
						obj.put("mednum", cfyp.getYPSL());
						obj.put("ip", cfyp.getZJQIP());
						obj.put("addr", cfyp.getPMDZ());
						json.put("YPBH", cfyp.getYPBH().trim());
						YPXX yp = ypxxdao.getYPXXbyID(Tool.Json2Map(json));
						if (yp != null) {
							obj.put("medtotal", yp.getYPSL());
							obj.put("orderType", "52");
							Orders.sendOrder(obj);
							log.warn("处方发送中-------------------------------------------"+JSONObject.fromObject(hascf));
						}
					}
				}
				return ;			
			}
			json.put("RGWCZT", 1);
			CFXX cf = cfdao.getQYCF(Tool.Json2Map(json));
			if (cf!=null){
				json.put("CFXH", cf.getCFXH().trim());
				obj.put("medcf", cf.getCFXH().trim());
				obj.put("docname", cf.getHZMZ().trim());				
				List<CFYP> list = cfypdao.getCFYPList(Tool.Json2Map(json));
				if (list != null & list.size() != 0){
					for (Iterator<CFYP> i = list.iterator(); i.hasNext();){
						CFYP cfyp = i.next();
						obj.put("mednum", cfyp.getYPSL());
						obj.put("ip", cfyp.getZJQIP());
						obj.put("addr", cfyp.getPMDZ());
						json.put("YPBH", cfyp.getYPBH().trim());
						YPXX yp = ypxxdao.getYPXXbyID(Tool.Json2Map(json));
						if (yp!=null){
							obj.put("medtotal", yp.getYPSL());
							obj.put("orderType", "52");
							Orders.sendOrder(obj);
							log.warn("处方发送中-------------------------------------------"+JSONObject.fromObject(cf));
						}
					}
					//更新处方状态
					cf.setRGWCZT(2);
					json = JSONObject.fromObject(cf);
					cfdao.updateQYCF(Tool.Json2Map(json));
					
					MyEndTask endtask = new MyEndTask(json,cfdao);
					endtask.start();
				} else {
					cf.setRGWCZT(3);
					json = JSONObject.fromObject(cf);
					log.warn("处方结束："+json);
					cfdao.updateQYCF(json);
				}
			}			
		} catch (Exception e) {
			log.error(e);
		}
	}


}
