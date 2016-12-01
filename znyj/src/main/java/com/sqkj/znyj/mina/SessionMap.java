package com.sqkj.znyj.mina;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.sqkj.znyj.tools.Tool;

import net.sf.json.*;

public class SessionMap {
	private final static Log log = LogFactory.getLog(SessionMap.class);

	private static SessionMap sessionMap = null;

	private Map<String, IoSession> map = new HashMap<String, IoSession>();

	// 构造私有化 单例
	private SessionMap() {
	}

	/**
	 * @Description: 获取唯一实例
	 * @author way
	 * 
	 */
	public static SessionMap newInstance() {
		log.debug("SessionMap单例获取---");
		if (sessionMap == null) {
			sessionMap = new SessionMap();
		}
		return sessionMap;
	}

	/**
	 * @Description: 保存session会话
	 * @author way
	 * 
	 */
	public void addSession(String key, IoSession session) {
		log.debug("保存会话到SessionMap单例---key=" + key);
		this.map.put(key, session);
	}

	/**
	 * @Description: 根据key查找缓存的session
	 * @author way
	 * 
	 */
	public IoSession getSession(String key) {
		log.debug("获取会话从SessionMap单例---key=" + key);
		return this.map.get(key);
	}

	/**
	 * @Description: 根据key释放缓存的session
	 * @author way
	 * 
	 */
	public void delSession(String key) {
		log.debug("释放会话从SessionMap单例---key=" + key);
		this.map.remove(key);
	}

	/**
	 * @Description: 获取当前连接的ip列表
	 * @author way
	 * 
	 */
	public JSONArray getList() {
		JSONArray arr = new JSONArray();
		for (Map.Entry<String, IoSession> entry : map.entrySet()) {
			arr.add(JSONObject.fromObject("{'ip':'" + entry.getKey() + "'}"));
		}
		return arr;
	}


	/**
	 * @Description: 发送消息到客户端
	 * @author way
	 * @throws InterruptedException 
	 * 
	 */
	public synchronized void send(String ip, byte[] message) throws InterruptedException {
		try {
			IoSession session = getSession(ip);

			if (session == null) {
				return;
			}
			Thread.sleep(100);
			log.info(Tool.bytes2HexString(message));
			session.write(IoBuffer.wrap(message));
		} catch (Exception e) {
			log.error(message + "======================" + e);
		}
	}
	
	
	/**
	 * @Description: 指定设备是否在线
	 * @author way
	 * 
	 */
	public boolean containsKey(String key){
		boolean result = map.containsKey(key);
		return result;	
	}

}
