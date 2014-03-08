package cn.edu.seu.cse.survey.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class AbstractController {
	protected void ajaxResponse(HttpServletResponse response, String str) {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
