package com.linwang.rpc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.ui.Model;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.linwang.api.INewsArticleService;
import com.linwang.entity.NewsArticle;
import com.linwang.rpc.base.BaseRpc;
import com.linwang.uitls.Page;
import com.linwang.uitls.web.JSONResultCode;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/newsArticle")
public class NewsArticleRpc extends BaseRpc{

	@Reference(version="1.0.0")
	private INewsArticleService newsArticleService;
	
	@RequestMapping(value="newsArticlePage",method=RequestMethod.GET)
	@RequiresPermissions("/newsArticle/newsArticlePage")
	public String newsArticle() throws Exception{
	    return "newsArticleList";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	@RequiresPermissions("/newsArticle/add")
    public String add(Model model,Integer id) throws Exception{
		 if(id!=null){
	         NewsArticle newsArticle=newsArticleService.selectByPrimaryKey(id);
	         model.addAttribute("newsArticle", newsArticle);
		 }
	    return "newsArticleAdd";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	@ResponseBody
	public JSONResultCode list(NewsArticle bean,Integer page,Integer rows) throws Exception{
		if (bean == null) {
	        bean = new NewsArticle();
	    }
		bean.setPageNumber(page);
	    bean.setPageSize(rows);
		Page<NewsArticle> pageBean = newsArticleService.getPage(bean,getRequest().getParameterMap());
	    return new JSONResultCode(0,pageBean.getTotalPage()+"",pageBean);
	 }
	 
	 @RequestMapping(value="del",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/newsArticle/del")
	 public JSONResultCode del(Integer id) throws Exception{
	 	 NewsArticle bean=newsArticleService.selectByPrimaryKey(id);
		 newsArticleService.deleteByPrimaryKey(id);
		 addSysLog("文章管理","删除",JSONObject.toJSONString(bean));
		 return new JSONResultCode(0);
	 }
	 
	 @RequestMapping(value="save",method=RequestMethod.POST)
	 @ResponseBody
	 @RequiresPermissions("/newsArticle/save")
	 public JSONResultCode save(NewsArticle bean) throws Exception{
		 if(bean.getId() == null){
	          //添加
	          int id=newsArticleService.insertSelective(bean);
	          bean.setId(id);
	          addSysLog("文章管理","添加", JSONObject.toJSONString(bean));
	     }else{
	            //修改
	    	 newsArticleService.updateByPrimaryKeySelective(bean);
	    	 addSysLog("文章管理","编辑",JSONObject.toJSONString(bean));
	     }
		 return new JSONResultCode(0);
	 }
}
