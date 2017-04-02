package com.linwang.rpc;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ITLinkService;
import com.linwang.entity.TLink;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;

@Controller
@RequestMapping("/link")
public class LinkRpc extends BaseRpc{
	 @Reference(version="1.0.0")
	 private ITLinkService tLinkService;
	 
	
	 @RequestMapping(value="manage",method=RequestMethod.GET)
	 @RequiresPermissions("/link/manage")
	 public String manage(Model model) throws Exception{
	    return "link/manage";
	 }
	 
	 @RequestMapping(value="list",method=RequestMethod.GET)
	 @ResponseBody
	 public JSONResultCode list(TLink bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new TLink();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<TLink> pageBean = tLinkService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="add",method=RequestMethod.GET)
	 @RequiresPermissions("/link/add")
	 public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
			 TLink condition=new TLink();
			 condition.setId(id);
			 TLink link= tLinkService.getByCondition(condition);
			 model.addAttribute("link", link);
		 }
	    return "link/add";
	 }
	 
	 @RequestMapping(value="delete",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/link/delete")
	 public JSONResultCode del(Integer id) throws Exception{
		 TLink bean=tLinkService.selectByPrimaryKey(id);
		 tLinkService.deleteByPrimaryKey(id);
		 addSysLog("友情链接管理","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/link/save")
	 public JSONResultCode save(TLink bean,Integer[] roleIds) throws Exception{
		 if(bean.getId() == null){
	            //添加
	            tLinkService.insertSelective(bean);
	            addSysLog("友情链接管理","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 	tLinkService.updateByPrimaryKeySelective(bean);
	    	 	addSysLog("友情链接管理","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0,"保存成功");
	 }
}
