package com.linwang.rpc;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IAuthFunctionService;
import com.linwang.api.IAuthRoleFunctionService;
import com.linwang.api.IAuthRoleService;
import com.linwang.entity.AuthFunction;
import com.linwang.entity.AuthUser;
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisCacheManager;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.StaticProp;

@Controller
@RequestMapping("/index")
public class indexRpc extends BaseRpc{
	 @Reference(version="1.0.0")
	 private IAuthFunctionService authFunctionService;
	 @Reference(version="1.0.0")
	 private IAuthRoleFunctionService authRoleFunctionService;
	 @Reference(version="1.0.0")
	 private IAuthRoleService authRoleService;
	 @Autowired
	 private RedisCacheManager redisCacheManager;
	
	 @RequestMapping(value="index",method=RequestMethod.GET)
	 @RequiresPermissions("admin:admin")
	 public String index(Model model,HttpServletRequest request) throws Exception{
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 AuthUser admin=(AuthUser) SecurityUtils.getSubject().getSession().getAttribute("admin");
		 List<AuthFunction> authFunctions=JSONObject.parseArray(jedisPoolManager.get("permission:"+ AccountDigestUtils.getMd5Pwd(admin.getUsername(),StaticProp.sysConfig.get("cookie.secret.key"))),AuthFunction.class);
		 if(authFunctions!=null){
			 request.setAttribute("authFunctions", authFunctions);
		 }
	    return "index";
	 }
	
	 @RequestMapping(value="login",method=RequestMethod.GET)
	 public String login() throws Exception{
	    return "login";
	 }
}
