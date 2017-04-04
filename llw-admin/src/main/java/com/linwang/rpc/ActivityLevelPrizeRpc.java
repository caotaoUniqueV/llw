package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IActivityLevelPrizeService;
import com.linwang.entity.ActivityLevelPrize;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/activityLevelPrize")
public class ActivityLevelPrizeRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private IActivityLevelPrizeService activityLevelPrizeService;
	
	@RequestMapping(value="activityLevelPrizePage",method=RequestMethod.GET)
	@RequiresPermissions("/activityLevelPrize/activityLevelPrizePage")
	public String activityLevelPrize() throws Exception{
	    return "activityLevelPrizeList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/activityLevelPrize/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         ActivityLevelPrize activityLevelPrize=activityLevelPrizeService.selectByPrimaryKey(id);
	         model.addAttribute("activityLevelPrize", activityLevelPrize);
		 }
	    return "activityLevelPrizeAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(ActivityLevelPrize bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new ActivityLevelPrize();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<ActivityLevelPrize> pageBean = activityLevelPrizeService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/activityLevelPrize/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 ActivityLevelPrize bean=activityLevelPrizeService.selectByPrimaryKey(id);
		 activityLevelPrizeService.deleteByPrimaryKey(id);
		 addSysLog("奖品","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/activityLevelPrize/save")
	 public JSONResultCode save(ActivityLevelPrize bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=activityLevelPrizeService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("奖品","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 activityLevelPrizeService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("奖品","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
