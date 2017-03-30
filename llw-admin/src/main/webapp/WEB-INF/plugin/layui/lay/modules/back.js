/**
 * 使用 HTML5 的 History 新 API pushState 来曲线监听 Android 设备的返回按钮
 */
layui.define(function(a) {
	"use strict";
	;!function(pkg, undefined){
		var pushHistory=function(uri) {
			if(uri!=null&&uri!=''&&uri!=undefined){
				uri="#";
			}
		    var state = {  
		        title: "title",  
		        url: uri  
		    };  
		    window.history.pushState(state, "title", uri);  
		}  
	
		var listen = function(uri){
			 window.addEventListener("popstate", function(e) {
		        	window.location.href=uri;
		     }, false);  
		}
	
		;!function(){
			pushHistory();
			this.listen = listen;
		}.call(window[pkg] = window[pkg] || {});
	
	}('XBack');
	a("back",null);
})