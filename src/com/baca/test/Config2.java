package com.baca.test;

/**
 * ���ݿ��������
 * @author Yang Tao <hsllany@163.com>
 *
 */
public class Config2 {
	
	/**
	 * ����ַ����ݿ��������ַ
	 */
//	public static final String DB_URL = "jdbc:mysql://114.113.227.155:3306/weibo";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/";
	
	/**
	 * ����ַ����ݿ�����
	 */
	public static final String DB_NAME = "weather";
	
	/**
	 * ����ַ����ݿ���û���
	 */
	public static final String DB_ADMIN = "root";
	
	/**
	 * ����ַ����ݿ������
	 */
//	public static final String DB_PASSWORD = "goodman6598ou";
	public static final String DB_PASSWORD = "";
	
	// public static final String DB_URL = "jdbc:mysql://localhost/weibo";
	// //goodman6598ou
	// public static final String DB_ADMIN = "root";
	// public static final String DB_PASSWORD = "badman111";
	//
	
	/**
	 * ����������ϵͳ�ļ��������λΪ����
	 */
	public static final int MONITOR_INTERVAL = 10000;
}
