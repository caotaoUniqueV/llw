layui.define(function(a) {
	"use strict";
	var _self,$id,btColor,fontColor,index;
	$.fn.extend({
		coreLoad : function (options,callback){
			var opts = $.extend({}, defaluts, options); //使用jQuery.extend 覆盖插件默认参数
            if(opts.formId){
            	opts.data=$("#"+opts.formId).serialize();
            }
            _self=$(this);
            if(opts.icon=="0"){
	    		var doc=document;  
	    	    var link=doc.createElement("link");  
	    	    link.setAttribute("id", "loadinglink");  
	    	    link.setAttribute("rel", "stylesheet");  
	    	    link.setAttribute("type", "text/css");  
	    	    link.setAttribute("href", "http://www.haoyuanqu.com/weixin/web/css/loading.css");  
	    	    var heads = $("head");  
	    	    if(heads.length)  
	    	    	heads.prepend(link);  
	    	    else  
	    	        doc.documentElement.appendChild(link);
	    	    
	    		var _str='<div class="sk-circle">'+
    				        '<div class="sk-circle1 sk-child"></div>'+
    				        '<div class="sk-circle2 sk-child"></div>'+
    				        '<div class="sk-circle3 sk-child"></div>'+
    				        '<div class="sk-circle4 sk-child"></div>'+
    				        '<div class="sk-circle5 sk-child"></div>'+
    				        '<div class="sk-circle6 sk-child"></div>'+
    				        '<div class="sk-circle7 sk-child"></div>'+
    				        '<div class="sk-circle8 sk-child"></div>'+
    				        '<div class="sk-circle9 sk-child"></div>'+
    				        '<div class="sk-circle10 sk-child"></div>'+
    				        '<div class="sk-circle11 sk-child"></div>'+
    				        '<div class="sk-circle12 sk-child"></div>'+
    				      '</div>';
	    		_self.html(_str);
	    	}
            if(callback!=null&&callback!=""&&callback!=undefined){
            	_self.load(opts.url,opts.data,callback);
			}
			if(opts.success!=null&&opts.success!=""&&opts.success!=undefined){
				 _self.load(opts.url,opts.data,opts.success);
			}
		},
		/**
		 * options 参数对象
		 * callback:回调的函数
		 */
		ajax: function (options,callback) {
            var opts = $.extend({}, defaluts, options); //使用jQuery.extend 覆盖插件默认参数
            if(opts.formId){
            	opts.data=$("#"+opts.formId).serialize();
            }
            _self=$(this);
            $id=_self.attr("id");
            var ajaxTimeoutTest=$.ajax({
        	    url: opts.url,
        	  //1秒后超时    
//        	    timeout:8000,
        	    type: (opts.type)?opts.type:"POST", // 默认为POST,你可以根据需要更改
        	    cache: false, // 默认为true,但对于script,jsonp类型为false,可以自行设置
        	    data: opts.data, // 将请求参数放这里.
        	    dataType: opts.dataType, // 指定想要的数据类型,默认为json
        	    jsonp: opts.isJsonp, // 指定回调处理JSONP类型的请求  需要跨域的话传1
        	    statusCode: { // 如果你想处理各状态的错误的话,后台可以自定义错误码
        	        404: function() {
//        	            showToast("该url不存在");
        	        	window.location.href=rootpath+"web/view/404.jsp"
        	        },
        	        500: function() {
        	            showToast("服务器出错了");
        	        }
        	    },
        	    beforeSend:function(){
        	    	if(opts.beforeSend!=null&&opts.beforeSend!=""&&opts.beforeSend!=undefined){
    	 				opts.beforeSend();
    	 				return;
    	 			}
        	    	var _str="";
        	    	opts.loading!=null&&opts.loading!=""&&opts.loading!=undefined?_str=opts.loading:_str=_self.attr("loading");
        	    	if(opts.icon=="1"){//用于提交form的加载...
        	    		if(_self.is("input")){
        	    			_self.attr('disabled',true);
        	    			_self.attr('loading',_self.val());
        	    			btColor=_self.css('background-color'); 
        	    			fontColor=_self.css("color");
        	    			_self.css("background-color","#ddd");
        	    			_self.css("color","#666");
        	    			_self.val(_str);
        	    		}else if(_self.is("button")){
        	    			_self.attr('disabled',true);
        	    			_self.attr('loading',_self.html());
        	    			btColor=_self.css('background-color');
        	    			fontColor=_self.css("color");
        	    			_self.css("background-color","#ddd");
        	    			_self.css("color","#666");
        	    			_self.html(_str);
        	    		}else{
        	    			_self.before('<div id="BgDiv1" class="title" style="position: absolute;display: block;width: 96%;padding: 0px;'+
	    					'margin: 0px -0.5rem;white-space: nowrap;z-index: 1;opacity: 0;cursor: not-allowed;background-color: rgb(0, 0, 0);border-radius: .25rem">&nbsp;</div>');
			    			$('#BgDiv1').css('height',_self.height());
			    			_self.addClass('disabled');
			    			_self.attr('loading',_self.html());
			    			_self.html(_str);
        	    		}
        	    	}else if(opts.icon=="2"||opts.icon=="0"||opts.icon=="5"||opts.icon=="6"||opts.icon=="7"){ //0用于列表加载
        	    		if(opts.icon=="5"){
        	    			_str="获取验证码中,请稍等...";
        	    		}else if(opts.icon=="6"){
        	    			_str="保存中...";
        	    		}else if(opts.icon=="7"){
        	    			_str="删除中...";
        	    		}else{
        	    			if(_str==null||_str==""||_str==undefined){
            	    			_str="加载中...";
            	    		}
        	    		}
        	    		$("body").append('<div id="mask" style="position: absolute;left: 0;top: 0;width: 100%;height: 100%;background: rgba(0,0,0,.4);z-index: 100000000;opacity: 0.2;transition-duration: .4s"></div>');
        	    		var _txt='<div class="DialogDiv" style="z-index: 100000000;position: fixed; top: 50%;left: 50%;-moz-transform: translate(-50%, -50%);-ms-transform: translate(-50%, -50%);-webkit-transform: translate(-50%, -50%);transform: translate(-50%, -50%);">'+
		                            '<div class="U-guodu-box" style="padding:5px 15px;  background:#3c3c3f; filter:alpha(opacity=90); -moz-opacity:0.9; -khtml-opacity: 0.9; opacity: 0.9;  min-heigh:200px; border-radius:10px;">'+
				                        '<div style="color:#fff; line-height:20px; font-size:12px; margin:0px auto; height:100%; padding-top:10%; padding-bottom:10%;">'+
					                        '<table width="100%" cellpadding="0" cellspacing="0" border="0" >'+
					                            '<tr><td  align="center"><img src="http://www.haoyuanqu.com/weixin/web/images/loading.gif"></td></tr>'+
					                            '<tr><td  valign="middle" align="center" style="color: #fff;">'+_str+'</td></tr>'+
					                        '</table>'+
				                        '</div>'+
			                        '</div>'+
			         		     '</div>';
        	    		 $("body").append(_txt);
	        	    	 $("#mask").css({height: $(document).height() });
	        			 $("body").css("overflow","hidden");
        	    	}else if(opts.icon=="3"){
        	    		var doc=document;  
        	    	    var link=doc.createElement("link");  
        	    	    link.setAttribute("id", "loadinglink");  
        	    	    link.setAttribute("rel", "stylesheet");  
        	    	    link.setAttribute("type", "text/css");  
        	    	    link.setAttribute("href", "http://www.haoyuanqu.com/weixin/web/css/loading.css");  
        	    	    var heads = $("head");  
        	    	    if(heads.length)  
        	    	    	heads.prepend(link);  
        	    	    else  
        	    	        doc.documentElement.appendChild(link);
        	    	    
        	    		var _str='<div class="sk-circle">'+
		    				        '<div class="sk-circle1 sk-child"></div>'+
		    				        '<div class="sk-circle2 sk-child"></div>'+
		    				        '<div class="sk-circle3 sk-child"></div>'+
		    				        '<div class="sk-circle4 sk-child"></div>'+
		    				        '<div class="sk-circle5 sk-child"></div>'+
		    				        '<div class="sk-circle6 sk-child"></div>'+
		    				        '<div class="sk-circle7 sk-child"></div>'+
		    				        '<div class="sk-circle8 sk-child"></div>'+
		    				        '<div class="sk-circle9 sk-child"></div>'+
		    				        '<div class="sk-circle10 sk-child"></div>'+
		    				        '<div class="sk-circle11 sk-child"></div>'+
		    				        '<div class="sk-circle12 sk-child"></div>'+
		    				      '</div>';
        	    		_self.html(_str);
        	    	}else if(opts.icon=="8"){
        	    		var ua = navigator.userAgent;
        	    		var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
        	    	    isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
        	    	    isAndroid = ua.match(/(Android)\s+([\d.]+)/),
        	    	    isMobile = isIphone || isAndroid;
        	    	    if(isMobile) {
        	    	    	//loading带文字
            	    		index=top.window.layer.open({
            	    		    type: 2
            	    		    ,shadeClose : false
            	    		    ,content: _str
            	    		  });
        	    	    }else{
        	    	    	index=top.window.layer.load();
        	    	    }
        	    	}else if(opts.icon=="9"){
        	    		var doc=document;  
        	    	    var link=doc.createElement("link");  
        	    	    link.setAttribute("id", "loadinglink");  
        	    	    link.setAttribute("rel", "stylesheet");  
        	    	    link.setAttribute("type", "text/css");  
        	    	    link.setAttribute("href", "http://hygw.ngrok.4kb.cn/wechat/web/css/loaders.css");  
        	    	    var heads = $("head");  
        	    	    if(heads.length)  
        	    	    	heads.prepend(link);  
        	    	    else  
        	    	        doc.documentElement.appendChild(link);
        	    		var str='<main id="mainMask">'+
									 '<div class="loader">'+
									      '<div class="loader-inner line-scale-pulse-out">'+
									          '<div></div>'+
									          '<div></div>'+
									          '<div></div>'+
									          '<div></div>'+
									          '<div></div>'+
									      '</div>'+
									 '</div>'+
								'</main>';
        	    		_self.html(str);
        	    		document.addEventListener('DOMContentLoaded', function () {
        	    		    document.querySelector('main').className += 'loaded';
        	    		});
        	    	}
        	    },
        	    success:function(data){
        	    	try
        	    	{
        	    		hideLoading(opts.icon,opts.formId,$id);
        	    		if(layer!=null&&layer!=""&&layer!=undefined){
        	    			top.window.layer.close(index);
        	    		}
        	    		if(callback!=null&&callback!=""&&callback!=undefined){
        	    			callback(data);
        	    		}
        	 			if(opts.success!=null&&opts.success!=""&&opts.success!=undefined){
        	 				opts.success(data);
        	 			}
            	    	$(".sk-circle").remove();
        	    	}
        	    	catch(err)
        	    	{
        	    		hideLoading(opts.icon,opts.formId,$id);
        	    		if(callback!=null&&callback!=""&&callback!=undefined){
        	    			callback(data);
        	    		}
        	    		if(opts.success!=null&&opts.success!=""&&opts.success!=undefined){
        	 				opts.success(data);
        	 			}
            	    	$(".sk-circle").remove();
        	    	}
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                	if(opts.error!=null&&opts.error!=""&&opts.error!=undefined){
    	 				opts.error(XMLHttpRequest, textStatus, errorThrown);
    	 				return;
    	 			}
                	if(layer!=null&&layer!=""&&layer!=undefined){
    	    			top.window.layer.close(index);
    	    		}
                	 if(textStatus=='timeout'){//超时,status还有success,error等值的情况
                	      ajaxTimeoutTest.abort();
                	      showToast("连接超时");
                	      hideLoading(opts.icon,null,$id);
                	 }else{
                		 if(XMLHttpRequest.status!="404"&&XMLHttpRequest.status!="500"){
                			 if(XMLHttpRequest.status=="0"){
                				 showToast("该请求未初始化");
                			 }else if(XMLHttpRequest.status=="1"){
                				 showToast("正在发送请求");
                			 }else if(XMLHttpRequest.readyState==4){
                				 showToast(XMLHttpRequest.responseText,null,function(){
                					 location.href=location.href;
                				 });
                			 }
                		 }
                		 hideLoading(opts.icon,opts.formId,$id);
                	 }
                	 $(".sk-circle").remove();
                },
                complete : function(xhr,textStatus){
                	//从http头信息取出 在filter定义的sessionstatus，判断是否是 timeout
                    if(xhr.getResponseHeader("sessionstatus")=="invalid"){ 
                    	var url=encodeURIComponent("http://www.haoyuanqu.com/weixin/web/weixinpay2/interface!oatuhCode.action?url="
		    				 	+encodeURIComponent(rootpath+"weixin!oatuh2User.action?url="+encodeURIComponent(xhr.getResponseHeader("path"))));
                    	layer.open({
                		    content: '当前登录信息已失效!'
                		    ,btn: '我知道了'
                		    ,end:function(){
                		    	var uri="https://open.weixin.qq.com/connect/oauth2/authorize?" +
			 					"appid=wx6a5fa08b35c7443d&" +
			 					"redirect_uri="+url+"&" +
			 					"response_type=code&" +
			 					"scope=snsapi_base&" +
			 					"state=123wechat_redirect";
                		    	window.location.href=uri;
                		    }
                		});
                    }
                }
        	});
        },
        rootPath : function(){
        	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
            var curWwwPath=window.document.location.href;
            //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
            var pathName=window.document.location.pathname;
            var pos=curWwwPath.indexOf(pathName);
            //获取主机地址，如： http://localhost:8083
            var localhostPaht=curWwwPath.substring(0,pos);
            //获取带"/"的项目名，如：/uimcardprj
            var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
            return(localhostPaht+projectName+"/")
        },
        getDistance : function( lat1,  lng1,  lat2,  lng2){//获取两地的距离
			  var radLat1 = lat1 * Math.PI / 180.0;
		      var radLat2 = lat2 * Math.PI / 180.0;
		      var a = radLat1 - radLat2;
		      var  b = lng2 * Math.PI / 180.0 - lng1 * Math.PI / 180.0;
		      var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
		      Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		      s = s *6378.137 ;// EARTH_RADIUS;
		      s = Math.round(s * 10000) / 10000;
		      return s;
		},
		select : function(options,callback){
			var opts = $.extend({}, defaluts, options); //使用jQuery.extend 覆盖插件默认参数
			_self=$(this);
			var $id=_self.attr("id");
			var str="";
			if($("input[name='"+$id+"va1Id']").length==0){
				str= '<input type="hidden" id="'+$id+'va1Id" name="'+$id+'va1Id" value="">'+
		        '<input type="hidden" id="'+$id+'va2Id" name="'+$id+'va2Id" value="">'+
		        '<input type="hidden" id="'+$id+'va3Id" name="'+$id+'va3Id" value="">';
			}
			var txt='<div class="mask js_mask animated" id="'+$id+'js_mask" style="display:none;"></div>'+
				    '<div class="from-choose animated " id="'+$id+'f" optionor="1" style="display:none;">'+
				        '<h1>'+opts.title+'<span class="close" id="'+$id+'close2"></span></h1>'+
				        '<input type="hidden" id="'+$id+'va1" name="'+$id+'va1" value="">'+
				        '<input type="hidden" id="'+$id+'va2" name="'+$id+'va2" value="">'+
				        '<input type="hidden" id="'+$id+'va3" name="'+$id+'va3" value="">'+
				        str+
				        '<section class="from-choose-warp" id="'+$id+'from-choose-warp">'+
				        	'<ul class="'+$id+'choose1">'+
				        	'</ul>'+
				        	'<ul class="'+$id+'choose2">'+
				        	'</ul>'+
				        	'<ul class="'+$id+'choose3">'+
				        	'</ul>'+
				        '</section>'+
					'</div>';
			$("form").prepend(txt);
			_self.on("click",function(){
		 		$('#'+$id+'f>.from-choose-warp').removeClass('from-choose-warp-left2 from-choose-warp-left3');
		 		$("#"+$id+"f").removeClass("fadeOutDown").addClass("bounceInUp").css("display","block");
		 		$("#"+$id+"js_mask").css("display","block");
		 		if(opts.isAddress){
					coreSelect(opts.url,''+$id+'choose1',_self,callback,'',$id);
				}else{
					coreSelect(opts.url,''+$id+'choose1',_self,callback,"close",$id);
				}
				$("#"+$id+"close2").on("touchstart",function(){
			 		$("#"+$id+"f").removeClass("bounceInUp").addClass("fadeOutDown");
			 		$("#"+$id+"js_mask").css("display","none");
			 	});
		 	});
		},
		authorize : function(options){
			var opts = $.extend({}, defaluts, options);
			_self=$(this);
			var b=true;
			if(opts.openid!=null&&opts.openid!=""&&opts.openid!=undefined){
				b=false;
				var openid=opts.openid;
				if(openid==null||openid==""||openid==undefined){
					_self.hide();
					b=true;
				}else{
					_self.show();
				}
			}
			if(b){
				var url=encodeURIComponent("http://www.haoyuanqu.com/weixin/web/weixinpay2/interface!oatuh.action?url="+encodeURIComponent(rootparh+"weixin!oatuh2User.action?url="+encodeURIComponent(opts.url)));
				window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6a5fa08b35c7443d&redirect_uri="+url+"&response_type=code&scope="+opts.scope+"&state="+opts.state+"#wechat_redirect";
			}
		},
		authorizeCore : function(options){
			var opts = $.extend({}, defaluts, options);
			_self=$(this);
			var b=true;
			if(opts.openid!=null&&opts.openid!=""&&opts.openid!=undefined){
				b=false;
				var openid=opts.openid;
				if(openid==null||openid==""||openid==undefined){
					_self.hide();
					b=true;
				}else{
					_self.show();
				}
			}
			if(b){
				var url=encodeURIComponent(rootpath+"weixin!oatuh.action?url="+encodeURIComponent(opts.url));
				window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6a5fa08b35c7443d&redirect_uri="+url+"&response_type=code&scope="+opts.scope+"&state="+opts.state+"&component_appid="+opts.component_appid+"#wechat_redirect";
			}
		},
		stopPropagation : function(){
			document.body.ontouchmove = function (e) {
			      e.stopPropagation();
			};
		},
		preventDefault : function(id){
			try {
				document.body.ontouchmove = function (e) {
				      e.preventDefault();
				};
				var myScroll = new IScroll(id,{
	            	click : true,
	            	hideScrollbar :true
	            });
			} catch (e) {
			}
		}
    });
	
	var startX = 0, startY = 0;
	//touchstart事件  
	function touchSatrtFunc(evt) {
	    try
	    {
	        //evt.preventDefault(); //阻止触摸时浏览器的缩放、滚动条滚动等  

	        var touch = evt.touches[0]; //获取第一个触点  
	        var x = Number(touch.pageX); //页面触点X坐标  
	        var y = Number(touch.pageY); //页面触点Y坐标  
	        //记录触点初始位置  
	        startX = x;
	        startY = y;

	    } catch (e) {
	        alert('touchSatrtFunc：' + e.message);
	    }
	}
	
	function coreSelect(url,id,obj,callback,close,$id){
		$("."+id).ajax({
			url : url,
			icon : 8
		},function(data){
			var res=callback(data);
			$("."+id).html(res);
			$('#'+$id+'f li').on("click",function(){
				if($(this).parent('ul').index()==0){
		 			var val1=$(this).children('a').html();
		 			var pId=$(this).children('a').attr("id");
		 			$('#'+$id+'va1').attr({value:val1});
		 			$('#'+$id+'va1Id').attr({value:pId});
		 			if(close=="close"){
		 				$("#"+$id+"f").removeClass("bounceInUp").addClass("fadeOutDown");
			       		$("#"+$id+"js_mask").css("display","none");
			       		$("#"+$id).val($("#"+$id+"va1").val());
		 			}else{
		 				$('#'+$id+"f").attr({optionor:2});
			 			$('#'+$id+'from-choose-warp').addClass('from-choose-warp-left2');
			 			coreSelect(url.substring(0,url.lastIndexOf("?"))+"?state=1&rid="+pId,$id+'choose2',obj,callback,'',$id);
		 			}
		 		}
		 		if($(this).parent('ul').index()==1){
		 			var val2=$(this).children('a').html();
		 			var cId=$(this).children('a').attr("id");
		 			$('#'+$id+'va2').attr({value:val2});
		 			$('#'+$id+'va2Id').attr({value:cId});
		 			$('#'+$id+"f").attr({optionor:3});
		 			$('#'+$id+'from-choose-warp').addClass('from-choose-warp-left3');
		 			coreSelect(url.substring(0,url.lastIndexOf("?"))+"?state=1&rid="+cId,$id+'choose3',obj,callback,'',$id);
		 		}
		 		if($(this).parent('ul').index()==2){
		 			var val3=$(this).children('a').html();
		 			var aId=$(this).children('a').attr("id");
		 			$('#'+$id+'va3').attr({value:val3});
		 			$('#'+$id+'va3Id').attr({value:aId});
		 			$("#"+$id+"f").removeClass("bounceInUp").addClass("fadeOutDown");
		       		$("#"+$id+"js_mask").css("display","none");
		       		$("#"+$id).val($("#"+$id+"va1").val()+" "+$("#"+$id+"va2").val()+" "+$("#"+$id+"va3").val());
		 		}
			});
		})
	}
	
	window.rootpath=$.fn.rootPath();
	window.distances=0;
	window.me=null;
	window.showToast=function(htmlStr,time,callback){
		var ua = navigator.userAgent;
		var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
	    isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
	    isAndroid = ua.match(/(Android)\s+([\d.]+)/),
	    isMobile = isIphone || isAndroid;
		var $time=2;
		var $time2=2000;
		if(time!=null&&time!=""&&time!=undefined&&typeof(time)!="function"){ 
			$time=time;
			$time2=time;
		}
	    if(isMobile) {
	    	layer.open({
			      content: htmlStr
			      ,time: $time
			      ,skin: 'msg'
			      ,shade : true
			      ,end : function(elem){
			    	  if(callback!=null&&callback!=""&&callback!=undefined){
							callback(elem);
						}
			      }
			});
	    }else{
	    	layer.msg(htmlStr,{shift:0,time:$time2}, function(){
	    		if(callback!=null&&callback!=""&&callback!=undefined){
	    	    	callback();
				}
	    	});
	    }
	}

	window.getBLen = function(str) {
    	  if (str == null) return 0;
    	  if (typeof str != "string"){
    	    str += "";
    	  }
    	  return str.replace(/[^\x00-\xff]/g,"01").length;
    	}
    
    window.sub=function(str,n){ 
    	var r=/[^\x00-\xff]/g; 
    	if(str.replace(r,"mm").length<=n){return str;} 
    	var m=Math.floor(n/2); 
    	for(var i=m;i<str.length;i++){ 
    	if(str.substr(0,i).replace(r,"mm").length>=n){ 
    	return str.substr(0,i)+"..."; 
    	} 
    	} 
    	return str; 
    } 
	window.isClean=true;
	window.issize=0;
	window.typeIndex=0;
	window.tabIndex=0;
	window.showImg=function( url,id) {
        var frameid = 'frameimg' + Math.random();
        window.img = '<img width=\'100%\' id="img" src=\''+url+'?'+Math.random()+'\' /><script>window.onload = function() { parent.document.getElementById(\''+frameid+'\').height = 145+\'px\'; }<'+'/script>';
        $(id).html('<iframe id="'+frameid+'" src="javascript:parent.img;" frameBorder="0" scrolling="no" width="100%"></iframe>');
	}
	var defaluts={
			data : null,
			icon : -1, //默认不显示 是否显示遮罩层
			formId : null,
			dataType : "json",
			isJsonp : null,//是否进行跨域,
			isAddress : true,
			scope : "snsapi_base",
			appid : "wx6a5fa08b35c7443d",
			component_appid : "wx6906a6c506d7ce35"
			
	}
	
	function hideLoading(icon,formId,id){
		if(icon=="1"){
			var obj=_self;
			var _str=obj.attr("loading");
			if(obj.is("input")){
				obj.attr('disabled',false);
	    		obj.attr('loading',_self.val());
	    		obj.css("background-color","#ddd");
	    		obj.css("color","#666");
	    		obj.css("background-color",btColor);
	    		obj.css("color",fontColor);
	    		obj.val(_str);
	    	}else if(obj.is("button")){
	    		obj.attr('disabled',false);
	    		obj.attr('loading',_self.html());
	    		obj.css("background-color",btColor);
	    		obj.css("color",fontColor);
	    		obj.html(_str);
	    	}else{
	    		obj.removeClass('disabled');
				obj.attr('loading',obj.html());
				obj.html(_str);
	    	}
		}
		if(formId&&isClean){
	    	document.getElementById(formId).reset();
	    }
		 $('#BgDiv1').remove();
		 $(".DialogDiv").remove();
		 $("#loadinglink").remove();
 		 $('#mask').remove();
	}
	a("ajax",null);
})