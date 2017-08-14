package com.skynet.sandplay.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.IBaseService;

/**
 * Servlet implementation class IndexServlet
 */
@Controller
@RequestMapping("/IndexServlet")
public class IndexServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	
	@Resource(name="userService")
	private IBaseService<User, String> baseService;
	
	public void setBaseService(IBaseService<User, String> baseService) {
		this.baseService = baseService;
	}
	
	@RequestMapping
	@Transactional
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String round = req.getParameter("round");
		System.out.println("round:"+round);
		User user = baseService.get("402881e65de12882015de1288d690000");
		resp.getWriter().write(gson.toJson(user));;
	}

}
