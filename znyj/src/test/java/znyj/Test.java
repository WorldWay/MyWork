package znyj;

import net.sf.json.JSONObject;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sqkj.znyj.service.DeviceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class Test {

    @Autowired
    private DeviceService service;
	
    @org.junit.Test
    public void test(){
		JSONObject obj = new JSONObject();
		obj.put("id", "3");
		obj.put("ZJQMC", "15");
		obj.put("ZJQIP", "192.168.100.110:4433");
		obj.put("result", service.getZJQCount(obj));
		System.out.println(obj.getString("result"));		
    }
	
}
