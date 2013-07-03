package com.baca.task.specific;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.baca.db.WeatherGetDB;
import com.baca.http.HttpConnector;
import com.baca.task.base.BaseRunnable;

public class WeatherPollutionGetTask extends BaseRunnable {
	private String m_webPage = new String();
	private String m_url;
	private Document doc;
	private WeatherGetDB dbcommand = new WeatherGetDB();
	private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public WeatherPollutionGetTask(Map<String, String> task) {
		super(task);
		this.m_url = task.get("url");
	}

	public WeatherPollutionGetTask(Map<String, String> task, String taskName) {
		super(task, taskName);
		this.m_url = task.get("url");
	}

	@Override
	protected String getRandomName() {
		return null;
	}

	private void crawlerWeb() {
		HttpConnector httpCon = new HttpConnector();
		httpCon.setContentType("text/html");
		try {
			m_webPage = httpCon.get(m_url);
			doc = Jsoup.parse(m_webPage);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseHtml() {
		String datetime = dt.format(new Date());
		Elements webHtml = doc.getElementById("bk").getElementsByTag("table")
				.get(3).getElementsByTag("tr");
		// Elements[] webHtmlCity = new Elements[webHtml.size()];
		for (int i = 1; i < webHtml.size(); i++) {

			if (i == 1) {
				Elements webHtmlCity1 = webHtml.get(1).getElementsByTag("td");
				String cityName = webHtmlCity1.get(1).getElementsByTag("a")
						.html();
				String airQ = webHtmlCity1.get(2).html();
				String airC = webHtmlCity1.get(3).html();
				String airDes = webHtmlCity1.get(4).html();
				System.out.println(cityName + " " + airQ + " " + airC + " "
						+ airDes);
				dbcommand.insert(cityName, airQ, airC, airDes, datetime);
			} else {
				Elements webHtmlCity = webHtml.get(i).getElementsByTag("td");
				String cityName = webHtmlCity.get(0).getElementsByTag("a")
						.html();
				String airQ = webHtmlCity.get(1).html();
				String airC = webHtmlCity.get(2).html();
				String airDes = webHtmlCity.get(3).html();
				System.out.println(cityName + " " + airQ + " " + airC + " "
						+ airDes);
				dbcommand.insert(cityName, airQ, airC, airDes, datetime);
			}
		}

	}

	@Override
	protected void excute() {
		try {
			synchronized (this) {
				Thread.sleep(4000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			crawlerWeb();
			parseHtml();
		}
	}

}
