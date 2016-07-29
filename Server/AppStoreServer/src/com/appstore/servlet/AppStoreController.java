package com.appstore.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appstore.service.OperateService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author entity
 *处理客户端tab的servlet
 */
public class AppStoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    
	public AppStoreController() {
        super();
       
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    String operate = new String( request.getParameter("operate"));
	    System.out.println(request.getRemoteAddr()+"进行了"+operate+"操作");
	    String json = null;
		if(operate.equals("paihang")){
			OperateService oper   = new OperateService();
  			 json =	oper.operate_paihang(request);
	    }
		else if(operate.equals("software")){
			  OperateService oper   = new OperateService();;   
			try {
				json =	oper.operate_software(request);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        else if(operate.equals("game")){
        	  OperateService oper   = new OperateService();;   
  			json =	oper.operate_game(request);
		}

		if(json == null){
			json = "wrong";
		}
		 OutputStream out = response.getOutputStream();  
         out.write(json.getBytes());  
	}

}





