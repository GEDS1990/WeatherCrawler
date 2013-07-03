package com.baca.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpProtocolParams;

import com.baca.config.Config;

@SuppressWarnings("deprecation")
public class HttpCreator{
	private static HttpClient m_client = null;
	
	private static final String CONTENT_CHARSET = "unicode";
	
	public static HttpClient getHttpClient(){
		if(m_client == null){
			ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();  
	        cm.setMaxTotal(Integer.valueOf(Config.getValue("max_httpclient_num")));  
			m_client = new DefaultHttpClient(cm);
			
			m_client.getParams().setParameter(HttpProtocolParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET);
//			System.out.println("httpClient is successfully established");
		}else{
//			System.out.println("httpClient has been established");
		}
		
		return m_client;
	}
}