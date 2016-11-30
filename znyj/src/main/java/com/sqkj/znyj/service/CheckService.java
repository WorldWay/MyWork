package com.sqkj.znyj.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqkj.znyj.hisdao.CheckDao;
import com.sqkj.znyj.model.PD;
import com.sqkj.znyj.serviceimpl.ICheckService;
import com.sqkj.znyj.tools.Tool;

@Service
public class CheckService implements ICheckService {

	private final static Log log = LogFactory.getLog(CheckService.class);
	
	@Autowired
	private CheckDao checkdao;

	@Override
	public List<PD> getPDList(JSONObject json) {
		try {
			Map<String, Object> map = Tool.Json2Map(json);
			List<PD> list = checkdao.getPDList(map);
			if (list != null & list.size() != 0)
				return list;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		return null;
	}

	@Override
	public int getPDCount(JSONObject json) {
		
		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = checkdao.getPDCount(map);
			return result;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}		
		
		return -1;
	}

	@Override
	public boolean updatePD(JSONObject json) {		
		try {
			Map<String, Object> map = Tool.Json2Map(json);
			int result = checkdao.updatePD(map);
			if (result != 0)
				return true;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		
		return false;
	}

	@Override
	public boolean resetPD() {
		try {
			int result = checkdao.resetPD();
			if (result != 0)
				return true;
		} catch (Exception e) {
			log.error(e);
		} finally {
		}
		
		return false;
	}	
	
	
}
