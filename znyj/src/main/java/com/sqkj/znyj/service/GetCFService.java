package com.sqkj.znyj.service;

import java.util.Iterator;
import java.util.List;
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
	public void QYend(JSONObject json) {
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
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

}
