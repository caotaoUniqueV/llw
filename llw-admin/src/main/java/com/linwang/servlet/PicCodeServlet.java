package com.linwang.servlet;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linwang.uitls.PicCodeUtils;

public class PicCodeServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PicCodeUtils.createPicStream(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		 doGet(request, response);
    }

}
