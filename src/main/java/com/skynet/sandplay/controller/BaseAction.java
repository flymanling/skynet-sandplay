package com.skynet.sandplay.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.IBaseService;

/**
 * Servlet implementation class IndexServlet
 */
@Controller
@RequestMapping("/BaseAction")
public class BaseAction {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	@Resource(name="userService")
	private IBaseService<User, String> baseService;
	
	public void setBaseService(IBaseService<User, String> baseService) {
		this.baseService = baseService;
	}
	
	@RequestMapping
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String data = req.getParameter("data");
		if(data != null) {
			BaseMsg msg = gson.fromJson(data, BaseMsg.class);
		}
	}

}
