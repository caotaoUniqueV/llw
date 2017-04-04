package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ISysTaskLogService;
import com.linwang.entity.SysTaskLog;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sysTaskLog")
public class SysTaskLogRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private ISysTaskLogService sysTaskLogService;
	
	@RequestMapping(value="sysTaskLogPage",method=RequestMethod.GET)
	@RequiresPermissions("/sysTaskLog/sysTaskLogPage")
	public String sysTaskLog() throws Exception{
	    return "sysTaskLogList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/sysTaskLog/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         SysTaskLog sysTaskLog=sysTaskLogService.selectByPrimaryKey(id);
	         model.addAttribute("sysTaskLog", sysTaskLog);
		 }
	    return "sysTaskLogAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(SysTaskLog bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new SysTaskLog();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<SysTaskLog> pageBean = sysTaskLogService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysTaskLog/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 SysTaskLog bean=sysTaskLogService.selectByPrimaryKey(id);
		 sysTaskLogService.deleteByPrimaryKey(id);
		 addSysLog("定时任务日志","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysTaskLog/save")
	 public JSONResultCode save(SysTaskLog bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=sysTaskLogService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("定时任务日志","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 sysTaskLogService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("定时任务日志","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
