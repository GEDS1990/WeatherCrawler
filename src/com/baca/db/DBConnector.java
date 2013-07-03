package com.baca.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.baca.config.Config;

/**
 * DB链接类，负责链接DB数据库；设置项请见com.baca.config.DBConfig
 * 
 * @author yang tao <hsllany@163.com>
 * @version 1.0
 */
public class DBConnector {
	/*
	 * 静态变量，存储数据库链接
	 */
	public static Connection m_conn;

	/**
	 * 若数据库连接未建立或已关闭，则建立数据库连接；返回数据库链接
	 * @return Connection， 数据库连接
	 */
	public static Connection getConnection() {
		try {
			//若未建立，或者数据库已闭
			if (m_conn == null) {		
				m_conn = DriverManager.getConnection(Config.getValue("task_db_url") + Config.getValue("task_db_name") + "?user="
						+ Config.getValue("task_db_admin") + "&password="
						+ Config.getValue("task_db_password")  + "&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
				
				System.out.println("DB: connection is successfully established");
			}else if(m_conn.isClosed()){
				m_conn = DriverManager.getConnection(Config.getValue("task_db_url") + Config.getValue("task_db_name") + "?user="
						+ Config.getValue("task_db_admin") + "&password="
						+ Config.getValue("task_db_password")  + "&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull");
				
				
				System.out.println("DB: connection is successfully re-established");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return m_conn;
	}

	/**
	 * 关闭数据库连接。
	 * @return 若数据库关闭成功，或已经关闭，或该数据库连接为null，则返回true；否则，返回false;
	 */
	public static boolean closeConnection() {
		try {
			if (m_conn != null) {
				if (m_conn.isClosed()) {
					System.out.println("DB: connection has been closed");
					return true;
				} else {
					m_conn.close();
					System.out.println("DB: connection is successfully closed");
					return true;
				}
			} else {
				System.out.println("DB: connection is null, need no closing");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}