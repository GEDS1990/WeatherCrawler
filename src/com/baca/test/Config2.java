package com.baca.test;

/**
 * 数据库的设置项
 * @author Yang Tao <hsllany@163.com>
 *
 */
public class Config2 {
	
	/**
	 * 任务分发数据库服务器地址
	 */
//	public static final String DB_URL = "jdbc:mysql://114.113.227.155:3306/weibo";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/";
	
	/**
	 * 任务分发数据库名称
	 */
	public static final String DB_NAME = "weather";
	
	/**
	 * 任务分发数据库的用户名
	 */
	public static final String DB_ADMIN = "root";
	
	/**
	 * 任务分发数据库的密码
	 */
//	public static final String DB_PASSWORD = "goodman6598ou";
	public static final String DB_PASSWORD = "";
	
	// public static final String DB_URL = "jdbc:mysql://localhost/weibo";
	// //goodman6598ou
	// public static final String DB_ADMIN = "root";
	// public static final String DB_PASSWORD = "badman111";
	//
	
	/**
	 * 爬虫任务检测系统的检测间隔，单位为毫秒
	 */
	public static final int MONITOR_INTERVAL = 10000;
}
