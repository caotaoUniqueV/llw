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
import com.google.common.base.Strings;
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisCacheManager;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.Page;
import com.linwang.uitls.PicCodeUtils;
import com.linwang.uitls.StaticProp;
import com.linwang.uitls.web.JSONResultCode;
import com.linwang.api.IAuthFunctionService;
import com.linwang.api.IAuthUserRoleService;
import com.linwang.api.IAuthUserService;
import com.linwang.entity.AuthFunction;
import com.linwang.entity.AuthRole;
import com.linwang.entity.AuthRoleFunction;
import com.linwang.entity.AuthUser;
import com.linwang.entity.AuthUserRole;


@Controller
@RequestMapping("/authUser")
public class AuthUserRpc extends BaseRpc{
	 @Reference(version="1.0.0")
	 private IAuthFunctionService authFunctionService;
	 @Reference(version="1.0.0")
	 private IAuthUserService authUserService; 
	 @Autowired
	 private RedisCacheManager redisCacheManager;
	 
	 @Reference(version="1.0.0")
	 private IAuthUserRoleService authUserRoleService;
	
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
		 AuthUser admin=(AuthUser) SecurityUtils.getSubject().getSession().getAttribute("admin");
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 List<AuthFunction> authFunctions=JSONObject.parseArray(jedisPoolManager.get("permissionAll:"+AccountDigestUtils.getMd5Pwd(admin.getUsername(),StaticProp.sysConfig.get("cookie.secret.key"))),AuthFunction.class);
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
		 AuthUser admin=(AuthUser) SecurityUtils.getSubject().getSession().getAttribute("admin");
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 List<AuthRole> authRoles=JSONObject.parseArray(jedisPoolManager.get("roles:"+AccountDigestUtils.getMd5Pwd(admin.getUsername(),StaticProp.sysConfig.get("cookie.secret.key"))),AuthRole.class);
		 model.addAttribute("authRoles", authRoles);
//		 
		 if(id!=null){
			//获取该角色的权限
			 AuthUserRole condition = new AuthUserRole();
	         condition.setUserId(id);
	         List<AuthUserRole> myRoles = authUserRoleService.getList(condition);
	         model.addAttribute("myRoles", myRoles);
	         
	         AuthUser authUser=authUserService.selectByPrimaryKey(id);
	         model.addAttribute("authUser", authUser);
		 }
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
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/authUser/del")
	 public JSONResultCode del(Integer id) throws Exception{
		 AuthUser admin=(AuthUser) SecurityUtils.getSubject().getSession().getAttribute("admin");
		 AuthUser bean=authUserService.selectByPrimaryKey(id);
		 authUserService.deleteByPrimaryKey(id);
		 addSysLog("管理员列表","删除",JSONObject.toJSONString(bean));
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
	     jedisPoolManager.set("isPermission:"+AccountDigestUtils.getMd5Pwd(admin.getUsername(),StaticProp.sysConfig.get("cookie.secret.key")),"1");//表示需要进行权限更新
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/authUser/save")
	 public JSONResultCode save(AuthUser bean,Integer[] roleIds,Integer type) throws Exception{
		 JedisPoolManager jedisPoolManager=redisCacheManager.getRedisManager();
		 if(bean.getId() == null){
			  AuthUser condition=new AuthUser();
			  condition.setUsername(bean.getUsername());
			  AuthUser aUser=authUserService.getByCondition(condition);
			  if(aUser!=null){
				  return new JSONResultCode(-1,"用户名已经存在");
			  }
	          //添加
			  bean.setPwd(AccountDigestUtils.getMd5Pwd(bean.getUsername(),bean.getPwd()));
	          int id=authUserService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("管理员列表","添加", JSONObject.toJSONString(bean));
	     }else{
	    	 if(type!=null&&type==1){//重置密码
	    		 bean=authUserService.selectByPrimaryKey(bean.getId());
	    		 bean.setPwd(AccountDigestUtils.getMd5Pwd(bean.getUsername(),"123456"));
	    		 authUserService.updateByPrimaryKeySelective(bean);
	    		 return new JSONResultCode(0);
	    	 }else if(type!=null&&type==2){//禁用账号
	    		 bean=authUserService.selectByPrimaryKey(bean.getId());
	    		 bean.setIsLock(true);
	    		 authUserService.updateByPrimaryKeySelective(bean);
	    		 return new JSONResultCode(0);
	    	 }else if(type!=null&&type==3){//启用账号
	    		 bean=authUserService.selectByPrimaryKey(bean.getId());
	    		 bean.setIsLock(false);
	    		 authUserService.updateByPrimaryKeySelective(bean);
	    		 return new JSONResultCode(0);
	    	 }
	            //修改
	    	 authUserService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("管理员列表","编辑",JSONObject.toJSONString(bean));
	    	 
	    	 //删除原来的权限
	    	 AuthUserRole condition=new AuthUserRole();
	    	 condition.setUserId(bean.getId());
	    	 authUserRoleService.delete(condition);
			 jedisPoolManager.set("isPermission:"+AccountDigestUtils.getMd5Pwd(bean.getUsername(),StaticProp.sysConfig.get("cookie.secret.key")),"1");//表示需要进行权限更新
	     }
		//添加权限
	     if(roleIds != null && roleIds.length > 0){
	        	for(Integer actionId : roleIds){
	        		AuthUserRole roleAction=new AuthUserRole();
	        		roleAction.setUserId(bean.getId());
	        		roleAction.setRoleId(actionId);
	        		authUserRoleService.insertSelective(roleAction);
	        		addSysLog("管理员列表","-", JSONObject.toJSONString(bean));
	        	}
	     }
		 return new JSONResultCode(0);
	 }
}
