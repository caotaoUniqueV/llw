package com.linwang.rpc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisCacheManager;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.Page;
import com.linwang.uitls.PicCodeUtils;
import com.linwang.uitls.web.Expressions;
import com.linwang.uitls.web.JSONResultCode;
import com.linwang.api.IAuthFunctionService;
import com.linwang.api.IAuthRoleFunctionService;
import com.linwang.api.IAuthRoleService;
import com.linwang.api.IAuthUserRoleService;
import com.linwang.api.IAuthUserService;
import com.linwang.entity.AuthFunction;
import com.linwang.entity.AuthRole;
import com.linwang.entity.AuthRoleFunction;
import com.linwang.entity.AuthUser;
import com.linwang.entity.AuthUserRole;


@Controller
@RequestMapping("/authRole")
public class AuthRoleRpc extends BaseRpc{
	 @Reference(version="1.0.0")
	 private IAuthRoleService authRoleService;
	 
	 @Reference(version="1.0.0")
	 private IAuthRoleFunctionService authRoleFunctionService;
	 
	 @Reference(version="1.0.0")
	 private IAuthUserRoleService authUserRoleService;
	 
	 @Reference(version="1.0.0")
	 private IAuthFunctionService authFunctionService;
	 
	 @Autowired
	 private RedisCacheManager redisCacheManager;
	 
	 @RequestMapping(value="list",method=RequestMethod.GET)
	 @ResponseBody
	 public JSONResultCode list(AuthRole bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new AuthRole();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<AuthRole> pageBean = authRoleService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="roleAdd",method=RequestMethod.GET)
	 @RequiresPermissions("/authRole/roleAdd")
	 public String roleAdd(Model model,Integer id) throws Exception{
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 List<AuthFunction> authFunctions=JSONObject.parseArray(jedisPoolManager.get("permissionAll"),AuthFunction.class);
		 model.addAttribute("authFunctions", authFunctions);
		 
		 if(id!=null){
			 AuthRole condition=new AuthRole();
			 condition.setId(id);
			 AuthRole authRole=authRoleService.getByCondition(condition);
			 model.addAttribute("authRole", authRole);
			 
			//获取该角色的权限
	         AuthRoleFunction condition2 = new AuthRoleFunction();
	         condition2.setRoleId(id);
	         List<AuthRoleFunction> myActions = authRoleFunctionService.getList(condition2);
	         model.addAttribute("myActions", myActions);
		 }
	    return "roleAdd";
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/authRole/del")
	 public JSONResultCode del(Integer id) throws Exception{
		 AuthRole bean=authRoleService.selectByPrimaryKey(id);
		 authRoleService.deleteByPrimaryKey(id);
		 addSysLog("角色管理","删除",JSONObject.toJSONString(bean));
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
	     jedisPoolManager.set("isPermission","1");//表示需要进行权限更新
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/authRole/save")
	 public JSONResultCode login(AuthRole bean,Integer[] roleIds) throws Exception{
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 if(bean.getId() == null){
	            //添加
	            authRoleService.insertSelective(bean);
	            addSysLog("角色管理","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 	authRoleService.updateByPrimaryKeySelective(bean);
	    	 	addSysLog("角色管理","编辑",JSONObject.toJSONString(bean));
	            //删除原来的权限
	    	 	AuthRoleFunction condition=new AuthRoleFunction();
	    	 	condition.setRoleId(bean.getId());
	    	 	authRoleFunctionService.delete(condition);
	    	 	jedisPoolManager.set("isPermission","1");//表示需要进行权限更新
	            
	     }
		 //添加权限
	     if(roleIds != null && roleIds.length > 0){
	        	for(Integer actionId : roleIds){
	        		AuthRoleFunction roleAction=new AuthRoleFunction();
	        		roleAction.setRoleId(bean.getId());
	        		roleAction.setFunctionId(actionId);
	        		authRoleFunctionService.insertSelective(roleAction);
	        		addSysLog("角色管理","-", JSONObject.toJSONString(bean));
	        	}
	     }
		 return new JSONResultCode(0);
	 }
}
