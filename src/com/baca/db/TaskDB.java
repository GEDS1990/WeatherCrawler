package com.baca.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * CREATE TABLE IF NOT EXISTS `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(200) NOT NULL,
  `url` varchar(200) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
*
**/

/**
 * 操作任务的数据库
 * @author Yang Tao <hsllany@163.com>
 * @version 1.0
 *
 */

public class TaskDB extends DB{
	private String m_table = "task";
	
	public TaskDB(){
		super();
		try {
			this.m_prepStmt = this.m_conn.prepareStatement("SELECT * FROM " + this.m_table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	public List<Map<String, String>> getTasks(){
		ResultSet result = null;
		try {
			result = this.m_prepStmt.executeQuery();
			return this.toList(result);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void deleteTask(String id){
		String sql = "DELETE FROM " + this.m_table + " WHERE id = " + id + "";		
		try {
			this.m_stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}
		
	}
	
	
}
