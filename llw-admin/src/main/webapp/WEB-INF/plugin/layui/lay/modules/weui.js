layui.define(function(a) {
	"use strict";
	
	;!function(pkg, undefined){
	
		//滚动加载
		var load=function(options){
			 var js = document.scripts || document.getElementsByTagName("script");
		   	 var corePath;
		   	 for (var i = js.length; i > 0; i--) {
		   	      if (js[i - 1].src.indexOf("oliverui.js") > -1) {
		   	    	corePath = js[i - 1].src.substring(0, js[i - 1].src.lastIndexOf("/") + 1);
		   	      }
		   	 }
		   
			$.ajax({
			      url: corePath+"weui/jquery-weui.js",
			      dataType: "script",
			      cache: false
			}).done(function() {
				var opts = $.extend({}, weuiDefaluts, options); //使用jQuery.extend 覆盖插件默认参数
				if(opts.elem!=null&&opts.elem!=""&&opts.elem!=undefined){
					single(opts);
				}else{
					multiple(opts);
				}
			})
		};
		
		var multiple=function(opts){//多个
//			if(opts.page==1){
//				init(opts);//初始化
//			};
//			
//			//滚动加载
//			$(opts.cont).infinite(opts.distance).on("infinite", function() {
//				if(opts.loading) return;
//				opts.loading = true;
//				
//				$.fn.ajax({
//					url : opts.url,
//					data : data(opts),
//					success : function(res){
//						opts.done(res,function(html,total){
//							$(opts.elem).append(html);
//							if(opts.page<total){
//								$(".weui-loadmore").remove();
//								$(opts.elem).after('<div class="weui-loadmore"><i class="weui-loading"></i><span class="weui-loadmore__tips">正在加载</span></div>');
//								opts.loading=false;
//							}else{
//								$(".weui-loadmore").remove();
//								$(opts.elem).after(opts.end);
//							}
//							opts.page++;
//						});
//					}
//				})
//				
//			});
		}
		
		var single=function(opts){//单个
			if(opts.page==1){
				$(opts.elem).before(opts.start);
				init(opts);//初始化
			};
			
			//下拉刷新
			$(opts.cont).pullToRefresh().on("pull-to-refresh", function() {
				$.fn.ajax({
					url : opts.url,
					data : data(opts),
					success : function(res){
						opts.done(res,function(html,total){
							$(opts.elem).prepend(html);
							$(".weui-loadmore").remove();
							$(opts.elem).after('<div class="weui-loadmore"><i class="weui-loading"></i><span class="weui-loadmore__tips">正在加载</span></div>');
							opts.page++;
							opts.loading=false;
							$(document.body).pullToRefreshDone();
						});
					}
				})
		    });
			
			//滚动加载
			$(opts.cont).infinite(opts.distance).on("infinite", function() {
				if(opts.loading) return;
				opts.loading = true;
				
				$.fn.ajax({
					url : opts.url,
					data : data(opts),
					success : function(res){
						opts.done(res,function(html,total){
							$(opts.elem).append(html);
							if(opts.page<total){
								$(".weui-loadmore").remove();
								$(opts.elem).after('<div class="weui-loadmore"><i class="weui-loading"></i><span class="weui-loadmore__tips">正在加载</span></div>');
								opts.loading=false;
							}else{
								$(".weui-loadmore").remove();
								$(opts.elem).after(opts.end);
							}
							opts.page++;
						});
					}
				})
				
			});
		}
		
		//初始化
		var init=function(opts){
			$.fn.ajax({
				url : opts.url,
				data : data(opts),
				icon : 3,
				success : function(res){
					opts.done(res,function(html,total){
						$(opts.elem).html(html);
						if(opts.page<total){
							$(opts.elem).after('<div class="weui-loadmore"><i class="weui-loading"></i><span class="weui-loadmore__tips">正在加载</span></div>');
						}else{
							$(".weui-loadmore").remove();
							$(opts.elem).after(opts.end);
							opts.loading=true;
						}
						opts.page++;
					});
				}
			})
		}
		
		var data=function(options){
			var opts = $.extend({}, weuiDefaluts, options); //使用jQuery.extend 覆盖插件默认参数
			if(opts.data==null||opts.data==""||opts.data==undefined){
				return {page : opts.page,rows : opts.rows};
			}else if(!JSON.stringify(opts.data).match("^\{(.+:.+,*){1,}\}$")){
				var dataStrs=opts.data.split("&");
				var tempData="{";
				$.each(dataStrs,function(i,v){
					var vStr=v.split("=");
					tempData+="\""+vStr[0]+"\""+":"+vStr[1]+",";
				})
				return jQuery.parseJSON(tempData.substring(0,tempData.length-1)+",\page\":"+opts.page+",\rows\":"+opts.rows+"}");
			}else{
				var data=opts.data;
				data.page=opts.page;
				data.rows=opts.rows;
				return data;
			}
		};
		
		//默认值
		var weuiDefaluts={
			rows : 10,
			page : 1,
			end : '<div class="weui-loadmore weui-loadmore_line"><span class="weui-loadmore__tips">没有更多了</span></div>',
			start : "<div class='weui-pull-to-refresh__layer' style='margin-bottom: 23px;'><div class='weui-pull-to-refresh__arrow'></div><div class='weui-pull-to-refresh__preloader'></div><div class='down'>下拉刷新</div><div class='up'>释放刷新</div><div class='refresh'>正在刷新</div></div>",
			loading : false,//状态标记
			distance : 50
		};
		
		
		;!function(){
			this.load = load;
		}.call(window[pkg] = window[pkg] || {});
		
	}('WUI');
	
	a("weui",null);
})