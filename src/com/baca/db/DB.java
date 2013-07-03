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
 * 数据库的基类，抽象类，所有的数据库操作应均从此类扩展。使用时，应使用PreparedStatement m_prepStmt进行操作。
 * 
 * @author Yang Tao <hsllany@163.com>
 * @version 1.2
 */
public abstract class DB {
	protected Connection m_conn = null;
	protected PreparedStatement m_prepStmt = null;
	protected Statement m_stmt = null;

	/**
	 * 构造函数，子类的构造函数如果要覆盖，应运行super.DB
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
	 * 关闭数据库连接，连接均被关闭
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
	 * 取得任务列表
	 * @return List<Map<String, String>> 任务列表
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