package com.baca.test;

import com.baca.task.specific.WeatherPollutionGetTask;

public class WeatherGetTest {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url="http://weather.china.com.cn/zhishu/8-1-1.html";
		WeatherPollutionGetTask weatherGet = new WeatherPollutionGetTask(null);		
		Thread t = new Thread(weatherGet);
		t.start();

	}

}
