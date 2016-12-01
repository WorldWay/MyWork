package com.sqkj.znyj.mina;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sqkj.znyj.dao.CFDao;
import com.sqkj.znyj.dao.CFYPDao;
import com.sqkj.znyj.model.CFXX;
import com.sqkj.znyj.model.PM;
import com.sqkj.znyj.service.CheckService;
import com.sqkj.znyj.service.DeviceService;
import com.sqkj.znyj.tools.Orders;
import com.sqkj.znyj.tools.Tool;

@Repository
public class ServerHandler extends IoHandlerAdapter {

	private final static Log log = LogFactory.getLog(ServerHandler.class);
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private CheckService checkService;
	
	@Autowired
	private CFDao cfdao;
	
	@Autowired
	private CFYPDao cfypdao;

	public ServerHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		log.debug("服务端收到信息-------------");

		if (message == null) return;
		byte[] buffer = null;
		try{
			buffer = (byte[]) message;
			if (buffer.length>1) log.info("接收信息："+Tool.bytes2HexString(buffer));
		}catch(Exception e){
			return;
		}
		//校验开始
		if (!Tool.JY(buffer)) return;
		//校验结束		
		try {
			JSONObject obj = new JSONObject();
			JSONObject json = new JSONObject();
			obj.put("addr", buffer[2]);
			obj.put("ip", session.getRemoteAddress().toString().substring(1));
			JSONArray arr = null;
			switch (buffer[4]) {
			case 0x68:
				obj.put("orderType", "58");
				switch (buffer[5]) {
				case 0x51:
					obj.put("type", 0x51);
					obj.put("ZJQIP", obj.getString("ip").toString());
					obj.put("PMDZ", obj.getInt("addr"));
					obj.put("isRight", 1);
					checkService.updatePD(obj);
					Orders.sendOrder(obj, 1);
					break;
				case 0x56:
					obj.put("type", 0x56);
					obj.put("ZJQIP", obj.getString("ip").toString());
					obj.put("PMDZ", obj.getInt("addr"));
					obj.put("isRight", 2);
					checkService.updatePD(obj);
					Orders.sendOrder(obj, 1);
					break;
				default:
					break;
				}
				break;
			case 0x69:
				byte[] pmid = new byte[7];
				System.arraycopy(buffer, 5, pmid, 0, 7);
				String PMID =Tool.bytes2HexString(pmid);
				int PMDZ=1;	

				//判断该屏幕是否分配过地址，若分配过则删除掉原来分配的地址				
				obj.put("PMID", PMID);
				int count = deviceService.getPMCount(obj);
				if (count>0)	deviceService.deletePM(obj);		
				
				//获取该中继器下所有的屏幕
				obj.put("ZJQIP", obj.getString("ip").trim());
				obj.remove("PMID");
				count = deviceService.getPMCount(obj);
				if (count>-1) {
					obj.put("start", 0);
					obj.put("length", count);
					arr = JSONArray.fromObject(deviceService.getPMList(obj));
				}				
				if (arr.contains(null)) arr = JSONArray.fromObject("[]");
				if (arr!=null){
					//获取该中继器下所有屏幕的地址
					boolean[] pow = new boolean[100];
					for (Iterator<JSONObject> i = arr.iterator();i.hasNext();){
						JSONObject j = i.next();
						pow[j.getInt("PMDZ")] = true;
					}
					//寻找目前未使用的最小地址号
					for (int i=1;i<pow.length;i++){
						if (!pow[i]){
							PMDZ = i;
							break;
						}
					}
					obj.put("PMID", PMID);
					obj.put("PMDZ", PMDZ);					
					obj.put("YPBH", "");
					deviceService.addPM(obj);
					
					obj.put("orderType", "5B");
					obj.put("idNum", PMID);
					obj.put("addr", PMDZ);
					obj.put("medTime", Tool.delayTime);
					Orders.sendOrder(obj);
				}
				break;
			case 0x62:
				obj.put("ZJQIP", obj.getString("ip").trim());
				obj.put("PMDZ", obj.getInt("addr"));
				PM pm = deviceService.getPMByDZ(obj);
				obj.put("YPBH", pm.getYPBH());
												
				json.put("RGWCZT", 2);
				CFXX hascf = cfdao.getQYCF(Tool.Json2Map(json));
				if (hascf != null){
					obj.put("CFBH", hascf.getCFBH());
					obj.put("FYCS", 1);		
					cfypdao.updateCFYP(obj);
				}
				
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.warn("接收信息"+Tool.bytes2HexString(buffer));
			log.error(e);
		}

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("------------服务端发消息到客户端---");
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {

		log.debug("移除一个session..." + session.getRemoteAddress().toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

		log.debug("远程session关闭了一个..." + session.getRemoteAddress().toString());

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		log.debug(session.getRemoteAddress().toString()
				+ "----------------------create");

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		log.debug(session.getServiceAddress() + "IDS");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// 保存客户端的会话session
		SessionMap sessionMap = SessionMap.newInstance();
		sessionMap.addSession(session.getRemoteAddress().toString()
				.substring(1), session);
		log.debug("连接打开：" + session.getLocalAddress());
		JSONObject json = new JSONObject();
		json.put("ZJQIP", session.getRemoteAddress().toString().substring(1));
		json.put("ZJQMC", "");
		int count = deviceService.getZJQCount(json);
		if (count == 0)	deviceService.addZJQ(json);
		else log.warn("中继器已添加");
	}

}
