package com.linwang.rpc;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.linwang.rpc.base.BaseRpc;

@Controller
public class ErrorPageRpc extends BaseRpc {

	@RequestMapping(value = "/error/{errorCode}")
	public String e_errorCode(Model model, @PathVariable String errorCode) {
		Throwable t = (Throwable) getRequest().getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION");
		if (t == null) {
			// Tomcat的错误处理方式
			t = (Throwable) getRequest().getAttribute("javax.servlet.error.exception");
		}
		if (t != null) {
			model.addAttribute("errorMsg", t.getMessage());
		}
		if (t != null && t instanceof UnsatisfiedServletRequestParameterException) {
			//特定异常处理方式
		}
		return errorCode;
	}
	
	 @RequestMapping(value="/error/401",method=RequestMethod.GET)
	 public String noPermissio() throws Exception{
	    return "noPermissio";
	 }
}
