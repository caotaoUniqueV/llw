layui.define(function(a) {
	"use strict";
	var ua = navigator.userAgent;
	var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
	    isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
	    isAndroid = ua.match(/(Android)\s+([\d.]+)/),
	    isMobile = isIphone || isAndroid;
	var _self,$index,$text,
	// 添加的文件数量
            fileCount = 0,

            // 添加的文件总大小
            fileSize = 0,

            // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,

            // 缩略图大小
            thumbnailWidth = 110 * ratio,
            thumbnailHeight = 110 * ratio,

            // 可能有pedding, ready, uploading, confirm, done.
            state = 'pedding',

            // 所有文件的进度信息，key为file id
            percentages = {},
            // 判断浏览器是否支持图片的base64
            isSupportBase64 = ( function() {
                var data = new Image();
                var support = true;
                data.onload = data.onerror = function() {
                    if( this.width != 1 || this.height != 1 ) {
                        support = false;
                    }
                }
                data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                return support;
            } )(),

            // 检测是否已经安装flash，检测flash的版本
            flashVersion = ( function() {
                var version;

                try {
                    version = navigator.plugins[ 'Shockwave Flash' ];
                    version = version.description;
                } catch ( ex ) {
                    try {
                        version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                .GetVariable('$version');
                    } catch ( ex2 ) {
                        version = '0.0';
                    }
                }
                version = version.match( /\d+/g );
                return parseFloat( version[ 0 ] + '.' + version[ 1 ], 10 );
            } )(),

            supportTransition = (function(){
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                s = null;
                return r;
            })();
	
	$.fn.extend({
		/**
		 * 公共的上传插件
		 * @param options
		 * @param callbackRes
		 * @param callback
		 */
		upload : function(options,callbackRes,callback){
			var opts = $.extend({}, defaluts, options); //使用jQuery.extend 覆盖插件默认参数
			_self=$(this);
			 if(isMobile) {
			    	if(opts.multiple){
			    		opts.fileNumLimit =9;
			    		uploadPc(opts,callback);
			    	}else{
			    		uploadMobile(opts,callback);
			    	}
			    }else{
			    	if(opts.uploadType=="img"){
			    		uploadMobile(opts,callbackRes,callback);
			    	}else{
			    		uploadPc(opts,callback);
			    	}
			    }
		}
	});
	window.uploader=null;
	var directory={
		images:"images",
		attachments:"attachments"
	};
	var defaluts={
			multiple : false
	};
	//pc端上传
	var uploadPc=function(opts,callback){
		 var js = document.scripts || document.getElementsByTagName("script");
	   	 var corePath;
	   	 for (var i = js.length; i > 0; i--) {
	   	      if (js[i - 1].src.indexOf("oliverui.js") > -1) {
	   	    	corePath = js[i - 1].src.substring(0, js[i - 1].src.lastIndexOf("/") + 1);
	   	      }
	   	 }
	   	 var num=300;
	    var progress= '<div class="progress">'+
	        '<span class="text">0%</span>'+
	        '<span class="percentage"></span></div>'
	        if(isMobile){
				progress="";
				num=9;
			}
		var con='<div id="wrapper">'+
		        '<div id="container">'+
		            '<div id="uploader">'+
		                '<div class="queueList">'+
		                    '<div id="dndArea" class="placeholder">'+
		                        '<div id="filePicker"></div>'+
		                        '<p>或将照片拖到这里，单次最多可选'+num+'张</p>'+
		                    '</div>'+
		                '</div>'+
		                '<div class="statusBar" style="display:none;">'+
		                progress+
		                  '<div class="info"></div>'+
		                    '<div class="btns">'+
		                        '<div id="filePicker2"></div><div class="uploadBtn">开始上传</div>'+
		                    '</div>'+
		                '</div>'+
		            '</div>'+
		        '</div>'+
		    '</div>';
		
		var head= document.getElementsByTagName('head')[0]; 
		var link = document.createElement("link");
	    link.type = "text/css";
	    link.rel = "stylesheet";
	    link.href = corePath+"webuploader/webuploader.css";
	    link.id="uploadLink";
	    
	    var link2 = document.createElement("link");
	    if(isMobile){
	    	link2.type = "text/css";
		    link2.rel = "stylesheet";
		    link2.href = corePath+"webuploader/style2.css";
		    link2.id="uploadLink";
	    }else{
	    	link2.type = "text/css";
		    link2.rel = "stylesheet";
		    link2.href = corePath+"webuploader/style.css";
		    link2.id="uploadLink";
	    }
	    
	    var obj2 = document.getElementById("uploadLink");
	    var layui= document.getElementById("layui");
	    if(obj2==null){
	    	 head.insertBefore(link,layui);
	    	 head.insertBefore(link2,layui);
		}
		
		$.ajax({
		      url: corePath+"webuploader/webuploader.js",
		      dataType: "script",
		      cache: false
		}).done(function() {
			_self.on("click",function(){
				if(isMobile){
					layer.open({
					    type: 1
					    ,content: con
					    ,anim: 'up'
//					    ,shadeClose : false
					    ,style: 'position:fixed; bottom:0; left:0; width: 100%;border:none;'
			        });
				}else{
					layer.open({
			    		  type: 1,
						  title: "上传",
						  area: ['800px', '520px'], //宽高
						  content: con
			    	});
				}
		    	fileCount=0;
				var $wrap = $('#uploader'),
		  // 图片容器
	            $queue = $( '<ul class="filelist"></ul>' )
	                .appendTo( $wrap.find( '.queueList' ) ),

	            // 状态栏，包括进度和控制按钮
	            $statusBar = $wrap.find( '.statusBar' ),

	            // 上传按钮
	            $upload = $wrap.find( '.uploadBtn' ),

	            // 没选择文件之前的内容。
	            $placeHolder = $wrap.find( '.placeholder' );

	            window.$progress = $statusBar.find( '.progress' ).hide();
	            // 文件总体选择信息。
	            window.$info = $statusBar.find( '.info' );
				
				 if ( !WebUploader.Uploader.support('flash') && WebUploader.browser.ie ) {
			            // flash 安装了但是版本过低。
			            if (flashVersion) {
			                (function(container) {
			                    window['expressinstallcallback'] = function( state ) {
			                        switch(state) {
			                            case 'Download.Cancelled':
			                                alert('您取消了更新！')
			                                break;

			                            case 'Download.Failed':
			                                alert('安装失败')
			                                break;

			                            default:
			                                alert('安装已成功，请刷新！');
			                                break;
			                        }
			                        delete window['expressinstallcallback'];
			                    };

			                    var swf =corePath+'webuploader/expressInstall.swf';
			                    // insert flash object
			                    var html = '<object type="application/' +
			                            'x-shockwave-flash" data="' +  swf + '" ';

			                    if (WebUploader.browser.ie) {
			                        html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
			                    }

			                    html += 'width="100%" height="100%" style="outline:0">'  +
			                        '<param name="movie" value="' + swf + '" />' +
			                        '<param name="wmode" value="transparent" />' +
			                        '<param name="allowscriptaccess" value="always" />' +
			                    '</object>';
			                    container.html(html);
			                })($wrap);
			            // 压根就没有安转。
			            } else {
			                $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
			            }
			            return;
			        } else if (!WebUploader.Uploader.support()) {
			            alert( 'Web Uploader 不支持您的浏览器！');
			            return;
			        }
				 
				 opts.swf=corePath+"webuploader/Uploader.swf";
				 opts.pick={
					id : '#filePicker',
					label: '点击选择图片'
				 };
				 opts.formData={
						 uid:123
				 };
				 opts.dnd='#dndArea';
				 opts.paste='#uploader';
				 opts.chunked=false;
				 opts.chunkSize=512 * 1024;
				 opts.accept={
						 title: 'Images',
		                 extensions: 'gif,jpg,jpeg,bmp,png',
		                 mimeTypes: 'image/*'
				 };
				 opts.disableGlobalDnd=true;
				 if(opts.fileNumLimit==null||opts.fileNumLimit==""||opts.fileNumLimit==undefined){
					 opts.fileNumLimit=300;
					 opts.fileSizeLimit=200 * 1024 * 1024;    // 200 M
					 opts.fileSingleSizeLimit=50 * 1024 * 1024;    // 50 M
				 }else{
					 opts.fileSizeLimit=20 * 1024 * 1024;    // 200 M
					 opts.fileSingleSizeLimit=2 * 1024 * 1024;    // 50 M
				 }
				 opts.duplicate=true;
				 uploader = WebUploader.create(opts);
				
				//上传成功
			    uploader.on( 'uploadSuccess', function( file,response) {
			    	try
	    	    	{
//			    		alert(response.result.value.path);
			    		uploadSuccess(response);
	    	    	}
	    	    	catch(err)
	    	    	{
	    	    	}
			    	
			    });
				
				 // 拖拽时不接受 js, txt 文件。
		        uploader.on('dndAccept', function( items ) {
		            var denied = false,
		                len = items.length,
		                i = 0,
		                // 修改js类型
		                unAllowed = 'text/plain;application/javascript ';
		            for ( ; i < len; i++ ) {
		                // 如果在列表里面
		                if ( ~unAllowed.indexOf( items[ i ].type ) ) {
		                    denied = true;
		                    break;
		                }
		            }
		            return !denied;
		        });
		        
		        // 添加“添加文件”的按钮，
		        uploader.addButton({
		            id: '#filePicker2',
		            label: '继续添加'
		        });
		        
		        uploader.on('ready', function() {
		            window.uploader = uploader;
		        });
		        
		        uploader.onUploadProgress = function( file, percentage ) {
		            var $li = $('#'+file.id),
		                $percent = $li.find('.progress span');

		            $percent.css( 'width', percentage * 100 + '%' );
		            percentages[ file.id ][ 1 ] = percentage;
		            updateTotalProgress($progress,$info);
		        };

		        uploader.onFileQueued = function( file ) {
		            fileCount++;
		            fileSize += file.size;

		            if ( fileCount === 1 ) {
		                $placeHolder.addClass( 'element-invisible' );
		                $statusBar.show();
		            }

		            addFile( file,$queue);
		            setState( 'ready',$upload,$placeHolder,$queue,$statusBar,$progress,$info);
		            updateTotalProgress($progress,$info);
		        };

		        uploader.onFileDequeued = function( file ) {
		            fileCount--;
		            fileSize -= file.size;

		            if ( !fileCount ) {
		                setState( 'pedding',$upload,$placeHolder,$queue,$statusBar,$progress,$info);
		            }

		            removeFile( file );
		            updateTotalProgress($progress,$info);

		        };

		        uploader.on( 'all', function(type) {
		            var stats;
		            switch( type ) {
		                case 'uploadFinished':
		                    setState( 'confirm',$upload,$placeHolder,$queue,$statusBar,$progress,$info);
		                    break;

		                case 'startUpload':
		                    setState( 'uploading',$upload,$placeHolder,$queue,$statusBar,$progress,$info);
		                    break;

		                case 'stopUpload':
		                    setState( 'paused',$upload,$placeHolder,$queue,$statusBar,$progress,$info);
		                    break;

		            }
		        });
		        
		        uploader.on( 'error', function(type) {
			    	 if(type=="F_EXCEED_SIZE"){
					       if(!isMobile){
					        	layer.msg("文件大小超过限制");
					        }else{
					        	var index=layer.open({
								      content: '文件大小超过限制!'
								      ,time: 2
								      ,skin: 'msg'
								});
					        }
			         }else if(type=="Q_EXCEED_SIZE_LIMIT"){
			        	 if(!isMobile){
					        	layer.msg("总文件大小超过限制");
					        }else{
					        	var index=layer.open({
								      content: '总文件大小超过限制!'
								      ,time: 2
								      ,skin: 'msg'
								});
					        }
			         }else if(type=="Q_EXCEED_NUM_LIMIT"){
					       if(!isMobile){
					        	layer.msg("上传文件数量超过限制");
					        }else{
					        	console.log(type);
					        	var index=layer.open({
								      content: '上传文件数量超过限制!'
								      ,time: 2
								      ,skin: 'msg'
								});
					        }
			         }
			    });

//		        uploader.onError = function( code ) {
//		        	if(!isMobile){
//			        	layer.msg( 'Eroor: ' + code);
//			        }else{
//			        	var index=layer.open({
//						      content:  'Eroor: ' + code
//						      ,time: 2
//						      ,skin: 'msg'
//						});
//			        }
//		        	
//		        };

		        $upload.on('click', function() {
		            if ( $(this).hasClass( 'disabled' ) ) {
		                return false;
		            }

		            if ( state === 'ready' ) {
		                uploader.upload();
		            } else if ( state === 'paused' ) {
		                uploader.upload();
		            } else if ( state === 'uploading' ) {
		                uploader.stop();
		            }
		        });

		        $info.on( 'click', '.retry', function() {
		            uploader.retry();
		        } );

		        $info.on( 'click', '.ignore', function() {
		        	alert( 'todo' );
		        } );

		        $upload.addClass( 'state-' + state );
		        updateTotalProgress($progress,$info);
		    });
		});
	};
	
	//手机上传
	var uploadMobile=function(opts,callbackRes,callback){
		var js = document.scripts || document.getElementsByTagName("script");
	   	 var corePath;
	   	 for (var i = js.length; i > 0; i--) {
	   	      if (js[i - 1].src.indexOf("oliverui.js") > -1) {
	   	    	corePath = js[i - 1].src.substring(0, js[i - 1].src.lastIndexOf("/") + 1);
	   	      }
	   	 }
	   	 
		var head= document.getElementsByTagName('head')[0]; 
		 var link = document.createElement("link");
	     link.type = "text/css";
	     link.rel = "stylesheet";
	     link.href = corePath+"webuploader/webuploader.css";
	     link.id="uploadLink";
	     var obj2 = document.getElementById("uploadLink");
	     if(obj2==null){
	    	 head.appendChild(link);
		 }
		
		$.ajax({
		      url: corePath+"webuploader/webuploader.html5only.min.js",
		      dataType: "script",
		      cache: false
		}).done(function() {
			opts.swf=corePath+"webuploader/Uploader.swf";
			opts.pick={
					multiple : opts.multiple,
					id : "#"+_self.attr("id")
			};
			opts.auto=true;
			var uploader = WebUploader.create(opts);
			// 当有文件添加进来的时候
		    uploader.on( 'fileQueued', function( file ) {
		    	if(callback){
		    		callback("fileQueued",file);
		    		return;
		    	}
		    	$text='<p id="' + file.id + '">添加文件成功,等待上传</p>';
		    	if(opts.uploadType=="img"){
		    		layer.msg($text, {
		    			  icon: 16
		    			  ,shade: 0.01
		    		});
		    	}else{
		    		$index=layer.open({
			            type: 2
			            ,content: $text
			         });
		    	}
		    });

		    // 文件上传过程中创建进度条实时显示。
		    uploader.on( 'uploadProgress', function( file, percentage ) {
		    	if(callback){
		    		callback("uploadProgress",file,percentage);
		    		return;
		    	}
		        var $li = $( '#'+file.id );
		        $text="上传中"+(percentage * 100).toFixed(0) + '%';
		        $li.html($text);
		        
		    });

		    //上传成功
		    uploader.on( 'uploadSuccess', function( file,response) {
	    			if(callback){
			    		callback("uploadSuccess",file,response);
			    		return;
			    	}
			        $( '#'+file.id ).find('p.state').text('已上传');
			        layer.close($index);
			        if(opts.uploadType=="img"){
			        	layer.msg("上传成功");
			        }else{
			        	var index=layer.open({
						      content: '上传成功!'
						      ,time: 2
						      ,skin: 'msg'
						});
			        }
			    	uploader.reset();
			    	callbackRes(response);
		    });

		    //上传失败
		    uploader.on( 'uploadError', function( file ) {
		    	if(callback){
		    		callback("uploadError",file);
		    		return;
		    	}
		        $( '#'+file.id ).find('p.state').text('上传出错');
		        layer.close($index);
		        if(opts.uploadType=="img"){
		        	layer.msg("上传出错1");
		        }else{
		        	layer.open({
					      content: '上传出错!'
					      ,time: 2
					      ,skin: 'msg'
					});
		        }
		    	uploader.reset();
		    });
		    
		    uploader.on( 'error', function(type) {
		    	 if(type=="F_EXCEED_SIZE"){
		    		 if(opts.uploadType=="img"){
				        	layer.msg("文件大小超过限制");
		    		 }else{
		    			 layer.open({
						      content: '文件大小超过限制'
						      ,time: 2
						      ,skin: 'msg'
						});
		    		 }
		         }else if(type=="Q_EXCEED_SIZE_LIMIT"){
		        	 if(opts.uploadType=="img"){
				        	layer.msg("总文件大小超过限制");
		    		 }else{
		    			 layer.open({
						      content: '总文件大小超过限制'
						      ,time: 2
						      ,skin: 'msg'
						});
		    		 }
		         }else if(type=="Q_EXCEED_NUM_LIMIT"){
		        	 if(opts.uploadType=="img"){
				        	layer.msg("上传文件数量超过限制");
		    		 }else{
		    			 layer.open({
						      content: '上传文件数量超过限制'
						      ,time: 2
						      ,skin: 'msg'
						});
		    		 }
		         }
		    });

		    // 完成上传完了，成功或者失败，先删除进度条。
		    uploader.on( 'uploadComplete', function( file ) {
		    	if(callback){
		    		callback("uploadComplete",file);
		    		return;
		    	}
		    });
		    
		    uploader.on( 'all', function( type ) {
		    	switch (type) {
					case "startUpload":
						state = 'uploading';
						break;
					case "stopUpload":
						state = 'paused';
						break;
					case "uploadFinished":
						state = 'done';
						break;
				}
		    	
		    	
		    	$("#ctlBtn2"+_self.attr("id")).on( 'click', function() {
		    		if ( state === 'uploading' ) {
		    			 uploader.stop();
		    		}
			    	layer.close($index);
			    	var index=layer.open({
					      content: '您取消了此次上传~'
					      ,time: 2
					      ,skin: 'msg'
					});
			    	uploader.reset();
			    });
		    	
		    	$("#ctlBtn1"+_self.attr("id")).on( 'click', function() {
		    		if ( state != 'uploading' ) {
	        		    uploader.upload();
	        		    $(this).css("cursor","not-allowed");
	        		    $(this).css("disabled",true);
		    		}
			    }); 
		    });
		});
		
	}
	
	// 当有文件添加进来时执行，负责view的创建
    var addFile=function( file,$queue) {
        var $li = $( '<li id="' + file.id + '">' +
                '<p class="title">' + file.name + '</p>' +
                '<p class="imgWrap"></p>'+
                '<p class="progress"><span></span></p>' +
                '</li>' ),
                text,
            $btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>' +
                '<span class="rotateRight">向右旋转</span>' +
                '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
            $prgress = $li.find('p.progress span'),
            $wrap = $li.find( 'p.imgWrap' ),
            $info = $('<p class="error"></p>'),
            showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = '文件大小超出';
                        break;

                    case 'interrupt':
                        text = '上传暂停';
                        break;

                    default:
                        text = '上传失败，请重试';
                        break;
                }

                $info.text( text ).appendTo( $li );
            };

        if ( file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            // @todo lazyload
            $wrap.text( '预览中' );
            uploader.makeThumb( file, function( error, src ) {
                var img;

                if ( error ) {
                    $wrap.text( '不能预览' );
                    return;
                }

                if( isSupportBase64 ) {
                    img = $('<img src="'+src+'">');
                    $wrap.empty().append( img );
                } else {
                    $.ajax('../../server/preview.php', {
                        method: 'POST',
                        data: src,
                        dataType:'json'
                    }).done(function( response ) {
                        if (response.result) {
                            img = $('<img src="'+response.result+'">');
                            $wrap.empty().append( img );
                        } else {
                            $wrap.text("预览出错");
                        }
                    });
                }
            }, thumbnailWidth, thumbnailHeight );

            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;
        }

        file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress.hide().width(0);
            } else if ( prev === 'queued' ) {
                $li.off( 'mouseenter mouseleave' );
                $btns.remove();
            }

            // 成功
            if ( cur === 'error' || cur === 'invalid' ) {
                console.log( file.statusText );
                showError( file.statusText );
                percentages[ file.id ][ 1 ] = 1;
            } else if ( cur === 'interrupt' ) {
                showError( 'interrupt' );
            } else if ( cur === 'queued' ) {
                percentages[ file.id ][ 1 ] = 0;
            } else if ( cur === 'progress' ) {
                $info.remove();
                $prgress.css('display', 'block');
            } else if ( cur === 'complete' ) {
                $li.append( '<span class="success"></span>' );
            }

            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
        });

        $li.on( 'mouseenter', function() {
            $btns.stop().animate({height: 30});
        });

        $li.on( 'mouseleave', function() {
            $btns.stop().animate({height: 0});
        });

        $btns.on( 'click', 'span', function() {
            var index = $(this).index(),
                deg;

            switch ( index ) {
                case 0:
                    uploader.removeFile( file );
                    return;

                case 1:
                    file.rotation += 90;
                    break;

                case 2:
                    file.rotation -= 90;
                    break;
            }

            if ( supportTransition ) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap.css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
            }
        });
        $li.appendTo( $queue );
    };
    
 // 负责view的销毁
    var removeFile=function( file ) {
        var $li = $('#'+file.id);

        delete percentages[ file.id ];
        updateTotalProgress($progress,$info);
        $li.off().find('.file-panel').off().end().remove();
    }

    var updateTotalProgress=function($progress,$info) {
        var loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;

        $.each( percentages, function( k, v ) {
            total += v[ 0 ];
            loaded += v[ 0 ] * v[ 1 ];
        } );

        percent = total ? loaded / total : 0;

        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
        updateStatus($info);
    }

    var updateStatus=function($info) {
        var text = '', stats;

        if ( state === 'ready' ) {
            text = '选中' + fileCount + '张图片，共' +
                    WebUploader.formatSize( fileSize ) + '。';
        } else if ( state === 'confirm' ) {
            stats = uploader.getStats();
            if ( stats.uploadFailNum ) {
                text = '已成功上传' + stats.successNum+ '张照片，'+
                    stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
            }

        } else {
            stats = uploader.getStats();
            text = '共' + fileCount + '张（' +
                    WebUploader.formatSize( fileSize )  +
                    '），已上传' + stats.successNum + '张';

            if ( stats.uploadFailNum ) {
                text += '，失败' + stats.uploadFailNum + '张';
            }
        }

        $info.html( text );
    };
    
//    var uploadSuccess=function(res){};

    var setState=function( val,$upload,$placeHolder,$queue,$statusBar,$progress,$info) {
        var file, stats;
        if ( val === state ) {
            return;
        }
        $upload.removeClass( 'state-' + state );
        $upload.addClass( 'state-' + val );
        state = val;

        switch ( state ) {
            case 'pedding':
                $placeHolder.removeClass( 'element-invisible' );
                $queue.hide();
                $statusBar.addClass( 'element-invisible' );
                uploader.refresh();
                break;

            case 'ready':
                $placeHolder.addClass( 'element-invisible' );
                $( '#filePicker2' ).removeClass( 'element-invisible');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;

            case 'uploading':
                $( '#filePicker2' ).addClass( 'element-invisible' );
                $progress.show();
                $upload.text( '暂停上传' );
                break;

            case 'paused':
                $progress.show();
                $upload.text( '继续上传' );
                break;

            case 'confirm':
                $progress.hide();
                $( '#filePicker2' ).removeClass( 'element-invisible' );
                $upload.text( '开始上传' );

                stats = uploader.getStats();
                if ( stats.successNum && !stats.uploadFailNum ) {
                    setState( 'finish',$upload,$placeHolder,$queue,$statusBar,$progress,$info);
                    return;
                }
                break;
            case 'finish':
                stats = uploader.getStats();
                if ( stats.successNum ) {
                	layer.closeAll();
                	if(!isMobile){
                		layer.msg('上传成功!',{shift:0},function(){
        		    		try
        	    	    	{
        			    		uploadSuccess(0);
        	    	    	}
        	    	    	catch(err)
        	    	    	{
        	    	    	}
        		    	});
			        }else{
			        	var index=layer.open({
						      content: '上传成功!'
						      ,time: 2
						      ,skin: 'msg'
						      ,end : function(){
						    	  uploadSuccess(0);
						      }
						});
			        }
                	uploader.destroy();
                } else {
                    // 没有成功的图片，重设
                    state = 'done';
                    location.reload();
                }
                break;
        }
        updateStatus($info);
    };
	a("upload",null);
})
