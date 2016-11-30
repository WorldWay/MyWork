package com.sqkj.znyj.service;

import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sqkj.znyj.hisdao.YPXXDao;
import com.sqkj.znyj.model.YPXX;
import com.sqkj.znyj.serviceimpl.IHisService;
import com.sqkj.znyj.tools.Tool;

@Service
public class HisService implements IHisService{

	private final static Log log = LogFactory.getLog(HisService.class);
	
	@Autowired
	private YPXXDao dao;

	@Override
	public List<YPXX> getYPXXList() {
		List<YPXX> list = dao.getYPXXList();
		return list;
	}

	@Override
	public YPXX getYPXXbyID(JSONObject json) {
		try {
			Map<String, Object> map = Tool.Json2Map(json);
			YPXX result = dao.getYPXXbyID(map);
			if (result != null)
				return result;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		return null;
	}
	
	
}
