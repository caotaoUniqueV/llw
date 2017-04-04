package com.linwang.rpc;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IActivityInfoService;
import com.linwang.entity.ActivityInfo;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/activityInfo")
public class ActivityInfoRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private IActivityInfoService activityInfoService;
	
	@RequestMapping(value="activityInfoPage",method=RequestMethod.GET)
	@RequiresPermissions("/activityInfo/activityInfoPage")
	public String activityInfo() throws Exception{
	    return "activityInfoList";
	}
	
	@RequestMapping(value = "/webUpload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> webUpload(
			@RequestParam MultipartFile file	//上传的文件
			) {
		return upload(file);
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/activityInfo/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         ActivityInfo activityInfo=activityInfoService.selectByPrimaryKey(id);
	         model.addAttribute("activityInfo", activityInfo);
		 }
	    return "activityInfoAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(ActivityInfo bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new ActivityInfo();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<ActivityInfo> pageBean = activityInfoService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/activityInfo/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 ActivityInfo bean=activityInfoService.selectByPrimaryKey(id);
		 activityInfoService.deleteByPrimaryKey(id);
		 addSysLog("活动管理","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/activityInfo/save")
	 public JSONResultCode save(ActivityInfo bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=activityInfoService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("活动管理","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 activityInfoService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("活动管理","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
