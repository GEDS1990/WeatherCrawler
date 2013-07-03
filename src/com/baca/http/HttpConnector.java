package com.baca.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HTTP;

/**
 * 模拟浏览器的类，可以使用其get和post方法
 * 
 * @author Yang Tao <hsllany@163.com>
 * @version 1.0.0
 */
public class HttpConnector {
	public static final String HTTP_MOVED_TEMPORARILY = "<<302 move temporarily>>";
	public static final String HTTP_BAD_REQUEST = "<<400 bad request>>";
	public static final String HTTP_FORBIDDEN = "<<403 forbidden>>";
	public static final String HTTP_NOT_FOUND = "<<404 not found>>";
	public static final String HTTP_NOT_IMPLEMENTED = "<<501 not implement>>";

	public static final String USER_AGENT_DEFAULT = "java_client";
	public static final String USER_AGENT_ANDROID = "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	public static final String USER_AGENT_IPHONE = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

	/**
	 * m_client
	 */
	private HttpClient m_client = null;

	/**
	 * User-Agent参数
	 */
	private String m_userAgent = null;

	/**
	 * Content-Type参数
	 */
	private String m_contentType = null;

	/**
	 * 构造函数
	 */
	public HttpConnector() {
		this.m_client = HttpCreator.getHttpClient();
		this.m_userAgent = USER_AGENT_DEFAULT;
	}

	/**
	 * 
	 * @param userAgent
	 *            用户代理
	 */
	public HttpConnector(String userAgent) {
		this.m_client = HttpCreator.getHttpClient();
		this.m_userAgent = userAgent;
	}

	public String getUserAgent() {
		return m_userAgent;
	}

	public void setUserAgent(String m_userAgent) {
		this.m_userAgent = m_userAgent;
	}

	public String getContentType() {
		return m_contentType;
	}

	public void setContentType(String m_contentType) {
		this.m_contentType = m_contentType;
	}

	/**
	 * 模拟get方法
	 * 
	 * @param url
	 *            ，get访问的地址
	 * @return page页面
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String get(String url) throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(url);

		get.setHeader("User-Agent", this.m_userAgent);
		get.setHeader("Content-Type", this.m_contentType);
		get.setHeader("Connection", "close");

		return this.request(get);
	}

	/**
	 * 模拟post方法
	 * 
	 * @param url
	 * @param postParameter
	 * @return page页面
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public String post(String url, List<NameValuePair> postParameter)
			throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);

		post.setHeader("User-Agent", this.m_userAgent);
		post.setHeader("Content-Type", this.m_contentType);
		// 设置post参数
		if (postParameter != null) {
			if (!postParameter.isEmpty()) {
				post.setEntity(new UrlEncodedFormEntity(postParameter,
						HTTP.UTF_8));
			}
		}

		return this.request(post);

	}

	/**
	 * 连接的基础方法，处理各种post\get请求，并返回相应的结果。
	 * 
	 * @param request
	 *            ，Post请求或Get请求
	 * @return page 若成功，则返回页面；否则返回各种错误信息
	 */
	public String request(HttpRequestBase request) throws ClientProtocolException, IOException{
		
		if((request instanceof HttpGet)|| (request instanceof HttpPost)){
			//执行请求
			HttpResponse response = m_client.execute(request);

			// 200,正常情况
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				
				StringBuilder builder = new StringBuilder();
				BufferedReader bufferedReader2 = new BufferedReader(
						new InputStreamReader(response.getEntity().getContent(),
								"UTF-8"));
				for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
						.readLine()) {
					builder.append(s);
				}
				String result = builder.toString();
				
				request.releaseConnection();
				// 返回页面
				return result;

				// 301，处理重定向，
			} else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY || response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY){
				System.out.println("relocate");
				Header[] resHeader = response.getHeaders("location");
				if(resHeader != null){
					String locUrl = resHeader[0].getValue();
					request.releaseConnection();
					this.get(locUrl);
				}else{
					return null;
				}
				// 403，处理禁止访问的情况，
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN) {
				System.err.println("error: 403, page forbidden");
				request.releaseConnection();
				return HTTP_FORBIDDEN;

				// 404，处理页面无法找到的情况
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				System.err.println("error: 404, page not found");
				request.releaseConnection();
				return HTTP_NOT_FOUND;
				
				//501, 页面无法处理
			} else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err.println("error: 501, page not implemented");
				request.releaseConnection();
				return HTTP_NOT_IMPLEMENTED;
			}
		}
		
		return null;		
	}
}
