<#macro mapperEl2 value>${r"${"}${value}}</#macro>
<#macro mapperEl3 value>${r"<#include"} '${value}' encoding="UTF-8"/></#macro>
<#macro mapperEl4 value>${r"<#if "}${value}.id??></#macro>
<#macro mapperEl5 value>${r"编辑<#else>添加</#if>"}</#macro>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${modular}</title>
	<@mapperEl3 '/include/cssjs_up.html'/>
</head>

<body class="gray-bg">
	<div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-sm-12">
            <ol class="breadcrumb" style="margin-top: 10px;margin-bottom: -10px;">
                <li>
                    <a href="../${rpcService}/${rpcService}Page">${modular}</a>
                </li>
                <li>
                    <strong><@mapperEl4 '${rpcService}'/><@mapperEl5 ''/></strong>
                </li>
            </ol>
        </div>
    </div>
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                    	<form class="form-horizontal m-t" id="form">
                    		<input type="hidden" name="id" value="<@mapperEl2 '${rpcService}.id'/>"/>
                    		<#list templateDtos as tm>
                    			<#if tm.type!='datetime'&&tm.lowerAttribute!='id'>
		                            <div class="form-group">
		                                <label class="col-sm-3 control-label">${tm.comment}：</label>
		                                <div class="col-sm-8">
		                                    <input name="${tm.lowerAttribute}" msg="请输入${tm.comment}" class="form-control" type="text" value="<@mapperEl2 '${rpcService}.${tm.lowerAttribute}'/>">
		                                </div>
		                            </div>
	                            </#if>
                            </#list>
                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button class="btn btn-primary" type="button" id="tj">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
<@mapperEl3 '/include/cssjs_down.html'/>
<script type="text/javascript" src="../plugin/layui/oliverui.js"></script>
    <script type="text/javascript">
    $(function(){
    	$('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    	Oliver.use(['ajax','login'],function(ui){
    		$("#tj").on("click",function(){
    			$(this).verify({
        			url : "../${rpcService}/save",
        			formId : "form",
        			icon : 1,
        			loading : "提交中...",
        			success : function (result){
        				if(result.code==0){
        					location.href="../${rpcService}/${rpcService}Page";
        				}else{
        					showToast(result);
        				}
        			}
        		})
    		})
    	});
    });
    </script>
</body>

</html>
