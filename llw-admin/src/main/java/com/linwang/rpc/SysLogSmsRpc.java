package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ISysLogSmsService;
import com.linwang.entity.SysLogSms;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sysLogSms")
public class SysLogSmsRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private ISysLogSmsService sysLogSmsService;
	
	@RequestMapping(value="sysLogSmsPage",method=RequestMethod.GET)
	@RequiresPermissions("/sysLogSms/sysLogSmsPage")
	public String sysLogSms() throws Exception{
	    return "sysLogSmsList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/sysLogSms/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         SysLogSms sysLogSms=sysLogSmsService.selectByPrimaryKey(id);
	         model.addAttribute("sysLogSms", sysLogSms);
		 }
	    return "sysLogSmsAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(SysLogSms bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new SysLogSms();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<SysLogSms> pageBean = sysLogSmsService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysLogSms/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 SysLogSms bean=sysLogSmsService.selectByPrimaryKey(id);
		 sysLogSmsService.deleteByPrimaryKey(id);
		 addSysLog("短信记录","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysLogSms/save")
	 public JSONResultCode save(SysLogSms bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=sysLogSmsService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("短信记录","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 sysLogSmsService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("短信记录","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
