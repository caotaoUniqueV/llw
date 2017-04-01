package com.linwang.shiro;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
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
import com.linwang.redis.JedisPoolManager;
import com.linwang.redis.RedisSessionDAO;
import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.IpUtil;
import com.linwang.uitls.web.Expressions;


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
	
	@Autowired
	private JedisPoolManager jedisPoolManager;
	
	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 * 认证操作，判断一个请求是否被允许进入系统 
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();  				//得到用户名 
	    String password = new String((char[])token.getCredentials()); 	//得到密码
	    Session session=SecurityUtils.getSubject().getSession();
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
			 throw new UnknownAccountException("账号不存在");
		 }
		 if(!admin.getPwd().equals(AccountDigestUtils.getMd5Pwd(admin.getUsername(), password))){
			 throw new IncorrectCredentialsException("账号或密码错误");
		 }
		 if(admin.getIsLock()){
			 throw new DisabledAccountException("账号已被禁用");
		 }
		 
		 admin.setDateLogin(new Date());
		 admin.setIpLogin(session.getHost());
		 authUserService.updateByPrimaryKey(admin);
		 
		 jedisPoolManager.set("admin",JSONObject.toJSONString(admin));
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
		permissions.add("admin:admin");
		List<AuthFunction> authFunctions=Lists.newArrayList();
		List<AuthRole> authRoles=Lists.newArrayList();
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
	    List<Integer> action2s = Lists.newArrayList(-1);
	    for (AuthUserRole authUserRole_ : roleActionsList) {//获取角色
	    	action2s.add(authUserRole_.getRoleId());
		}
	    AuthRole authRole=new AuthRole();
		authRole.and(Expressions.in("id", action2s));
		List<AuthRole> authRoles_=authRoleService.getList(authRole);
		for (AuthRole authRole2 : authRoles_) {
			roles.add(authRole2.getName());
			authRoles.add(authRole2);
		}
		//获取角色的所有权限
		AuthRoleFunction authRoleFunction=new AuthRoleFunction();
		authRole.and(Expressions.in("roleId", action2s));
		List<AuthRoleFunction> authRoleFunctions =authRoleFunctionService.getList(authRoleFunction);
		if(authRoleFunctions==null){
			return null;
	    }
	    if(authRoleFunctions.size()==0){
	    	return null;
	    }
	    List<Integer> actions = Lists.newArrayList(-1);
        for(AuthRoleFunction roleActions : authRoleFunctions){
            actions.add(roleActions.getFunctionId());
        }
	    AuthFunction authFunction=new AuthFunction();
	    authFunction.setOrderBy("paixu ASC");
		authFunction.and(Expressions.in("id", actions));
		List<AuthFunction> authFunction_=authFunctionService.getList(authFunction);
		for (AuthFunction authFunction2 : authFunction_) {
			if(!Strings.isNullOrEmpty(authFunction2.getUrl())){
				permissions.add(authFunction2.getUrl());
			}
			authFunctions.add(authFunction2);
		}
	    jedisPoolManager.set("permission",JSONObject.toJSONString(authFunctions));
		jedisPoolManager.set("roles",JSONObject.toJSONString(authRoles));
	    
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
	}

}
