package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ICouponModuService;
import com.linwang.entity.CouponModu;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/couponModu")
public class CouponModuRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private ICouponModuService couponModuService;
	
	@RequestMapping(value="couponModuPage",method=RequestMethod.GET)
	@RequiresPermissions("/couponModu/couponModuPage")
	public String couponModu() throws Exception{
	    return "couponModuList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/couponModu/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         CouponModu couponModu=couponModuService.selectByPrimaryKey(id);
	         model.addAttribute("couponModu", couponModu);
		 }
	    return "couponModuAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(CouponModu bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new CouponModu();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<CouponModu> pageBean = couponModuService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/couponModu/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 CouponModu bean=couponModuService.selectByPrimaryKey(id);
		 couponModuService.deleteByPrimaryKey(id);
		 addSysLog("礼品券模板","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/couponModu/save")
	 public JSONResultCode save(CouponModu bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=couponModuService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("礼品券模板","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 couponModuService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("礼品券模板","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
