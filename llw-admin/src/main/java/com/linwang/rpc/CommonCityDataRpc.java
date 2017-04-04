package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.ICommonCityDataService;
import com.linwang.entity.CommonCityData;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/commonCityData")
public class CommonCityDataRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private ICommonCityDataService commonCityDataService;
	
	@RequestMapping(value="commonCityDataPage",method=RequestMethod.GET)
	@RequiresPermissions("/commonCityData/commonCityDataPage")
	public String commonCityData() throws Exception{
	    return "commonCityDataList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/commonCityData/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         CommonCityData commonCityData=commonCityDataService.selectByPrimaryKey(id);
	         model.addAttribute("commonCityData", commonCityData);
		 }
	    return "commonCityDataAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(CommonCityData bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new CommonCityData();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<CommonCityData> pageBean = commonCityDataService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/commonCityData/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 CommonCityData bean=commonCityDataService.selectByPrimaryKey(id);
		 commonCityDataService.deleteByPrimaryKey(id);
		 addSysLog("省市区管理","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/commonCityData/save")
	 public JSONResultCode save(CommonCityData bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=commonCityDataService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("省市区管理","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 commonCityDataService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("省市区管理","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
