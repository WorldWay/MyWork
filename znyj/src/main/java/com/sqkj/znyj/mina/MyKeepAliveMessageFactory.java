package com.sqkj.znyj.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class MyKeepAliveMessageFactory implements KeepAliveMessageFactory{

	private final Log LOG = LogFactory.getLog(MyKeepAliveMessageFactory.class);
	
    private static final byte[] HEARTBEATREQUEST = {0x00};  
    private static final byte[] HEARTBEATRESPONSE = {0x01}; 
    public Object getRequest(IoSession session) {
        LOG.debug("请求预设信息: " + HEARTBEATREQUEST);  
         return HEARTBEATREQUEST;
    }
    public Object getResponse(IoSession session, Object request) {
        LOG.debug("响应预设信息: " + HEARTBEATRESPONSE); 
        return HEARTBEATRESPONSE;  
    }
    public boolean isRequest(IoSession session, Object message) {
         LOG.debug("请求心跳包信息: " + message);
         if (message == null) return false;
         byte[] data = null;
         try{
        	 data = (byte[]) message;
         } catch(Exception e){
        	 return false;
         }
         if (data.length == 0) return false;
         if (data[0]==HEARTBEATREQUEST[0])  
             return true; 
         return false;  
    }
    public boolean isResponse(IoSession session, Object message) {
      LOG.debug("响应心跳包信息: " + message);  
      if (message == null) return false;
      byte[] data = null;
      try{
     	 data = (byte[]) message;
      } catch(Exception e){
     	 return false;
      }
      if (data.length == 0) return false;
      if(data[0]==HEARTBEATRESPONSE[0])  
          return true;
        return false;
    }	
}
