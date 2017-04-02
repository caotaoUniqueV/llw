package com.linwang.rpc.base;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IAuthFunctionService;
import com.linwang.api.ISysLogService;
import com.linwang.entity.AuthFunction;
import com.linwang.entity.AuthUser;
import com.linwang.entity.SysLog;
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisCacheManager;
import com.linwang.uitls.CookieUtil;
import com.linwang.uitls.HtmlParsePlaintText;
import com.linwang.uitls.IpUtil;
import com.linwang.uitls.Logger;
import com.linwang.uitls.PageData;
import com.linwang.uitls.StaticProp;


public class BaseRpc extends ExceptionBaseRpc {
	@Autowired
	private ISysLogService sysLogService;
	
	@Autowired
	private IAuthFunctionService authFunctionService;
	
	@Autowired
	 private RedisCacheManager redisCacheManager;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 6357869213649815390L;
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * @Title: addSysLog @Description: 添加系统日志 @author 龚国君 javazj@qq.com @date
	 * 2016年4月18日 上午7:12:03 @version V1.0 @param @param module @param @param
	 * oprate @param @param msg 设定文件 @return void 返回类型 @throws
	 */
	public void addSysLog(final String module, final String oprate, final String msg) {
		JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		String uri = getRequest().getRequestURI().trim();
		uri = uri.trim();
		if (uri.endsWith("/") && uri.length() > 1) {
			uri = uri.substring(0, uri.length() - 1);
		}
		final String uri_ = uri;
		AuthFunction condition = new AuthFunction();
		condition.setUrl(uri_);
		AuthFunction adminActions = authFunctionService.getByCondition(condition);
		if (adminActions == null) {
			adminActions = new AuthFunction();
			adminActions.setName("-");
		}
		final AuthFunction adminActions_ = adminActions;
		final AuthUser admin=JSONObject.parseObject(jedisPoolManager.get("admin"),AuthUser.class);
		final String ip = getIp();
		StaticProp.execute.execute(new Runnable() {
			public void run() {
				SysLog sysLog = new SysLog();
				sysLog.setAdminId(admin.getId());
				sysLog.setIp(ip);
				sysLog.setModuleType(module);
				sysLog.setOprateType(oprate);
				sysLog.setMsg(msg);
				sysLog.setName(adminActions_.getName());
				sysLog.setUri(uri_);
				sysLog.setDateAdd(new Date());
				sysLogService.insertSelective(sysLog);
			}
		});
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
