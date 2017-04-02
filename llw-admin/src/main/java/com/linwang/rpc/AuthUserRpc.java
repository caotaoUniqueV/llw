package com.linwang.rpc;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisCacheManager;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.Page;
import com.linwang.uitls.PicCodeUtils;
import com.linwang.uitls.web.JSONResultCode;
import com.linwang.api.IAuthFunctionService;
import com.linwang.api.IAuthUserService;
import com.linwang.entity.AuthFunction;
import com.linwang.entity.AuthRole;
import com.linwang.entity.AuthRoleFunction;
import com.linwang.entity.AuthUser;


@Controller
@RequestMapping("/authUser")
public class AuthUserRpc extends BaseRpc{
	 @Reference(version="1.0.0")
	 private IAuthFunctionService authFunctionService;
	 @Reference(version="1.0.0")
	 private IAuthUserService authUserService; 
	 @Autowired
	 private RedisCacheManager redisCacheManager;
	
	 @RequestMapping(value="login",method=RequestMethod.POST)
	 @ResponseBody
	 public JSONResultCode login(@RequestParam String username,@RequestParam String password,@RequestParam String picCode) throws Exception{
		 if(!PicCodeUtils.checkCodeIsEqual(getRequest(), picCode)){
			return new JSONResultCode(-1,"验证码不正确");
		 }
		 UsernamePasswordToken token = new UsernamePasswordToken(username, password); 
		 SecurityUtils.getSubject().login(token);
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="permissionSetting",method=RequestMethod.GET)
	 @RequiresPermissions("/authUser/permissionSetting")
	 public String permissionSetting(Model model) throws Exception{
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 List<AuthFunction> authFunctions=JSONObject.parseArray(jedisPoolManager.get("permissionAll"),AuthFunction.class);
		 model.addAttribute("authFunctions", authFunctions);
	    return "permissionSetting";
	 }
	 
	 @RequestMapping(value="roleManagement",method=RequestMethod.GET)
	 @RequiresPermissions("/authUser/roleManagement")
	 public String roleManagement() throws Exception{
	    return "roleManagement";
	 }
	 
	 @RequestMapping(value="managers",method=RequestMethod.GET)
	 @RequiresPermissions("/authUser/managers")
	 public String managers() throws Exception{
	    return "managers";
	 }
	 
	 @RequestMapping(value="managerAdd",method=RequestMethod.GET)
	 @RequiresPermissions("/authUser/managerAdd")
	 public String roleAdd(Model model,Integer id) throws Exception{
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
//		 List<AuthFunction> authFunctions=JSONObject.parseArray(jedisPoolManager.get("permissionAll"),AuthFunction.class);
//		 model.addAttribute("authFunctions", authFunctions);
//		 
//		 if(id!=null){
//			//获取该角色的权限
//	         AuthRoleFunction condition2 = new AuthRoleFunction();
//	         condition2.setRoleId(id);
//	         List<AuthRoleFunction> myActions = authRoleFunctionService.getList(condition2);
//	         model.addAttribute("myActions", myActions);
//		 }
	    return "managerAdd";
	 }
	 
	 @RequestMapping(value="list",method=RequestMethod.GET)
	 @ResponseBody
	 public JSONResultCode list(AuthUser bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new AuthUser();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<AuthUser> pageBean = authUserService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
}
