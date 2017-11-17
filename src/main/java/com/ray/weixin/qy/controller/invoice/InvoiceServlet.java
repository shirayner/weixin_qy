package com.ray.weixin.qy.controller.invoice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.qy.config.Env;
import com.ray.weixin.qy.service.invoice.InvoiceService;
import com.ray.weixin.qy.util.AuthHelper;

/**
 * Servlet implementation class WeiXinInvoiceServlet
 */
public class InvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InvoiceServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.将请求、响应的编码均设置为UTF-8（防止中文乱码）  
		request.setCharacterEncoding("UTF-8");  
		response.setCharacterEncoding("UTF-8"); 

		//2.接收请求参数
		//得到一个2层的转义字符串
		String invoiceListStr_escape=request.getParameter("invoiceListStr");
		System.out.println("invoiceListStr_escape:"+invoiceListStr_escape);

		
		//3.查询发票详细信息
		String accessToken=null;
		JSONObject InvoiceInfo=null;
		try {
			accessToken = AuthHelper.getAccessToken(Env.CORP_ID, Env.AGENT_SECRET);
			InvoiceInfo=InvoiceService.listInvoiceInfo(accessToken, invoiceListStr_escape);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		//4.将发票详细信息发送至前台
		PrintWriter out = response.getWriter(); 
		out.print(InvoiceInfo);  
		out.close();  
		out = null;  




	}

}
