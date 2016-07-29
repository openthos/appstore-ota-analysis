package com.appstore.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.appstore.interfaces.DataBase;

public class CheckUpdateService {
	
	DataBase dbService = null;
	String version = "";

	public String checkUpdate() {

		String sql = "select documentVersion from updatesystem order by id desc";
		System.out.println(sql);
		dbService = new DataBaseService();
		ResultSet result = dbService.QueryDML(sql);
		try {
			result.first();
			if (result.next()) {
				version = result.getString("documentVersion");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
}
