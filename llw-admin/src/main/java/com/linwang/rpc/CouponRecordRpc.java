package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ICouponRecordService;
import com.linwang.entity.CouponRecord;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/couponRecord")
public class CouponRecordRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private ICouponRecordService couponRecordService;
	
	@RequestMapping(value="couponRecordPage",method=RequestMethod.GET)
	@RequiresPermissions("/couponRecord/couponRecordPage")
	public String couponRecord() throws Exception{
	    return "couponRecordList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/couponRecord/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         CouponRecord couponRecord=couponRecordService.selectByPrimaryKey(id);
	         model.addAttribute("couponRecord", couponRecord);
		 }
	    return "couponRecordAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(CouponRecord bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new CouponRecord();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<CouponRecord> pageBean = couponRecordService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/couponRecord/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 CouponRecord bean=couponRecordService.selectByPrimaryKey(id);
		 couponRecordService.deleteByPrimaryKey(id);
		 addSysLog("领取记录","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/couponRecord/save")
	 public JSONResultCode save(CouponRecord bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=couponRecordService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("领取记录","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 couponRecordService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("领取记录","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
