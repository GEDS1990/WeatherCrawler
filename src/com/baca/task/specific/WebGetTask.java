package com.baca.task.specific;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.baca.http.HttpConnector;
import com.baca.task.base.BaseRunnable;

public class WebGetTask extends BaseRunnable {
	protected String m_url;
	
	public WebGetTask(Map<String, String> task, String url){
		super(task);
		this.m_url = url;
	}
	
	public WebGetTask(Map<String, String> task, String taskName, String url){
		super(task, taskName);
		this.m_url = url;
	}

	@Override
	protected String getRandomName() {
		return String.valueOf(Math.round(Math.random() * 10000)) + "_"
				+ String.valueOf(System.currentTimeMillis()) + "_dbTask";
	}

	@Override
	protected void excute() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		HttpConnector h = new HttpConnector();
		try {
			String a = h.get(this.m_url);
			System.out.println(a);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
