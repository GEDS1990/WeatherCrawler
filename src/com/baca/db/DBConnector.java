package com.baca.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.baca.config.Config;

/**
 * DB�����࣬��������DB���ݿ⣻���������com.baca.config.DBConfig
 * 
 * @author yang tao <hsllany@163.com>
 * @version 1.0
 */
public class DBConnector {
	/*
	 * ��̬�������洢���ݿ�����
	 */
	public static Connection m_conn;

	/**
	 * �����ݿ�����δ�������ѹرգ��������ݿ����ӣ��������ݿ�����
	 * @return Connection�� ���ݿ�����
	 */
	public static Connection getConnection() {
		try {
			//��δ�������������ݿ��ѱ�
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
	 * �ر����ݿ����ӡ�
	 * @return �����ݿ�رճɹ������Ѿ��رգ�������ݿ�����Ϊnull���򷵻�true�����򣬷���false;
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