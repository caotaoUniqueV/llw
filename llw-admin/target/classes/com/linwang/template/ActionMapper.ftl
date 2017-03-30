package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.linwang.api.I${methodName}Service;
import com.linwang.entity.${methodName};
import com.linwang.base.BaseRpc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/${rpcService}")
public class ${methodName}Rpc extends BaseRpc{

	@Reference(version="1.0.0")
	private I${methodName}Service ${rpcService}Service;
	
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 public String save(${methodName} ${rpcService}) throws Exception{
		 
	    return "index";
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 public String del(java.lang.Integer id) throws Exception{
		 
	    return "index";
	 }
	 
	 @RequestMapping(value="update",method=RequestMethod.POST)
	 @ResponseBody
	 public String update(${methodName} ${rpcService}) throws Exception{
		 
	    return "index";
	 }
	 
	 @RequestMapping(value="find",method=RequestMethod.POST)
	 @ResponseBody
	 public String find(${methodName} ${rpcService}) throws Exception{
		 
	    return "index";
	 }
}
