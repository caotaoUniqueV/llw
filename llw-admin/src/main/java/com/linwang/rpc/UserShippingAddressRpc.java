package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IUserShippingAddressService;
import com.linwang.entity.UserShippingAddress;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/userShippingAddress")
public class UserShippingAddressRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private IUserShippingAddressService userShippingAddressService;
	
	@RequestMapping(value="userShippingAddressPage",method=RequestMethod.GET)
	@RequiresPermissions("/userShippingAddress/userShippingAddressPage")
	public String userShippingAddress() throws Exception{
	    return "userShippingAddressList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/userShippingAddress/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         UserShippingAddress userShippingAddress=userShippingAddressService.selectByPrimaryKey(id);
	         model.addAttribute("userShippingAddress", userShippingAddress);
		 }
	    return "userShippingAddressAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(UserShippingAddress bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new UserShippingAddress();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<UserShippingAddress> pageBean = userShippingAddressService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/userShippingAddress/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 UserShippingAddress bean=userShippingAddressService.selectByPrimaryKey(id);
		 userShippingAddressService.deleteByPrimaryKey(id);
		 addSysLog("收货地址","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/userShippingAddress/save")
	 public JSONResultCode save(UserShippingAddress bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=userShippingAddressService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("收货地址","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 userShippingAddressService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("收货地址","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
