package com.linwang.shiro;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.rpc.RpcContext;
import com.google.common.collect.Sets;
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
import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.IpUtil;


/**
 * @author wa
 *  2016-3-3
 */
public class ShiroRealm extends AuthorizingRealm {
	
	@Autowired
	private IAuthUserService authUserService;
//	
	@Autowired 
	private IAuthUserRoleService authUserRoleService;
	
	@Autowired 
	private IAuthFunctionService authFunctionService;
	
	@Autowired 
	private IAuthRoleFunctionService authRoleFunctionService;
	
	@Autowired 
	private IAuthRoleService authRoleService;
	
	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * 认证操作，判断一个请求是否被允许进入系统 
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();  				//得到用户名 
	    String password = new String((char[])token.getCredentials()); 	//得到密码
		//表没有记录插入默认记录
		 AuthUser admin=authUserService.getByCondition(null);
		 if(admin == null){
				admin = new AuthUser();
				admin.setId(1);
				admin.setIsLock(false);
				admin.setMobile("13500000000");
				admin.setUsername("admin");
				admin.setPwd(AccountDigestUtils.getMd5Pwd(admin.getUsername(), "admin"));
				admin.setRealname("超级管理员");
				authUserService.insertSelective(admin);
		 }
		 
		 AuthUser condition = new AuthUser();
	     condition.setUsername(username);
		 admin = authUserService.getByCondition(condition);
		 if(admin == null){
			 new Exception("账号不存在");
		 }
		 if(!admin.getPwd().equals(AccountDigestUtils.getMd5Pwd(admin.getUsername(), password))){
			 new Exception("账号或密码错误");
		 }
		 if(admin.getIsLock()){
			 new Exception("账号已禁用");
		 }
		 
		 admin.setDateLogin(new Date());
		 String serverIP = RpcContext.getContext().getRemoteHost();
		 admin.setIpLogin(serverIP);
		 authUserService.updateByPrimaryKey(admin);
		 
		 return new SimpleAuthenticationInfo(username, password, getName());
	}
	
	
	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String)principals.getPrimaryPrincipal();
		Set<String> roles=Sets.newHashSet();
		Set<String> permissions=Sets.newHashSet();
		AuthUser condition = new AuthUser();
	    condition.setUsername(username);
	    AuthUser admin = authUserService.getByCondition(condition);
	    
	    AuthUserRole authUserRole=new AuthUserRole();
	    authUserRole.setUserId(admin.getId());
	    List<AuthUserRole> roleActionsList = authUserRoleService.getList(authUserRole);
	    if(roleActionsList==null){
	    	return null;
	    }
	    if(roleActionsList.size()==0){
	    	return null;
	    }
	    for (AuthUserRole authUserRole_ : roleActionsList) {//获取角色
			AuthRole authRole=new AuthRole();
			authRole.setId(authUserRole_.getRoleId());
			AuthRole authRole_=authRoleService.getByCondition(authRole);
			roles.add(authRole_.getName());
			
			//获取角色的所有权限
			AuthRoleFunction authRoleFunction=new AuthRoleFunction();
			authRoleFunction.setRoleId(authUserRole_.getRoleId());
			List<AuthRoleFunction> authRoleFunctions = authRoleFunctionService.getList(authRoleFunction);
			if(authRoleFunctions==null){
		    	continue;
		    }
		    if(authRoleFunctions.size()==0){
		    	continue;
		    }
			for (AuthRoleFunction authRoleFunction_ : authRoleFunctions) {
				AuthFunction authFunction=new AuthFunction();
				authFunction.setId(authRoleFunction_.getFunctionId());
				AuthFunction authFunction_=authFunctionService.getByCondition(authFunction);
				permissions.add(authFunction_.getUrl());
			}
		}
	    
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
	}

}
