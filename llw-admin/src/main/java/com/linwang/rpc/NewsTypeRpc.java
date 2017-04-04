package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.INewsTypeService;
import com.linwang.entity.NewsType;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/newsType")
public class NewsTypeRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private INewsTypeService newsTypeService;
	
	@RequestMapping(value="newsTypePage",method=RequestMethod.GET)
	@RequiresPermissions("/newsType/newsTypePage")
	public String newsType() throws Exception{
	    return "newsTypeList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/newsType/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         NewsType newsType=newsTypeService.selectByPrimaryKey(id);
	         model.addAttribute("newsType", newsType);
		 }
	    return "newsTypeAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(NewsType bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new NewsType();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<NewsType> pageBean = newsTypeService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/newsType/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 NewsType bean=newsTypeService.selectByPrimaryKey(id);
		 newsTypeService.deleteByPrimaryKey(id);
		 addSysLog("文章分类","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/newsType/save")
	 public JSONResultCode save(NewsType bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=newsTypeService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("文章分类","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 newsTypeService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("文章分类","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
