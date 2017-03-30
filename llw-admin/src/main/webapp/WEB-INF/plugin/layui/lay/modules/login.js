layui.define(function(a) {
	"use strict";
	var _self,_b=true,num=120,password,getcode,arr=[],picCodeKey="",ckId="",isCode=false,bColor,layIndex,
	mobileReg = /^0?(13[0-9]|15[012356789]|18[012356789]|14[57]|17[012356789])[0-9]{8}$/,
	emailReg  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
	postcode=/^[0-9][0-9]{5}$/;
	$.fn.extend({
		/**
		 * 校验
		 */
		check : function(options){
			var opts = $.extend({}, null, options); //使用jQuery.extend 覆盖插件默认参数
			_self=$(this);
			_b=true;
			isCode=opts.code;
			var serialize=$("#"+opts.formId).serialize();
			return analysis(serialize,opts.formId);
		},
		coreCheck : function (options,callback){
			var opts = $.extend({}, defaluts, options); //使用jQuery.extend 覆盖插件默认参数
			_self=$(this);
			if(!opts.noCheck){
				_b=_self.check(options);
			}
			if(_b){
				if(options.icon==undefined){
					options.icon=1;
				}
				$(_self).ajax(options,function(data){
					callback(data);
				});
			}
		},
		/**
		 * 公共验证
		 */
		verify : function (options,callback){
			var opts = $.extend({}, defaluts, options); //使用jQuery.extend 覆盖插件默认参数
			_self=$(this);
			if(!opts.noCheck){
				_b=_self.check(options);
			}
			if(opts.defined!=null&&opts.defined!=""&&opts.defined!=undefined){
				_b=opts.defined();//用户自定义规则
			}
			if(_b){
				if(options.icon==undefined){
					options.icon=1;
				}
				options.success=null;
				$(_self).ajax(options,function(data){
					if(callback!=null&&callback!=""&&callback!=undefined){
    	    			callback(data);
    	    		}
					if(opts.success!=null&&opts.success!=""&&opts.success!=undefined){
    	 				opts.success(data);
    	 			}
				});
			}
		},
		offsets : function(){
			_seft=$(this);
			var u = navigator.userAgent; 
			var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端 
			var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
			$("input").focus(function(){
				if(isAndroid){
					if($(this).offset().top>150){
						$("html,body").animate({scrollTop:_seft.offset().top+150});
					}
				}
			});
		},
		/**
		 * 注册验证码按钮时间
		 */
		registerCode : function (options){
			var opts = $.extend({}, null, options); //使用jQuery.extend 覆盖插件默认参数
			_self=$(this);
			$options=options;
			console.log($options);
			switch (opts.type) {
			case "PC":
				_self.on("click",function(){
					webGetCode(options);//pc
				});
				break;
			default:
				mReg(options);//手机
				break;
			}
		},
		checkImgCode : function(options,code){
//			var opt=options;
//			var _seft=$(this);
//			var va={
//					url : "http://www.haoyuanqu.com/weixin/web/weixinpay2/userInfo!checkImgCode.action?code2="+code,
//					icon : 2,
//					loading : "验证中..."
//			}
//			if(opt.isJsonp){
//				va.dataType="jsonp";
//				va.isJsonp="callback";
//			}
//			va.code=true;
//			va.formId=options.formId;
//			isClean=false;
//			$(this).ajax(va,function(data){
//				if(data.result.msg=="ok"){
//					$('.mask').hide();
//					$(".captchabox").hide();
//					$("input[name='code2']").val('');
//					_seft.coreRegisterCode(opt,code);
//				}else{
//					showToast(data.result.msg);
//					$("input[name='code2']").focus();
//					codeimg.attr("src","http://www.haoyuanqu.com/weixin/login/authImage?id=" + Math.random());
//				}
//			});
			var opt=options;
			var _seft=$(this);
			isClean=false;
//			$('.mask').hide();
//			$(".captchabox").hide();
			$("input[name='check']").val('');
			_seft.coreRegisterCode(opt,code);
		},
		coreRegisterCode : function (options,code){
//			var opts = $.extend({}, null, options); //使用jQuery.extend 覆盖插件默认参数
//			_self=$(this);
//			console.log("coreRegisterCode:");
//			console.log(_self);
//			_b=_self.check(options);
//			if(_b){
//				isClean=false;
//				$(_self).ajax(options,function(data){
//					if(data.result.msg=="ok"){
//						$(_self).duanxin(options);
//						buttonCode=_self;
//						layer.close(layIndex);
//						$("input[name='"+mobileName+"']").attr('readonly',true);
//						showToast("发送成功,请注意查收!");
//						_self.unbind("click",null);
//					}else{
//						layer.close(layIndex);
//						showToast(data.result.msg);
//						codeimg.attr("src","http://www.haoyuanqu.com/weixin/login/authImage?id=" + Math.random());
//					}
//				});
//			}
			var opts = $.extend({}, null, options); //使用jQuery.extend 覆盖插件默认参数
			_self=$(this);
			console.log("coreRegisterCode:");
			console.log(_self);
			_b=_self.check(options);
			if(_b){
				isClean=false;
				options.loading="获取验证码中,请稍等...";
				options.icon=8;
				var uri=options.url;
				if(uri.indexOf("?")>0){
					uri=uri.substring(0,uri.lastIndexOf("?"));
				}
				options.url=uri+"?phone="+$("input[name='"+mobileName+"']").val()+"&imgVer="+code;
				$(_self).ajax(options,function(data){
					if(data.result.success){
						$(_self).duanxin(options);
						buttonCode=_self;
						layer.close(layIndex);
						$("input[name='"+mobileName+"']").attr('readonly',true);
						showToast("发送成功,请注意查收!");
						_self.unbind("click",null);
					}else{
						layer.close(layIndex);
						showToast(data.result.msg);
					}
				});
			}
		},
		/**
		 * 获取图形验证码
		 */
		registerImgCode : function (className){
//			codeimg=$("."+className);
//			_self=$(this);
//			var id=_self;
//			$("."+className).attr("src","http://www.haoyuanqu.com/weixin/login/authImage?id=" + Math.random());
//			_self.on("click",function(){
//				$("."+className).attr("src","http://www.haoyuanqu.com/weixin/login/authImage?id=" + Math.random());
//			})
			ckImg();
		},
		raseImgCode : function(options){
			window._seft=$(this);
			    num=120;
				$("#qr").unbind("click");
				_seft.removeClass("phone-yz-btn");
	    		$("#code2").val('');
	    		$("#code").val('');
	    		_seft.removeClass('wait');
				for (var i = arr.length - 1; i >= 0; i--) {
			             if (typeof arr[i] !== 'undefined') {
			                clearInterval(arr[i]);
			             }
			     }
				$("input[name='"+mobileName+"']").attr('readonly',false);
				_seft.removeAttr("disabled");
				_seft.bind("click",function(){
					$("#qr").unbind("click");
					_b=_self.check(options);
					if(_b){
						layIndex=layer.open({
						    type: 1
						    ,content: txt
						    ,anim: 'up'
						    ,shadeClose : false
						    ,style: 'position:fixed; bottom:0; left:0; width: 100%; height: 200px; padding:10px 0; border:none;'
						    ,success : function(){
						    	var close1=$("#close1"); 
								close1.on("touchstart",function(){
									layer.close(layIndex);
								});
						    }
						  });
						$.fn.registerImgCode('code');
						$("#qr").on("click",function(){
							_b=$("input[name='check']").val();
							if(_b!=null&&_b!=""&&_b!=undefined){
								_seft.checkImgCode(options,_b);
							}else{
								showToast("请输入图形验证码");
							}
						})
					}
				})
		},
		/**
		 * 短信倒计时
		 */
		duanxin : function(options){	
			var opts = $.extend({}, null, options); //使用jQuery.extend 覆盖插件默认参数
			_self=$(this);
			var timer=null;
			console.log("duanxin:");
			console.log(_self);
			_self.addClass("phone-yz-btn");
			$('.phone-yz-btn').unbind("click",$.fn.duanxin);
			if(_self.is("button")){
				$('.phone-yz-btn').html(''+num+'s');
			}else if(_self.is("input")){
				$('.phone-yz-btn').val(''+num+'s');
			}else{
				$('.phone-yz-btn').html(''+num+'s');
			}
			var btColor=$('.phone-yz-btn').css('background-color'); 
			var fontColor=$('.phone-yz-btn').css("color");
			$('.phone-yz-btn').css("background-color","#ddd");
			$('.phone-yz-btn').css("color","#666");
			$('.phone-yz-btn').addClass('wait');
			timer=setInterval(function(){
				num--;
				if(_self.is("button")){
					$('.phone-yz-btn').html(''+num+'s');
				}else if(_self.is("input")){
					$('.phone-yz-btn').val(''+num+'s');
				}else{
					$('.phone-yz-btn').html(''+num+'s');
				}
				$('.phone-yz-btn').css("background-color","#ddd");
				$('.phone-yz-btn').css("color","#666");
				$('.phone-yz-btn').addClass('wait');
				if(num<=0){
					num=120;
		    		$("#code2").val('');
		    		$("#code").val('');
		    		if(_self.is("button")){
		    			$('.phone-yz-btn').html('再次获取');
		    		}else if(_self.is("input")){
		    			$('.phone-yz-btn').val('再次获取');
		    		}else{
		    			$('.phone-yz-btn').html('再次获取');
		    		}
					$("input[name='"+mobileName+"']").attr('readonly',false);
					$('.phone-yz-btn').css("background-color",btColor);
					$('.phone-yz-btn').css("color",fontColor);
					$('.phone-yz-btn').removeClass('wait');
					clearInterval(timer);
					$(".phone-yz-btn").removeAttr("disabled");
					$('.phone-yz-btn').bind("click",function(){
						if(opts.type!="PC"){
							var txt='<div class="captchabox">'+
					    	'<button class="close" id="close1" style="float: right;width: 0.4rem;height: 0.4rem;background: url('+rootpath+'web/images/close-icon.png);background-size: 0.4rem 0.4rem;border: none;margin-right: 0.133333rem;margin-top: -18px;" type="button"></button>'+
					    	'<p style="position: relative;margin-top: .5rem;overflow: hidden;border-bottom: 1px solid #eee;width: 90%;margin-left: 5%;">'+
					    		'<label class="captcha-icon" style="position: absolute;top: 0.373333rem;margin-right: 0.133333rem;left: 0.066667rem;float: left;width: 0.36rem;height: 0.373333rem;background: url('+rootpath+'web/images/captcha-icon.png);background-size: 0.36rem 0.373333rem;"></label>'+
					        	'<input type="text" name="check" placeholder="图形验证码" style="    float: left;width: 2.466667rem;font-size: 0.373333rem; padding: 0.266667rem 0 0.266667rem 0.666667rem;border: none;" />'+
					        	'<img src="" class="codesize code" style="float:left;margin-top: 0.266667rem;width: 3.2rem;height: 0.6rem;border: none;"/>'+
					        	'<button class="refresh" type="button" onclick="ckImg()" style=" float: left;width: 0.56rem;height: 0.453333rem;border: none; margin-top: 0.333333rem;margin-left: 0.133333rem;background: url('+rootpath+'web/images/refresh-icon.png);background-size: 0.56rem 0.426667rem;"></button>'+
					    	'</p>'+
					    	'<button type="button" name="" id="qr" class="confirm" style=" width: 90%;height: 1.0rem;border: none;color: #fff;font-size: 0.4rem;margin: 0.533333rem 0;border-radius: 0.066667rem;background-color: #2fad6c;margin-left: 5%;">确认</button>'+
					    '</div>';
							layIndex=layer.open({
								 type: 1
								 ,content: txt
								 ,anim: 'up'
								 ,shadeClose : false
								 ,style: 'position:fixed; bottom:55%; left:0; width: 80%; height: 120px; padding:10px 0; border:none;margin-left:10%;'
							    ,success : function(){
							    	var close1=$("#close1"); 
									close1.on("touchstart",function(){
										layer.close(layIndex);
									});
							    }
							  });
							$("#qr").unbind("click");
							$.fn.registerImgCode('code');
							$("#qr").on("click",function(){
								_b=$("input[name='check']").val();
								if(_b!=null&&_b!=""&&_b!=undefined){
									console.log(buttonCode);
									$("#"+buttonCode.attr("id")).checkImgCode(options,_b);
								}else{
									showToast("请输入图形验证码");
								}
							})
						}else{
							webGetCode(options);
						}
					})
					}
			},1000);
			arr.push(timer);
		}
    });
	
	window.codeimg=null;
	window.mobileName=null;
	window.buttonCode=null;
	window.isJsonp=false;
	var $options=null;
	window.successToast=function(htmlStr,style,time,callback){
		if(isFunction(style)){
			callback=style;
		}
		var ua = navigator.userAgent;
		var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
		    isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
		    isAndroid = ua.match(/(Android)\s+([\d.]+)/),
		    isMobile = isIphone || isAndroid;
		    if(isMobile) {
		    	layer.open({
				      content: htmlStr
				      ,time: 2
				      ,style: 'position:relative;bottom:60px;'
				      ,skin: 'msg'
				      ,end : function(elem){
				    	    if(callback!=null&&callback!=""&&callback!=undefined){
				    	    	callback(elem);
							}
					   }
				});
		    }else{
		    	layer.msg(htmlStr, {shift:0}, function(){
		    		if(callback!=null&&callback!=""&&callback!=undefined){
		    	    	callback();
					}
		    	});
		    }
	}
	var isFunction=function(fn) {
		   return Object.prototype.toString.call(fn)=== '[object Function]';
    }
	var coreToast=function(str,isCode2,namdId){
		if(!isCode2){
			if(!isCode){
				successToast(str);
				$("#"+namdId).focus();
				return false;
			}
			if(str=="请输入手机号码"){
				successToast(str);
				$("#"+namdId).focus();
				return false;
			}
			return true;
		}else{
			return true;
		}
	};
	var checkEmptyData=function(ckId,namdId,formType){
		if($("#"+ckId).attr("msg")==null||$("#"+ckId).attr("msg")==""){
			return _b=true;
		}
		switch($("#"+ckId).attr("msg"))
		{
			case "mobile":
				mobileName=namdId;
				_b=coreToast("请输入手机号码",false,ckId);
				break;
			case "email":
				_b=coreToast("请输入邮箱",false,ckId);
				break;
			case "card":
				_b=coreToast("请输入卡号",false,ckId);
				break;
			case "money":
				_b=coreToast("请输入金额",false,ckId);
				break;
			case "postcode":
				_b=coreToast("请输入邮政编码",false,ckId);
				break;
			case "phoneCode":
				_b=coreToast("请输入手机验证码",false,ckId);
				break;
			case "password":
				_b=coreToast("请输入密码",false,ckId);
				break;
			case "onpassword":
				_b=coreToast("请输入重复密码",false,ckId);
				break;
			case "code":
				_b=coreToast("请输入图形验证码",false,ckId);
				break;
			case "url":
				_b=coreToast("请输入url",false,ckId);
				break;
			case "ID":
				_b=coreToast("请输入身份证号码",false,ckId);
				break;
			default :
				if($("#"+ckId).attr("display")!='none'){
					_b=coreToast($("#"+ckId).attr("msg"),false,ckId);
				}
				break;
		}
		return _b;
	};
	var checkData=function(data,ckId,nameId,formType){
		if(formType=="checkbox"||formType=="radio"){
			if(!$("#"+ckId).is(':checked')){
				successToast($("#"+ckId).attr("msg"));
				$("#"+ckId).focus();
				_b=false;
			}
			return _b;
		}
		if($("#"+ckId).attr("msg")=="mobile"){
			if(!mobileReg.test(data)||isNaN(data)){
				mobileName=nameId;
				successToast("请输入正确的手机号码");
				$("#"+ckId).focus();
				_b=false;
			}
		}else if($("#"+ckId).attr("msg")=="email"){
			if(!emailReg.test(decodeURIComponent(data))){
				successToast("请输入正确的邮箱");
				$("#"+ckId).focus();
				_b=false;
			}
		}else if($("#"+ckId).attr("msg")=="money"){
			if(isNaN(data)){
				successToast("请输入正确的金额");
				$("#"+ckId).focus();
				_b=false;
			}
		}else if($("#"+ckId).attr("msg")=="card"){
				if(!CheckBankNo(ckId)){
					$("#"+ckId).focus();
					_b=false;
				}
		}else if($("#"+ckId).attr("msg")=="ID"){
			if(!IdentityCodeValid($("#"+ckId).val())){
				$("#"+ckId).focus();
				_b=false;
			}
		}else if($("#"+ckId).attr("msg")=="postcode"){
			if(!postcode.test(data)||isNaN(data)){
				successToast("请输入正确的邮政编码");
				$("#"+ckId).focus();
				_b=false;
			}
		}else if($("#"+ckId).attr("msg")=="phoneCode"){
			if(isNaN(data)){
				successToast("请输入正确的手机验证码");
				$("#"+ckId).focus();
				_b=false;
			}
		}else if($("#"+ckId).attr("msg")=="password"){
			password=$("#"+ckId).val();
		}else if($("#"+ckId).attr("msg")=="onpassword"&&$("#"+ckId).val()!=password){
			successToast("两次密码输入不一致");
			$("#"+ckId).focus();
			_b=false;
		}else if($("#"+ckId).attr("msg")=="url"){
			var str=$("input[name='"+nameId+"']").val();
			//判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
			//下面的代码中应用了转义字符"\"输出一个字符"/"
			var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
			var objExp=new RegExp(Expression);
			if(!objExp.test(str)){
				successToast("非法的url");
				$("#"+ckId).focus();
				_b=false;
			}
		}else{
			var number=$("#"+ckId).attr("number");
			var maxlength=$("#"+ckId).attr("maxLen");//最多输入多少个字符
			var minlength=$("#"+ckId).attr("minLen");//最少输入多少个字符
			if(number!=null&&number!=""&&number!=undefined){
				if(isNaN($("#"+ckId).val())){
					successToast("请输入正确的字符");
					$("#"+ckId).focus();
					_b=false;
				}
			}
			if(maxlength!=null&&maxlength!=""&&maxlength!=undefined){
				if($("#"+ckId).val().length>maxlength){
					successToast("最多输入"+maxlength+"个字符");
					$("#"+ckId).focus();
					_b=false;
				}
			}
			if(minlength!=null&&minlength!=""&&minlength!=undefined){
				if($("#"+ckId).val().length<minlength){
					successToast("最少输入"+minlength+"个字符");
					$("#"+ckId).focus();
					_b=false;
				}
			}
		}
		return _b;
	};
	var analysis=function(serialize,formId){
		if(formId==null||formId==""||formId==undefined){
			successToast("缺少参数formId");
			_b=false;
			return _b;
		}
		var frm=document.getElementById(formId);
//		if(bColor!=null&&bColor!=""&&bColor!=undefined){
//			 $("#"+formId).find("input").css({"border":"1px solid "+bColor});
//			 $("#"+formId).find("select").css({"border":"1px solid "+bColor});
//			 $("#"+formId).find("textarea").css({"border":"1px solid "+bColor});
//		}
		
		if(frm.elements.length){
	        for(var i=0;i<frm.length;i++){//遍历每个表单元素
	            var obj=frm[i];
	            if($(obj).attr("name")==null||$(obj).attr("name")==""){
	            	continue;
	            }
	            bColor=$(obj).css("border-color");
	            if($(obj).attr("name").indexOf(".")>-1){
	            	 var $id=$(obj).attr("name").substring($(obj).attr("name").lastIndexOf(".")+1,$(obj).attr("name").length);
					$("input[name='"+$(obj).attr("name")+"']").attr("id",$id);
					ckId=$id;
				}else{
					$("input[name='"+$(obj).attr("name")+"']").attr("id",$(obj).attr("name"));
					ckId=$(obj).attr("name");
				}
	            if(obj.type=="textarea"||obj.type=="select-one"){
	            	if($(obj).attr("name").indexOf(".")>-1){
	            		var $id=$(obj).attr("name").substring($(obj).attr("name").lastIndexOf(".")+1,$(obj).attr("name").length);
	            		$(obj).attr("id",$id);
	            	}else{
	            		$(obj).attr("id",$(obj).attr("name"));
	            	}
	            }
	            
	            if($(obj).val()==null||$(obj).val()==""||$(obj).val()==undefined){
					if(!checkEmptyData(ckId,$(obj).attr("name"),obj.type)){
						break;
					}
				}else{
					if(!checkData($(obj).val(),ckId,$(obj).attr("name"),obj.type)){
						break;
					}
				}
	        }
	    }
		return _b
	}
    //Description:  银行卡号Luhm校验

    //Luhm校验规则：16位银行卡号（19位通用）:
    
    // 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
    // 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
    // 3.将加法和加上校验位能被 10 整除。
	var luhmCheck=function(bankno,obj){
        var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）
    
        var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
        var newArr=new Array();
        for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
            newArr.push(first15Num.substr(i,1));
        }
        var arrJiShu=new Array();  //奇数位*2的积 <9
        var arrJiShu2=new Array(); //奇数位*2的积 >9
        
        var arrOuShu=new Array();  //偶数位数组
        for(var j=0;j<newArr.length;j++){
            if((j+1)%2==1){//奇数位
                if(parseInt(newArr[j])*2<9)
                arrJiShu.push(parseInt(newArr[j])*2);
                else
                arrJiShu2.push(parseInt(newArr[j])*2);
            }
            else //偶数位
            arrOuShu.push(newArr[j]);
        }
        
        var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
        var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
        for(var h=0;h<arrJiShu2.length;h++){
            jishu_child1.push(parseInt(arrJiShu2[h])%10);
            jishu_child2.push(parseInt(arrJiShu2[h])/10);
        }        
        
        var sumJiShu=0; //奇数位*2 < 9 的数组之和
        var sumOuShu=0; //偶数位数组之和
        var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
        var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
        var sumTotal=0;
        for(var m=0;m<arrJiShu.length;m++){
            sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
        }
        
        for(var n=0;n<arrOuShu.length;n++){
            sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
        }
        
        for(var p=0;p<jishu_child1.length;p++){
            sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
            sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
        }      
        //计算总和
        sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
        
        //计算Luhm值
        var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
        var luhm= 10-k;
        
        if(lastNum==luhm){
        	return true;
        }
        else{
        	showToast("银行卡号必须符合Luhm校验");
        	return false;
        }        
    }
	var CheckBankNo=function(obj) {
		var bankno =$.trim($("input[name='"+obj+"']").val());
		if(isNaN(bankno)) {
			successToast("银行卡号必须全为数字");
		    return false;
		}
		if (bankno.length<16 || bankno.length>19) {
			successToast("银行卡号长度必须在16到19之间");
		    return false;
		}
		//开头6位
		var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
		if (strBin.indexOf(bankno.substring(0, 2))== -1) {
			successToast("银行卡号开头6位不符合规范");
		    return false;
		}
		//Luhm校验（新）   
		if(!luhmCheck(bankno,obj)){
		   return false;
		}else{
			return true;
		}
	}
	
	var webGetCode=function(options){
			_b=_self.check(options);
			if(_b){
				isClean=false;
				options.icon=5;
				$(_self).ajax(options,function(data){
					if(data.msg=="0"){
						$(_self).duanxin(options);
						buttonCode=_self;
						$("input[name='"+mobileName+"']").attr('readonly',true);
						showToast("发送成功,请注意查收!");
						_self.unbind("click",null);
					}else{
						showToast(data.msg);
					}
				});
			}
	}
	
	var mReg=function(options){
		var txt='<div class="captchabox">'+
			    	'<button class="close" id="close1" style="float: right;width: 0.4rem;height: 0.4rem;background: url('+rootpath+'web/images/close-icon.png);background-size: 0.4rem 0.4rem;border: none;margin-right: 0.133333rem;margin-top: -18px;" type="button"></button>'+
			    	'<p style="position: relative;margin-top: .5rem;overflow: hidden;border-bottom: 1px solid #eee;width: 90%;margin-left: 5%;">'+
			    		'<label class="captcha-icon" style="position: absolute;top: 0.373333rem;margin-right: 0.133333rem;left: 0.066667rem;float: left;width: 0.36rem;height: 0.373333rem;background: url('+rootpath+'web/images/captcha-icon.png);background-size: 0.36rem 0.373333rem;"></label>'+
			        	'<input type="text" name="check" placeholder="图形验证码" style="    float: left;width: 2.466667rem;font-size: 0.373333rem; padding: 0.266667rem 0 0.266667rem 0.666667rem;border: none;" />'+
			        	'<img src="" class="codesize code" style="float:left;margin-top: 0.266667rem;width: 3.2rem;height: 0.6rem;border: none;"/>'+
			        	'<button class="refresh" type="button" onclick="ckImg()" style=" float: left;width: 0.56rem;height: 0.453333rem;border: none; margin-top: 0.333333rem;margin-left: 0.133333rem;background: url('+rootpath+'web/images/refresh-icon.png);background-size: 0.56rem 0.426667rem;"></button>'+
			    	'</p>'+
			    	'<button type="button" name="" id="qr" class="confirm" style=" width: 90%;height: 1.0rem;border: none;color: #fff;font-size: 0.4rem;margin: 0.533333rem 0;border-radius: 0.066667rem;background-color: #2fad6c;margin-left: 5%;">确认</button>'+
			    '</div>';
		getcode=_self;
		_self.on("click",function(){
			$("#qr").unbind("click");
			console.log(_self);
			_b=_self.check(options);
			if(_b){
				layIndex=layer.open({
				    type: 1
				    ,content: txt
				    ,anim: 'up'
				    ,shadeClose : false
				    ,style: 'position:fixed; bottom:55%; left:0; width: 80%; height: 120px; padding:10px 0; border:none;margin-left:10%;'
				    ,success : function(){
				    	var close1=$("#close1"); 
						close1.on("touchstart",function(){
							layer.close(layIndex);
						});
				    }
				  });
				$.fn.registerImgCode('code');
				$("#qr").on("click",function(){
					_b=$("input[name='check']").val();
					if(_b!=null&&_b!=""&&_b!=undefined){
						getcode.checkImgCode(options,_b);
					}else{
						successToast("请输入图形验证码");
					}
				})
			}
		})
	};
	
	var defaluts={
			noCheck : false
	}
	
	var ckImg=function(){
//		$.ajax({
//			  type: 'GET',
//			  url: "http://www.haoyuanqu.com/weixin/login/authImage",
//			  dataType: 'text',
//			  success:function(data) { 
//				 $(".code").attr("src",data);
//			     picCodeKey=data.replace("https://api.haoyuanqu.com/code?picCodeKey=","");
//			  }
//		});
		$.ajax({
			  type: 'GET',
			  url: rootpath+"../register/imgNew",
			  success:function(data) {
				  if(data.result.success){
					  $(".code").attr("src",data.result.value);
				  }else{
					  successToast(data.result.value);
				  }
//			     picCodeKey=data.replace("https://api.haoyuanqu.com/code?picCodeKey=","");
			  }
		});
	}
	
	var IdentityCodeValid=function(code){ 
        var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
        var tip = "";
        var pass= true;
        
        if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
        	successToast("身份证号格式错误");
        	return false;
        }
        
       else if(!city[code.substr(0,2)]){
    	   successToast("身份证地址编码错误");
    	   return false;
        }
        else{
            //18位身份证需要验证最后一位校验位
            if(code.length == 18){
                code = code.split('');
                //∑(ai×Wi)(mod 11)
                //加权因子
                var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                //校验位
                var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++)
                {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if(parity[sum % 11] != code[17]){
                	successToast("身份证校验位错误");
                	return false;
                }
            }
        }
        return true;
    }
	
	a("login",null);
})

function ckImg(){
		$.ajax({
			  type: 'GET',
			  url: "http://www.haoyuanqu.com/weixin/login/authImage",
			  dataType: 'text',
			  success:function(data) { 
				 $(".code").attr("src",data);
			     picCodeKey=data.replace("https://api.haoyuanqu.com/code?picCodeKey=","");
			  }
		});
}