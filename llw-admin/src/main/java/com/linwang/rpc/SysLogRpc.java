package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ISysLogService;
import com.linwang.entity.SysLog;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sysLog")
public class SysLogRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private ISysLogService sysLogService;
	
	@RequestMapping(value="sysLogPage",method=RequestMethod.GET)
	@RequiresPermissions("/sysLog/sysLogPage")
	public String sysLog() throws Exception{
	    return "sysLogList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/sysLog/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         SysLog sysLog=sysLogService.selectByPrimaryKey(id);
	         model.addAttribute("sysLog", sysLog);
		 }
	    return "sysLogAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(SysLog bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new SysLog();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<SysLog> pageBean = sysLogService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysLog/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 SysLog bean=sysLogService.selectByPrimaryKey(id);
		 sysLogService.deleteByPrimaryKey(id);
		 addSysLog("系统日志","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysLog/save")
	 public JSONResultCode save(SysLog bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=sysLogService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("系统日志","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 sysLogService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("系统日志","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
