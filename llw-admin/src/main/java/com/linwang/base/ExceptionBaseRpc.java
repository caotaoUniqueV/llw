package com.linwang.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


public class ExceptionBaseRpc {
private static final Logger LOGGER = LogManager.getLogger(ExceptionBaseRpc.class);
	
	/**
	 * @Description: 数据库类型错误
	 * @param @param ex
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@ExceptionHandler({TypeMismatchException.class})
	@ResponseBody
	public String handleTypeMismatchException(TypeMismatchException ex) {
		LOGGER.error("数据库异常：",ex);
		return ex.getMessage();
	}
	/**
	 * @Description: 数据库约束错误
	 * @param @param ex
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@ExceptionHandler({DataIntegrityViolationException.class})
	@ResponseBody
	public String handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		LOGGER.error("数据库异常：",ex);
		return ex.getMessage();
	}
	/**
	 * @Description: 缺少指定的参数
	 * @param @param ex
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@ExceptionHandler({MissingServletRequestParameterException.class})
	@ResponseBody
	public String handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
		LOGGER.error("handleException",ex);
		return ex.getMessage();
	}
	
	/**
	* @Description: 默认错误配置
	* @param @param ex
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@ExceptionHandler({Exception.class})
	@ResponseBody
	public String handleException(Exception ex){
		LOGGER.error("handleException",ex);
		return ex.getMessage();
	}
	
	/**
	* @Description: 错误配置
	* @param @param ex
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@ExceptionHandler({IncorrectCredentialsException.class})
	@ResponseBody
	public String incorrectCredentialsException(Exception ex){
		LOGGER.error("handleException",ex);
		return ex.getMessage();
	}
	
	/**
	* @Description: 权限错误配置
	* @param @param ex
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public String UnauthorizedException(Exception ex){
		LOGGER.error("handleException",ex);
		return "redirect:/error/401";
	}
}
