package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ISysConfigService;
import com.linwang.entity.SysConfig;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sysConfig")
public class SysConfigRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private ISysConfigService sysConfigService;
	
	@RequestMapping(value="sysConfigPage",method=RequestMethod.GET)
	@RequiresPermissions("/sysConfig/sysConfigPage")
	public String sysConfig() throws Exception{
	    return "sysConfigList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/sysConfig/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         SysConfig sysConfig=sysConfigService.selectByPrimaryKey(id);
	         model.addAttribute("sysConfig", sysConfig);
		 }
	    return "sysConfigAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(SysConfig bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new SysConfig();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<SysConfig> pageBean = sysConfigService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysConfig/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 SysConfig bean=sysConfigService.selectByPrimaryKey(id);
		 sysConfigService.deleteByPrimaryKey(id);
		 addSysLog("系统参数","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysConfig/save")
	 public JSONResultCode save(SysConfig bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=sysConfigService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("系统参数","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 sysConfigService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("系统参数","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
