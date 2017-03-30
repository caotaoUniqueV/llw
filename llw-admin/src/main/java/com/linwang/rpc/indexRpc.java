package com.linwang.rpc;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.linwang.api.IAuthFunctionService;
import com.linwang.base.BaseRpc;
import com.linwang.entity.AuthFunction;
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisSessionDAO;

@Controller
@RequestMapping("/index")
public class indexRpc extends BaseRpc{
	 @Reference(version="1.0.0")
	 private IAuthFunctionService authFunctionService;
	 @Autowired
	 private JedisPoolManager jedisPoolManager;
	
	 @RequestMapping(value="index",method=RequestMethod.GET)
	 public String index(Model model,HttpServletRequest request) throws Exception{
		String jsonObject=jedisPoolManager.get("permission");//从缓存里面取值
		if(Strings.isNullOrEmpty(jsonObject)){
			List<AuthFunction> authFunctions=authFunctionService.getList();
			jsonObject=JSONObject.toJSONString(authFunctions);
			jedisPoolManager.set("permission", jsonObject);
		}
		List<AuthFunction> authFunctions=JSONObject.parseArray(jsonObject,AuthFunction.class);
		model.addAttribute("authFunctions",authFunctions);
	    return "index";
	 }
	
	 @RequestMapping(value="login",method=RequestMethod.GET)
	 public String login() throws Exception{
	    return "login";
	 }
}
