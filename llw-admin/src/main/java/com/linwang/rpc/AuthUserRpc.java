package com.linwang.rpc;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linwang.uitls.AccountDigestUtils;
import com.linwang.uitls.PicCodeUtils;
import com.linwang.uitls.web.JSONResultCode;
import com.linwang.base.BaseRpc;


@Controller
@RequestMapping("/authUser")
public class AuthUserRpc extends BaseRpc{
	
	 @RequestMapping(value="login",method=RequestMethod.POST)
	 @ResponseBody
	 public JSONResultCode login(@RequestParam String username,@RequestParam String password,@RequestParam String picCode) throws Exception{
		 if(!PicCodeUtils.checkCodeIsEqual(getRequest(), picCode)){
			return new JSONResultCode(-1,"验证码不正确");
		 }
		 UsernamePasswordToken token = new UsernamePasswordToken(username, AccountDigestUtils.getMd5Pwd(username, password)); 
		 SecurityUtils.getSubject().login(token);
		 return new JSONResultCode(0);
	 }
	 
}
