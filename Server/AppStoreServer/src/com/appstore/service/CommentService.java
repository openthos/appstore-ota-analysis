package com.appstore.service;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommentService {
	DataBaseService dbs = null;

	public void addComment(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String aid = request.getParameter("aid");
		String comment = request.getParameter("comment");
		String temp = new String(comment.getBytes("ISO-8859-1"), "UTF-8");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String time = sdf.format(now);
		dbs = new DataBaseService();
		dbs.getConn();
		String sql = "insert into comment(aid,content,time) values(" + aid + ",'" + temp + "','" + time + "')";
		// System.out.println(sql);
		dbs.InsertDML(sql);
		dbs.CloseAll();
	}

	public String getComment(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String aid = request.getParameter("aid");
		String sql="select * from comment where aid='"+aid+"'";
		dbs = new DataBaseService();
		ResultSet result = dbs.QueryDML(sql);
		StringBuilder json = new StringBuilder();
		json.append('[');
		while (result.next()) {
			json.append('{');
			json.append("id:").append(result.getInt("id")).append(",");
			json.append("aid:\"").append(result.getInt("aid")).append("\",");
			json.append("content:\"").append(result.getString("content")).append("\",");
			json.append("username:\"").append(result.getString("username")).append("\",");
			json.append("time:\"").append(result.getString("time")).append("\"");
			json.append("},");
		}
		json.deleteCharAt(json.length() - 1);
		json.append(']');
		dbs.CloseAll();
		return json.toString();
	}
}
