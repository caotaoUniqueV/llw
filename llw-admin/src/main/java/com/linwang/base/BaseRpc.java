package com.linwang.base;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.linwang.uitls.CookieUtil;
import com.linwang.uitls.HtmlParsePlaintText;
import com.linwang.uitls.IpUtil;
import com.linwang.uitls.Logger;
import com.linwang.uitls.PageData;


public class BaseRpc extends ExceptionBaseRpc {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 6357869213649815390L;
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 文本框内容转为HTML格式
	 * 
	 * @param textArea
	 * @return
	 */
	public String changeTextAreaToHtml(String textArea) {
		return HtmlParsePlaintText.changeTextAreaToHtml(textArea);
	}

	/**
	 * @Description: 初始化数据绑定设置信息
	 * @version: V1.0
	 * @param dataBinder
	 */
	@InitBinder
	public void setDisallowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("pageNumber", "pageSize", "pageBeginIndex", "expressionChainList", "orderBy",
				"extConditions", "nullColums");
	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	public String getIp() {
		return IpUtil.getIp(getRequest());
	}

	public CookieUtil getCookieUtil() {
		return new CookieUtil(getRequest(), getResponse());
	}
}
