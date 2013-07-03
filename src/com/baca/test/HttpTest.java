package com.baca.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.baca.http.HttpConnector;


public class HttpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpConnector h = new HttpConnector();
		try {
			String a = h.get("http://localhost/test/rel.php");
			System.out.println(a);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
