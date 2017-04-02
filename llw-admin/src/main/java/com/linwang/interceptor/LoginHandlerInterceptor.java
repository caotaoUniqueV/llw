package com.linwang.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IAuthFunctionService;
import com.linwang.api.IAuthRoleFunctionService;
import com.linwang.api.IAuthRoleService;
import com.linwang.entity.AuthUser;
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisCacheManager;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
	 @Autowired
	 private RedisCacheManager redisCacheManager;
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	            throws Exception {
//		 StringBuilder redirectURL = new StringBuilder();
//	     redirectURL.append(request.getRequestURI());
//		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 return true;
	 }
}
