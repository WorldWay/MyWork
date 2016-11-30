package com.sqkj.znyj.tools;

import java.nio.charset.Charset;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sqkj.znyj.mina.SessionMap;

import net.sf.json.JSONObject;

public class Orders {

	private final static Log log = LogFactory.getLog(Orders.class);
	private final static Random r = new Random();
	private static Charset ch = Charset.forName("gb2312");
	
	/**
	 * 向下位机传送药品总数和门诊或摆药单取药数量，
	 * 只传送药品总数时，除药品总数外都为0x00
	 * 
	 * @param obj
	 * @throws Exception
	 */
	private static void order52(JSONObject obj) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt(obj.getString("addr"),10) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x11, (byte) 0x52 });		
		if (obj.getString("medcf").equals("")||obj.getString("docname").equals("")||obj.getString("mednum").equals("")) {
			msg = Tool.byteUnion(msg, Tool.int2byte(0, 6));
			msg = Tool.byteUnion(msg, Tool.byteGet(new byte[]{}, 6));
			msg = Tool.byteUnion(msg, Tool.toBCD(0,2));
		} else {
			msg = Tool.byteUnion(msg, Tool.byteGet(Tool.str2bytes(obj.getString("medcf"), null), 1, 6));
			msg = Tool.byteUnion(msg, Tool.byteGet(obj.getString("docname").getBytes(ch), 6));
			msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("mednum"),2));
		}		
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medtotal"),2));
		msg = addjym(msg);		
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 向下位机传送药品名称及效期
	 * @param obj
	 * @throws Exception
	 */
	private static void order53(JSONObject obj) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt(obj.getString("addr"),10) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x13, (byte) 0x53 });
		msg = Tool.byteUnion(msg, Tool.byteGet(obj.getString("medname").getBytes(ch), 16));
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medy"),1));
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medm"),1));
		msg = addjym(msg);		
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 向下位机传送药品名称及效期(备用)
	 * @param obj
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static void order531(JSONObject obj) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt(obj.getString("addr"),10) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x1f, (byte) 0x53 });
		msg = Tool.byteUnion(msg, Tool.byteGet(obj.getString("medname").getBytes(ch), 16));
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medy"),1));
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medm"),1));
		msg = Tool.byteUnion(msg, Tool.byteGet(obj.getString("medstan").getBytes(ch), 10));
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medpos1"),1));
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medpos2"),1));
		msg = addjym(msg);		
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 向下位机传送药品剂型和库位
	 * @param obj
	 * @throws Exception
	 */
	private static void order56(JSONObject obj) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt(obj.getString("addr"),10) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x0d, (byte) 0x56 });
		msg = Tool.byteUnion(msg, Tool.byteGet(obj.getString("medstan").getBytes(ch), 10));
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medpos1"),1));
		msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medpos2"),1));
		msg = addjym(msg);		
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 向下位机传送取药完成
	 * @param obj
	 * @throws Exception
	 */
	private static void order54(JSONObject obj) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt(obj.getString("addr"),10) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x07, (byte) 0x54 });	
		if (obj.getString("medcf").trim() == "")
			msg = Tool.byteUnion(msg, Tool.int2byte(0, 6));
		else
			msg = Tool.byteUnion(msg, Tool.byteGet(Tool.str2bytes(obj.getString("medcf"), null), 1, 6));
		//msg = Tool.byteUnion(msg, Tool.byteGet(obj.getString("docname").getBytes(ch), 6));
		//msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("mednum"),2));
		//msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medtotal"),2));
		msg = addjym(msg);		
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 向所有下位机传送盘点
	 * @param obj
	 * @throws Exception
	 */
	private static void order58(JSONObject obj,int type) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt("ff",16) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x03, (byte) 0x58 });	
		if (type == 0){
			msg = Tool.byteUnion(msg, Tool.int2byte(0, 2));
		} else if (type == 1){
			msg = Tool.byteUnion(msg, new byte[] { (byte) obj.getInt("addr") });
			msg = Tool.byteUnion(msg, new byte[] { (byte) obj.getInt("type") });
		}	
		msg = addjym(msg);	
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 取下位机ID
	 * @param obj
	 * @throws Exception
	 */
	private static void order59(JSONObject obj) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt("ff",16) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x02, (byte) 0x59, (byte) 0x59 });	
		msg = addjym(msg);		
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 向所有下位机传送盘点和取下位机ID完成
	 * @param obj
	 * @throws Exception
	 */
	private static void order5A(JSONObject obj) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt("ff",16) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x03, (byte) 0x5A, (byte) 0x58, (byte) 0x59 });
		msg = addjym(msg);		
		SessionMap.newInstance().send(obj.getString("ip"), msg);
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 向所有下位机传送电子标签ID和对应的下位机地址
	 * @param obj
	 * @param type
	 * @throws Exception
	 */
	private static void order5B(JSONObject obj,int type) throws Exception{
		//Random r = new Random(255);
		byte[] msg = new byte[2];
		r.nextBytes(msg);
		msg = Tool.byteUnion(msg, new byte[] { (byte) Integer.parseInt("ff",16) });
		msg = Tool.byteUnion(msg, new byte[] { (byte) 0x0A, (byte) 0x5B });
		if (type == 0){
			msg = Tool.byteUnion(msg, Tool.str2bytes(obj.getString("idNum"), null));
			msg = Tool.byteUnion(msg, Tool.int2byte(obj.getInt("addr"), 1));
			msg = Tool.byteUnion(msg, Tool.toBCD(obj.getInt("medTime"),1));
		} else if (type == 1) {
			
		}
		msg = addjym(msg);		
		SessionMap.newInstance().send(obj.getString("ip"), msg);
	}
	
	/**
	 * 添加校验码
	 * @param msg
	 */
	private static byte[] addjym(byte[] msg){
		byte jym = 0x00;
		for (int i=2;i<msg.length;i++){
			jym = (byte) (jym + msg[i]);
		}		
		msg = Tool.byteUnion(msg, new byte[] { jym });		
		return msg;
	}
	
	/**
	 * 发送指令
	 * @param obj
	 * @return
	 */
	public static boolean sendOrder(JSONObject obj){
		sendOrder(obj, 0);
		return true;
	}
	
	/**
	 * 按类型发送指令
	 * @param obj
	 * @param type
	 * @return
	 */
	public static boolean sendOrder(JSONObject obj,int type){	
		
		try{
			switch (Tool.str2bytes(obj.getString("orderType"), " ")[0]) {
			
			case 0x52:
				order52(obj);
				break;			
			case 0x53:
				order53(obj);
				//order531(obj);
				break;
			case 0x56:
				order56(obj);
				break;
			case 0x54:
				order54(obj);
				break;
			case 0x58:
				order58(obj,type);
				break;
			case 0x59:
				order59(obj);
				break;
			case 0x5A:
				order5A(obj);
				break;
			case 0x5B:
				order5B(obj,type);
				break;
			default:
				break;
			}
		} catch(Exception e){
			log.error(e);
			return false;
		}
		return true;
	}

	
}
