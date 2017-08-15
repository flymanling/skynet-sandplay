package com.skynet.sandplay.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/IndexServlet")
public class StudentAction {

	@RequestMapping
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
