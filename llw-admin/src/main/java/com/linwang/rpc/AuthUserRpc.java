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
import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.Page;
import com.linwang.uitls.PicCodeUtils;
import com.linwang.uitls.web.JSONResultCode;
import com.linwang.api.IAuthFunctionService;
import com.linwang.api.IAuthUserService;
import com.linwang.base.BaseRpc;
import com.linwang.entity.AuthFunction;
import com.linwang.entity.AuthUser;


@Controller
@RequestMapping("/authUser")
public class AuthUserRpc extends BaseRpc{
	 @Reference(version="1.0.0")
	 private IAuthFunctionService authFunctionService;
	 @Reference(version="1.0.0")
	 private IAuthUserService authUserService; 
	
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
		 AuthFunction authFunction=new AuthFunction();
		 authFunction.setOrderBy("paixu ASC");
		 List<AuthFunction> authFunctions=authFunctionService.getList(authFunction);
		 model.addAttribute("authFunctions", authFunctions);
	    return "permissionSetting";
	 }
	 
	 @RequestMapping(value="permissionAdd",method=RequestMethod.GET)
	 @RequiresPermissions("/authUser/permissionAdd")
	 public String permissionAdd(Model model) throws Exception{
		 AuthFunction authFunction=new AuthFunction();
		 authFunction.setOrderBy("paixu ASC");
		 List<AuthFunction> authFunctions=authFunctionService.getList(authFunction);
		 model.addAttribute("authFunctions", authFunctions);
	    return "permissionAdd";
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
