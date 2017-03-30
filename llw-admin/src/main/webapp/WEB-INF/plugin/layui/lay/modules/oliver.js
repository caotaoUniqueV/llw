layui.define(function(a) {
	"use strict";
;!function(pkg, undefined){
	
	var jssdk=function(options){
		 $.ajax({
		      url: 'http://res.wx.qq.com/open/js/jweixin-1.0.0.js',
		      dataType: "script",
		      cache: false
		}).done(function() {
			$.ajax({
			      url: "http://www.haoyuanqu.net/wechat/web/core/js/wxm-core.js",
			      dataType: "script",
			      cache: false
			})
			var opts = $.extend({},defaluts,options); //使用jQuery.extend 覆盖插件默认参数
			//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	        var curWwwPath=window.document.location.href;
	        var pathName=window.document.location.pathname;
	        var pos=curWwwPath.indexOf(pathName);
	        //获取主机地址，如： http://localhost:8083
	        var localhostPaht=curWwwPath.substring(0,pos);
			$.ajax({
				url : localhostPaht+'/weixinCore/weixin!obtainSign.action?hostId='+opts.hostId+'&url='+encodeURIComponent(curWwwPath.split('#')[0]),
				type : "GET",
				cache: false,
				dataType : "json",
				success : function (data){
					wx.config({
			    	    debug: false, 
			    	    appId: data.appid, // 必填，公众号的唯一标识
			    	    timestamp:data.timestamp, // 必填，生成签名的时间戳
			    	    nonceStr: data.nonceStr, // 必填，生成签名的随机串
			    	    signature:data.signature,// 必填，签名，见附录1
			    	    jsApiList: ['checkJsApi',
			    	                'onMenuShareTimeline',
			    	                'onMenuShareAppMessage',
			    	                'onMenuShareQQ',
			    	                'onMenuShareWeibo',
			    	                'onMenuShareQZone',
			    	                'hideMenuItems',
			    	                'showMenuItems',
			    	                'hideAllNonBaseMenuItem',
			    	                'showAllNonBaseMenuItem',
			    	                'translateVoice',
			    	                'startRecord',
			    	                'stopRecord',
			    	                'onRecordEnd',
			    	                'playVoice',
			    	                'pauseVoice',
			    	                'stopVoice',
			    	                'uploadVoice',
			    	                'downloadVoice',
			    	                'chooseImage',
			    	                'previewImage',
			    	                'uploadImage',
			    	                'downloadImage',
			    	                'getNetworkType',
			    	                'openLocation',
			    	                'getLocation',
			    	                'hideOptionMenu',
			    	                'showOptionMenu',
			    	                'closeWindow',
			    	                'scanQRCode',
			    	                'chooseWXPay',
			    	                'openProductSpecificView',
			    	                'addCard',
			    	                'chooseCard',
			    	                'openCard']
			    	});
			    	wx.ready(function(){
			    		opts.wx=wx;
			    		if(opts.share!=null&&opts.share!=""&&opts.share!=undefined){
			    			onMenuShareTimeline(opts);
							onMenuShareAppMessage(opts);
							onMenuShareQQ(opts);
							onMenuShareWeibo(opts);
							onMenuShareQZone(opts);
			    		}
			    		if(opts.scan!=null&&opts.scan!=""&&opts.scan!=undefined){
			    			scanQRCode(opts);
			    		}
			    		if(opts.openLocation!=null&&opts.openLocation!=""&&opts.openLocation!=undefined){
			    			openLocation(opts);
			    		}
			    		
			    		if(opts.chooseWXPay!=null&&opts.chooseWXPay!=""&&opts.chooseWXPay!=undefined){
			    			chooseWXPay(opts);
			    		}
			    		if(opts.hideMenuItems!=null&&opts.hideMenuItems!=""&&opts.hideMenuItems!=undefined){
			    			hideMenuItems(opts);
			    		}
			    	});
			    	
			    	wx.error(function(res){
				    	 alert("ERROR:"+JSON.stringify(res));
			    	});
				}
			});
		});
	};
	
	
	var chooseWXPay=function(opts){
		document.querySelector(opts.chooseWXPay.elem).onclick = function () {
			var wx=opts.wx;
			wx.chooseWXPay({
			    timestamp: opts.chooseWXPay.timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
			    nonceStr: opts.chooseWXPay.nonceStr, // 支付签名随机串，不长于 32 位
			    package: opts.chooseWXPay.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
			    signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
			    paySign: opts.chooseWXPay.paySign, // 支付签名
			    success: function (res) {
			        // 支付成功后的回调函数
			    	opts.chooseWXPay.success(res);
			    }
			});
		};
	}
	var hideMenuItems=function(opts){
		var wx=opts.wx;
		wx.hideMenuItems({
		    menuList: opts.hideMenuItems.menuList // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
		});
	}
	var openLocation=function(opts){
		if(opts.openLocation.elem!=null&&opts.openLocation.elem!=""&&opts.openLocation.elem!=undefined){
			document.querySelector(opts.openLocation.elem).onclick = function () {
				location(opts);
			};
		}else{
			location(opts);
		}
	}
	
	var location=function(opts){
		var wx=opts.wx;
		wx.openLocation({
		    latitude: opts.openLocation.latitude, // 纬度，浮点数，范围为90 ~ -90
		    longitude: opts.openLocation.longitude, // 经度，浮点数，范围为180 ~ -180。
		    name: opts.openLocation.name, // 位置名
		    address: opts.openLocation.address, // 地址详情说明
		    scale: opts.openLocation.scale, // 地图缩放级别,整形值,范围从1~28。默认为最大
		    infoUrl: opts.openLocation.infoUrl // 在查看位置界面底部显示的超链接,可点击跳转
		});
	}
	
	var scanQRCode=function(opts){
		var wx=opts.wx;
		document.querySelector(opts.scan.elem).onclick = function () {
			if(opts.scan.needResult==null||opts.scan.needResult!=""&&opts.scan.needResult!=undefined){
				opts.scan.needResult=opts.needResult;
			}
			if(opts.scan.scanType==null||opts.scan.scanType!=""&&opts.scan.scanType!=undefined){
				opts.scan.scanType=opts.scanType;
			}
			wx.scanQRCode({
			    needResult: opts.scan.needResult, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			    scanType: opts.scan.scanType, // 可以指定扫二维码还是一维码，默认二者都有
			    success: function (res) {
			    	opts.scan.success(res);
			    }
			});
		};
	}
	
	var onMenuShareTimeline=function(opts){
		var wx=opts.wx;
		//分享
		wx.onMenuShareTimeline({
		    title: opts.share.title, // 分享标题
		    link: opts.share.link, // 分享链接
		    imgUrl: opts.share.imgUrl, // 分享图标
		    success: function () { 
		    	opts.share.success();
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	opts.share.cancel();
		    }
		});
	}
	
	var onMenuShareAppMessage=function(opts){
		var wx=opts.wx;
		//分享
		wx.onMenuShareAppMessage({
		    title: opts.share.title, // 分享标题
		    link: opts.share.link, // 分享链接
		    imgUrl: opts.share.imgUrl, // 分享图标
		    desc: opts.share.desc, // 分享描述
		    type: opts.share.type, // 分享类型,music、video或link，不填默认为link
		    dataUrl: opts.share.dataUrl, // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		    	opts.share.success();
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	opts.share.cancel();
		    }
		});
	}
	
	var onMenuShareQQ=function(opts){
		var wx=opts.wx;
		//分享
		wx.onMenuShareQQ({
		    title: opts.share.title, // 分享标题
		    link: opts.share.link, // 分享链接
		    imgUrl: opts.share.imgUrl, // 分享图标
		    desc: opts.share.desc, // 分享描述
		    success: function () { 
		    	opts.share.success();
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	opts.share.cancel();
		    }
		});
	}
	
	var onMenuShareWeibo=function(opts){
		var wx=opts.wx;
		//分享
		wx.onMenuShareWeibo({
		    title: opts.share.title, // 分享标题
		    link: opts.share.link, // 分享链接
		    imgUrl: opts.share.imgUrl, // 分享图标
		    desc: opts.share.desc, // 分享描述
		    success: function () { 
		    	opts.share.success();
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	opts.share.cancel();
		    }
		});
	}
	
	var onMenuShareQZone=function(opts){
		var wx=opts.wx;
		//分享
		wx.onMenuShareQZone({
		    title: opts.share.title, // 分享标题
		    link: opts.share.link, // 分享链接
		    imgUrl: opts.share.imgUrl, // 分享图标
		    desc: opts.share.desc, // 分享描述
		    success: function () { 
		    	opts.share.success();
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	opts.share.cancel();
		    }
		});
	}
	var defaluts={
		type : "link",
		dataUrl : '',
		hostId : 476,
		needResult : 0,
		scanType : ["qrCode","barCode"]
		
	}

	;!function(){
	        this.jssdk = jssdk;
	}.call(window[pkg] = window[pkg] || {});

}('WX');
a("jssdk",null);
})
