package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ISysTaskHandelService;
import com.linwang.entity.SysTaskHandel;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sysTaskHandel")
public class SysTaskHandelRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private ISysTaskHandelService sysTaskHandelService;
	
	@RequestMapping(value="sysTaskHandelPage",method=RequestMethod.GET)
	@RequiresPermissions("/sysTaskHandel/sysTaskHandelPage")
	public String sysTaskHandel() throws Exception{
	    return "sysTaskHandelList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/sysTaskHandel/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         SysTaskHandel sysTaskHandel=sysTaskHandelService.selectByPrimaryKey(id);
	         model.addAttribute("sysTaskHandel", sysTaskHandel);
		 }
	    return "sysTaskHandelAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(SysTaskHandel bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new SysTaskHandel();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<SysTaskHandel> pageBean = sysTaskHandelService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysTaskHandel/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 SysTaskHandel bean=sysTaskHandelService.selectByPrimaryKey(id);
		 sysTaskHandelService.deleteByPrimaryKey(id);
		 addSysLog("定时任务","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/sysTaskHandel/save")
	 public JSONResultCode save(SysTaskHandel bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=sysTaskHandelService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("定时任务","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 sysTaskHandelService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("定时任务","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
