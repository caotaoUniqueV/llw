package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IConfigNoticeTempleService;
import com.linwang.entity.ConfigNoticeTemple;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/configNoticeTemple")
public class ConfigNoticeTempleRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private IConfigNoticeTempleService configNoticeTempleService;
	
	@RequestMapping(value="configNoticeTemplePage",method=RequestMethod.GET)
	@RequiresPermissions("/configNoticeTemple/configNoticeTemplePage")
	public String configNoticeTemple() throws Exception{
	    return "configNoticeTempleList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/configNoticeTemple/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         ConfigNoticeTemple configNoticeTemple=configNoticeTempleService.selectByPrimaryKey(id);
	         model.addAttribute("configNoticeTemple", configNoticeTemple);
		 }
	    return "configNoticeTempleAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(ConfigNoticeTemple bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new ConfigNoticeTemple();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<ConfigNoticeTemple> pageBean = configNoticeTempleService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/configNoticeTemple/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 ConfigNoticeTemple bean=configNoticeTempleService.selectByPrimaryKey(id);
		 configNoticeTempleService.deleteByPrimaryKey(id);
		 addSysLog("短信模板","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/configNoticeTemple/save")
	 public JSONResultCode save(ConfigNoticeTemple bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=configNoticeTempleService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("短信模板","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 configNoticeTempleService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("短信模板","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
