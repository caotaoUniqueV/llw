package com.linwang.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/** 
* 备注： 限制登录次数，如果5次出错，锁定1个小时 
*/ 
public class credentialsMatcher extends HashedCredentialsMatcher {
//	@Override 
//    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) { 
//        String username = (String) token.getPrincipal(); 
//        Object element = EhcacheUtil.getItem(username); 
//        if (element == null) { 
//            EhcacheUtil.putItem(username, 1); 
//            element=0; 
//        }else{ 
//            int count=Integer.parseInt(element.toString())+1; 
//            element=count; 
//            EhcacheUtil.putItem(username,element); 
//        } 
//        AtomicInteger retryCount = new AtomicInteger(Integer.parseInt(element.toString())); 
//        if (retryCount.incrementAndGet() > 5) { 
//            throw new ExcessiveAttemptsException(); 
//        } 
//        boolean matches = super.doCredentialsMatch(token, info); 
//        if (matches) { 
//            EhcacheUtil.removeItem(username); 
//        } 
//        return matches; 
//    } 
}
