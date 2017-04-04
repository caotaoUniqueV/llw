package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IUserRechargeService;
import com.linwang.entity.UserRecharge;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/userRecharge")
public class UserRechargeRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private IUserRechargeService userRechargeService;
	
	@RequestMapping(value="userRechargePage",method=RequestMethod.GET)
	@RequiresPermissions("/userRecharge/userRechargePage")
	public String userRecharge() throws Exception{
	    return "userRechargeList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/userRecharge/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         UserRecharge userRecharge=userRechargeService.selectByPrimaryKey(id);
	         model.addAttribute("userRecharge", userRecharge);
		 }
	    return "userRechargeAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(UserRecharge bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new UserRecharge();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<UserRecharge> pageBean = userRechargeService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/userRecharge/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 UserRecharge bean=userRechargeService.selectByPrimaryKey(id);
		 userRechargeService.deleteByPrimaryKey(id);
		 addSysLog("重置记录","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/userRecharge/save")
	 public JSONResultCode save(UserRecharge bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=userRechargeService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("重置记录","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 userRechargeService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("重置记录","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
