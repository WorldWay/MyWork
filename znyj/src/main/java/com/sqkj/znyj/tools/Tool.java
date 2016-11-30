package com.sqkj.znyj.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

public class Tool {

	private final static Log log = LogFactory.getLog(Tool.class);
	
	/**
	 * byte[]转16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String bytes2HexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 合并byte[]
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] byteUnion(byte[] a, byte[] b) {
		if (a == null || b == null)
			return null;

		byte[] result = new byte[a.length + b.length];
		if (result.length == 0)
			return null;
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);

		return result;
	}

	/**
	 * 获取指定长度的byte[]
	 * 
	 * @param a
	 * @param len
	 * @return
	 */
	public static byte[] byteGet(byte[] a, int len) {
		byte[] result = new byte[len];
		System.arraycopy(a, 0, result, 0, Math.min(a.length, len));
		return result;
	}
	
	/**
	 * 获取指定长度的byte[]
	 * 
	 * @param a
	 * @param len
	 * @return
	 */
	public static byte[] byteGet(byte[] a,int start, int len) {
		byte[] result = new byte[len];
		System.arraycopy(a, start, result, 0, Math.min(a.length, len));
		return result;
	}

	/**
	 * 10进制转指定长度BCD码
	 * 
	 * @param a
	 * @param len
	 * @return
	 */
	public static byte[] toBCD(int a ,int len) {

		byte[] result = new byte[len];

		for (int i = 0; i < len; i++) {
			int bcd = (a % 10) + (a / 10 % 10) * 16;
			a = a / 100;
			result[len - 1 - i] = (byte) bcd;
		}

		return result;

	}

	/**
	 * 转换10进制数为制定长度的byte[]
	 * 
	 * @param a
	 * @param len
	 * @return
	 */
	public static byte[] int2byte(int a, int len) {
		byte[] result = new byte[len];

		for (int i = 0; i < len; i++) {
			result[len - 1 - i] = (byte) (a & 0xff);
			a = a >> 8;
		}

		return result;
	}
	
	/**
	 * 转换10进制数为制定长度的byte[]
	 * 
	 * @param a
	 * @param len
	 * @return
	 */
	public static byte[] int2byte(long a, int len) {
		byte[] result = new byte[len];

		for (int i = 0; i < len; i++) {
			result[len - 1 - i] = (byte) (a & 0xff);
			a = a >> 8;
		}

		return result;
	}

	/**
	 * 字符串转byte[]
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] str2bytes(String s, String splits) {
		String[] hexstr = null;
		if (splits == null) {
			hexstr = new String[s.length() / 2];
			for (int i = 0; i < s.length() / 2; i++)
				hexstr[i] = s.substring(2 * i, 2 * (i + 1));
		} else {
			hexstr = s.split(splits);
		}
		byte[] result = new byte[hexstr.length];
		for (int i = 0; i < hexstr.length; i++)
			result[i] = (byte) Integer.parseInt(hexstr[i], 16);
		return result;
	}

	/**
	 *  json2map
	 * @param json
	 * @return map
	 * 
	 */
	public static Map<String, Object> Json2Map(JSONObject json){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			for (@SuppressWarnings("rawtypes")
			Iterator i = json.keys();i.hasNext();) {
				String key = i.next().toString();
				map.put(key, json.get(key));
			}
		} catch(Exception e){
			log.error(e);
		}
		
		return map;
	}
	
	/**
	 * 校验数据 
	 * @param data
	 * @return
	 */
	public static boolean JY(byte[] data){
		try {
			byte[] b = new byte[data[3] + 2];
			byte jym = (byte) (data[data.length - 1] & 0xff);
			System.arraycopy(data, 2, b, 0, data[3] + 2);
			byte jys = 0x00;
			for (int i = 0; i < b.length; i++) {
				jys += b[i] & 0xff;
			}
			if (jym == jys)
				return true;
		} catch (Exception e) {
			log.error("校验出错，请检查数据:" + bytes2HexString(data));
		}
		log.error("校验出错，请检查数据:" + bytes2HexString(data));
		return false;
	}
	
	public static int delayTime = 2;
	
	public static ScheduledThreadPoolExecutor	tpool = new ScheduledThreadPoolExecutor(1);
	
	public static ScheduledThreadPoolExecutor	endtpool = new ScheduledThreadPoolExecutor(1);
	
	public static void main(String args[]) {
		
		Random r = new Random();
		byte[] b = new byte[2];
		for (int i = 0;i<100;i++){
		r.nextBytes(b);		
		}
		System.out.println(bytes2HexString(byteGet(str2bytes("20161125000001", null), 1, 6)));
	}
}
