package com.baca.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���ݿ�Ļ��࣬�����࣬���е����ݿ����Ӧ���Ӵ�����չ��ʹ��ʱ��Ӧʹ��PreparedStatement m_prepStmt���в�����
 * 
 * @author Yang Tao <hsllany@163.com>
 * @version 1.2
 */
public abstract class DB {
	protected Connection m_conn = null;
	protected PreparedStatement m_prepStmt = null;
	protected Statement m_stmt = null;

	/**
	 * ���캯��������Ĺ��캯�����Ҫ���ǣ�Ӧ����super.DB
	 */
	public DB() {
		this.m_conn = DBConnector.getConnection();

		try {
			this.m_stmt = this.m_conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر����ݿ����ӣ����Ӿ����ر�
	 */
	public void closeConnection() {
		DBConnector.closeConnection();
	}

	public void closeStatement() {
		if (this.m_stmt != null) {
			try {
				if (this.m_stmt.isClosed()) {
					System.out.println("DB: Statement has been closed");
				} else {
					this.m_stmt.close();
					System.out.println("DB: Statement is successfully closed");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("DB: Statement is null");
		}
	}
	
	public void closeAll(){
		this.closeStatement();
		this.closeConnection();
	}
	
	/**
	 * ȡ�������б�
	 * @return List<Map<String, String>> �����б�
	 */
	public List<Map<String, String>> toList(ResultSet result){
		try {
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
			
			while(result.next()){
				Map<String, String> rowData = new HashMap<String, String>();
				
				for(int i = 0; i < columnCount; i++){				
					rowData.put(metaData.getColumnName(i + 1), result.getString(i + 1));
				}
				
				resultList.add(rowData);
			}
			
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}