package com.sqkj.znyj.thread;

import java.util.concurrent.TimeUnit;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sqkj.znyj.service.GetCFService;
import com.sqkj.znyj.tools.Tool;

@Component
public class Init implements InitializingBean {

	@Autowired
	private GetCFService cfservice;

	@Override
	public void afterPropertiesSet() throws Exception {
		MyTask task = new MyTask(cfservice);
		Tool.tpool.scheduleAtFixedRate(task, 0, 1000, TimeUnit.MILLISECONDS);
	}

	private static class MyTask implements Runnable {

		private GetCFService cfService;

		public MyTask(GetCFService cfservice) {
			this.cfService = cfservice;
		}

		@Override
		public void run() {
			cfService.QYstart(new JSONObject());
		}
	}

}
