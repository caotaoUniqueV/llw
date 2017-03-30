;!function(pkg, undefined){
	var wflow=null;
	var wlaypage=null;
	window.laytpl=null;
	var use=function(options,callback){
		 var js = document.scripts || document.getElementsByTagName("script");
	   	 var jsPath;
	   	 for (var i = js.length; i > 0; i--) {
	   	      if (js[i - 1].src.indexOf("oliverui.js") > -1) {
	   	           jsPath = js[i - 1].src.substring(0, js[i - 1].src.lastIndexOf("/") + 1);
	   	      }
	   	 }
		var head= document.getElementsByTagName('head')[0]; 
        var link = document.createElement("link");
        link.type = "text/css";
        link.rel = "stylesheet";
        link.href = jsPath+"css/layui.css";
		head.appendChild(link);
		
		$.ajax({
		      url: jsPath+"layui2.js",
		      dataType: "script",
		      cache: false
		}).done(function() {
			var ua = navigator.userAgent;
			var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
		    isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
		    isAndroid = ua.match(/(Android)\s+([\d.]+)/),
		    isMobile = isIphone || isAndroid;
		    if(isMobile) {
		    	$.ajax({
				      url: jsPath+"layerMobile/layer.js",
				      dataType: "script",
				      cache: false
				})
		    }
		    if(options.indexOf("viewer")>-1){
		    	 var link = document.createElement("link");
		         link.type = "text/css";
		         link.rel = "stylesheet";
		         link.href = jsPath+"css/viewer.min.css";
		         link.id='oliverLoad';
		 		 head.appendChild(link);
		    }
				layui.use(options, function(){
					 $.each(options,function(n,value){
						 switch (value) {
							case "laypage":
								wlaypage=layui.laypage;
							break;
							case "flow":
								wflow=layui.flow;
							break;
							case "laytpl":
								laytpl=layui.laytpl;
							break;
						}
				     });
					 if(callback!=null&&callback!=""&&callback!=undefined){
						 callback(ui);
					 }
				});
		});
	}
	
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
		if (this[i] == val) return i;
		}
		return -1;
	};
	
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
		this.splice(index, 1);
		}
	};
	
	var ui={
			flow : function(options){
				var opts = $.extend({}, odefaluts, options); //使用jQuery.extend 覆盖插件默认参数
				if(opts.versions=="v1"){
					console.log(opts);
					 wflow.load({
						    elem: opts.elem //流加载容器
						    ,isAuto: opts.isAuto
						    ,isLazyimg : opts.isLazyimg
//						    ,end : opts.end
						    ,done: function(page, next){ //执行下一页的回调
						    	try {
				        			$(opts.elem).viewer("destroy");
								} catch (e) {
								}
						    	var data=opts.data;
								if(data==null||data==""||data==undefined){
									data={
										page : page,
										rows : opts.rows
									};
								}else{
									data.page=page;
									data.rows=opts.rows;
								}
						    	var lis = [];
						        //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
						        $.fn.ajax({
						        	url : opts.url,
						        	data:data
						        },function(res){
						        	if(res.result.success){
						        		var txt=opts.success(res);
							        	next(txt, page < res.pager.total);    
							        	try {
						        			$(opts.elem).viewer();
										} catch (e) {
										}
						        	}else{
						        		showToast(res.result.msg);
						        	}
						        })
						    }
						  });
				}else{
					 wflow.load({
						    elem: opts.elem //流加载容器
						    ,isAuto: opts.isAuto
						    ,isLazyimg : opts.isLazyimg
//						    ,end : opts.end
						    ,done: function(page, next){ //执行下一页的回调
						    	try {
				        			$(opts.elem).viewer("destroy");
								} catch (e) {
								}
						    	var data=opts.data;
								if(data==null||data==""||data==undefined){
									data={
										page : page,
										rows : opts.rows
									};
								}else{
									data.page=page;
									data.rows=opts.rows;
								}
						    	var lis = [];
						        //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
						        $.fn.ajax({
						        	url : opts.url,
						        	data:data
						        },function(res){
						        	if(res.result.success){
						        		var txt=opts.success(res);
							        	next(txt, page < res.result.code);    
							        	try {
						        			$(opts.elem).viewer();
										} catch (e) {
										}
						        	}else{
						        		showToast(res.result.msg);
						        	}
						        })
						    }
						  });
				}
			},
			laypage : function(options){
				var total=0;
				var $p=null;
				var page2=1;
				if(location.hash.replace('#!oliver=', '')!=null&&location.hash.replace('#!oliver=', '')!=""&&location.hash.replace('#!oliver=', '')!=undefined){
					page2=location.hash.replace('#!oliver=', '');
					$p=page2;
				}
				var b=false;
				var opts = $.extend({}, odefaluts, options); //使用jQuery.extend 覆盖插件默认参数
				 var data=opts.data;
				    try {
				    	if(data==null||data==""||data==undefined){
							data={
								page : page2,
								rows : opts.rows
							};
						}else if(data.indexOf("&")>-1){
							var p="&page="+page2+"&rows="+opts.rows;
							data=data+p;
						}else{
							data.page=page2;
							data.rows=opts.rows;
						}
					} catch (e) {
						if(data==null||data==""||data==undefined){
							data={
								page : page2,
								rows : opts.rows
							};
						}else{
							data.page=page2;
							data.rows=opts.rows;
						}
					}
					  $.fn.ajax({
				        	url : opts.url,
				        	icon : opts.icon,
				        	data : data
				        },function(res){
				        	b=true;
				        	if(opts.versions=="v1"){
				        		total=res.pager.total;
				        	}else{
				        		total=parseInt(res.result.msg);
				        	}
				        	var style=opts.style;
				        	var laypage={};
				        	laypage.cont=opts.cont;
				        	laypage.skip=opts.skip;
				        	laypage.skin=opts.skin;
				        	laypage.pages=res.pager.total;
				        	laypage.groups=opts.groups;
				        	laypage.curr=location.hash.replace('#!oliver=', '');
				        	laypage.hash="oliver";
				        	if((opts.first!=null&&opts.first!=""&&opts.first!=undefined)||!opts.first){
				        		laypage.first=opts.first;
				        	}
				        	if((opts.last!=null&&opts.last!=""&&opts.last!=undefined)||!opts.last){
				        		laypage.last=opts.last;
				        	}
				        	if((opts.prev!=null&&opts.prev!=""&&opts.prev!=undefined)||opts.prev){
				        		laypage.prev=opts.prev;
				        	}
				        	if((opts.next!=null&&opts.next!=""&&opts.next!=undefined)||opts.next){
				        		laypage.next=opts.next;
				        	}
				        	laypage.jump=function(obj, first){
								  try {
					        			$(opts.elem).viewer("destroy");
									} catch (e) {
									}
								  var curr = obj.curr;
									  if(res!=null&&res!=""&&res!=undefined&&b){
										  var txt=opts.success(res);
										  if(txt==null||txt==""||txt==undefined||txt==" "){
											  $(opts.elem).html('');
											  layer.msg("暂无数据");
											  return;
										  }
								          $(opts.elem).html(txt);
								          try {
								        	  $(opts.elem).viewer();
											} catch (e) {
											}
								          b=false;
									  }else{
										    try {
										    		if(data.indexOf("&")>-1&&data.indexOf("page=1")>-1){
														data=data.replace("page=1","page="+curr);
														$p=curr;
													}else if(data.indexOf("&")>-1){
														data=data.replace("page="+$p,"page="+curr);
													}
											} catch (e) {
												data.page=curr;
											}
										//得到了当前页，用于向服务端请求对应数据
									        //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
									        $(opts.elem).ajax({
									        	 url : opts.url,
									        	data : data,
									        	icon : opts.icon
									        },function(res){
									        	var txt=opts.success(res);
									        	if(txt==null||txt==""||txt==undefined||txt==" "){
									        		$(opts.elem).html('');
									        		layer.msg("暂无数据");
													  return;
												  }
									        	$(opts.elem).html(txt);
									        	 try {
										        	  $(opts.elem).viewer();
													} catch (e) {
													}
									        })
									  }
							   }
				        	console.log(laypage);
				        	wlaypage(laypage);
				       });
			}
	}
	
	var odefaluts={
			isAuto : true,
			isLazyimg : true,
			rows : 10,
			skip : true,
			icon : 8,
			skin : "#62a8ea",
			async : false,
			versions : "v1",
			groups : 5
	}
	
	;!function(){
		this.use=use;
	}.call(window[pkg] = window[pkg] || {});

}('Oliver');