package com.baca.db;

import java.sql.SQLException;

public class WeatherGetDB extends DB{
	
	public WeatherGetDB(){
		super();
		try {
			this.m_prepStmt = this.m_conn.prepareStatement("insert into weatherget (cityname,airquality,aircircum,directions,time) values (?,?,?,?,?) ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(String name,String quality,String circum,String description ,String datetime){		
		try {
			this.m_prepStmt.setString(1, name);
			this.m_prepStmt.setString(2, quality);
			this.m_prepStmt.setString(3, circum);
			this.m_prepStmt.setString(4, description);
			this.m_prepStmt.setString(5,datetime);
			this.m_prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
