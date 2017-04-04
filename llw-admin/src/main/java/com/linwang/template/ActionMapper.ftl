package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.I${methodName}Service;
import com.linwang.entity.${methodName};
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/${rpcService}")
public class ${methodName}Rpc extends BaseRpc{

	@Reference(version="1.0.0")
	private I${methodName}Service ${rpcService}Service;
	
	@RequestMapping(value="${rpcService}Page",method=RequestMethod.GET)
	@RequiresPermissions("/${rpcService}/${rpcService}Page")
	public String ${rpcService}() throws Exception{
	    return "${rpcService}List";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/${rpcService}/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         ${methodName} ${rpcService}=${rpcService}Service.selectByPrimaryKey(id);
	         model.addAttribute("${rpcService}", ${rpcService});
		 }
	    return "${rpcService}Add";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(${methodName} bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new ${methodName}();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<${methodName}> pageBean = ${rpcService}Service.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/${rpcService}/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 ${methodName} bean=${rpcService}Service.selectByPrimaryKey(id);
		 ${rpcService}Service.deleteByPrimaryKey(id);
		 addSysLog("${modular}","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/${rpcService}/save")
	 public JSONResultCode save(${methodName} bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=${rpcService}Service.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("${modular}","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 ${rpcService}Service.updateByPrimaryKeySelective(bean);
	    	 addSysLog("${modular}","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
