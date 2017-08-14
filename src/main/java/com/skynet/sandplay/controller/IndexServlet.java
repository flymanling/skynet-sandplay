package com.skynet.sandplay.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet {
	private static final long serialVersionUID = 1L;

	@RequestMapping
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("hello sandplay");
		resp.getWriter().write("hello, this is sandplay");;
	}

}
