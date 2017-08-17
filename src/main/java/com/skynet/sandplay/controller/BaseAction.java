package com.skynet.sandplay.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.skynet.sandplay.dispatcher.ActionManager;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.util.StringUtil;

/**
 * Servlet implementation class IndexServlet
 */
@Controller
@RequestMapping("/BaseAction")
public class BaseAction {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	@Resource
	private ActionManager actionManager;
	
	@RequestMapping
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String data = "";
		String method = req.getMethod();
		if (method.equalsIgnoreCase("post")) {// post请求
			data = getBodyString(req.getReader());
//			requestContent = req.getRequestURL() + "?data=" + msg;
//			System.out.println("【reqId=" + curId + "】post:" + requestContent);
		} else {
			data = req.getParameter("data");
		}
		System.out.println("data:" + data);
		String result = "data is empty";
		if(StringUtil.isNotEmpty(data)) {
			BaseMsg msg = gson.fromJson(data, BaseMsg.class);
			result = actionManager.onBaseMsg(msg);
		}
		resp.setHeader("content-type","text/html;charset=UTF-8");
		OutputStream out = resp.getOutputStream();
		out.write(result.getBytes("UTF-8"));
	}
	
	private static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		if (br == null)
			return str;
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
		return str;
	}

}
