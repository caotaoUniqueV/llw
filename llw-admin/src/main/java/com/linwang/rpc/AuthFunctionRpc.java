package com.linwang.rpc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisCacheManager;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.PicCodeUtils;
import com.linwang.uitls.web.JSONResultCode;
import com.linwang.api.IAuthFunctionService;
import com.linwang.entity.AuthFunction;


@Controller
@RequestMapping("/authFunction")
public class AuthFunctionRpc extends BaseRpc{
	 @Reference(version="1.0.0")
	 private IAuthFunctionService authFunctionService;
	 @Autowired
	 private RedisCacheManager redisCacheManager;
	
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/authFunction/save")
	 public JSONResultCode login(AuthFunction authFunction) throws Exception{
		 if(authFunction.getPid()==null){
			 authFunction.setPid(0);
		 }
		 if(Strings.isNullOrEmpty(authFunction.getUrl())){
			 authFunction.setUrl("");
		 }
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 if(authFunction.getId()==null){
			 authFunctionService.insertSelective(authFunction);
			 addSysLog("权限设置","添加",JSONObject.toJSONString(authFunction));
		 }else{
			 authFunctionService.updateByPrimaryKeySelective(authFunction);
			 addSysLog("权限设置","编辑",JSONObject.toJSONString(authFunction));
		 }
		 jedisPoolManager.set("isPermission","1");//表示需要进行权限更新
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/authFunction/del")
	 public JSONResultCode del(Integer id) throws Exception{
		 AuthFunction authFunction=authFunctionService.selectByPrimaryKey(id);
		 authFunctionService.deleteByPrimaryKey(id);
		 addSysLog("权限设置","删除",JSONObject.toJSONString(authFunction));
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 jedisPoolManager.set("isPermission","1");//表示需要进行权限更新
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="permissionEdit",method=RequestMethod.GET)
	 @RequiresPermissions("/authFunction/permissionEdit")
	 public String permissionAdd(Model model,Integer id) throws Exception{
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 List<AuthFunction> authFunctions=JSONObject.parseArray(jedisPoolManager.get("permissionAll"),AuthFunction.class);
		 model.addAttribute("authFunctions", authFunctions);
		 
		 if(id!=null){
			 AuthFunction condition=new AuthFunction();
			 condition.setId(id);
			 AuthFunction aFunction_=authFunctionService.getByCondition(condition);
			 model.addAttribute("authFunction", aFunction_);
		 }
	    return "permissionAdd";
	 }
}
