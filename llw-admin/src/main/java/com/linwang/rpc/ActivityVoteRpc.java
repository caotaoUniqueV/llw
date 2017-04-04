package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.IActivityVoteService;
import com.linwang.entity.ActivityVote;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/activityVote")
public class ActivityVoteRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private IActivityVoteService activityVoteService;
	
	@RequestMapping(value="activityVotePage",method=RequestMethod.GET)
	@RequiresPermissions("/activityVote/activityVotePage")
	public String activityVote() throws Exception{
	    return "activityVoteList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/activityVote/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         ActivityVote activityVote=activityVoteService.selectByPrimaryKey(id);
	         model.addAttribute("activityVote", activityVote);
		 }
	    return "activityVoteAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(ActivityVote bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new ActivityVote();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<ActivityVote> pageBean = activityVoteService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/activityVote/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 ActivityVote bean=activityVoteService.selectByPrimaryKey(id);
		 activityVoteService.deleteByPrimaryKey(id);
		 addSysLog("投票设置","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/activityVote/save")
	 public JSONResultCode save(ActivityVote bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=activityVoteService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("投票设置","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 activityVoteService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("投票设置","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
