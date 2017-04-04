<#macro mapperEl value>${r"<#include"} '${value}' encoding="UTF-8"/></#macro>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${modular}</title>
	<@mapperEl '/include/cssjs_up.html'/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <input type="text" placeholder="请输入关键词" class="input-sm form-control"id="name"> <span class="input-group-btn">
                                        <button type="button" class="btn btn-sm btn-primary" onclick="list($('#name').val())">搜索</button> </span>
                                </div>
                            </div>
                            <#if add==0>
                            <div class="col-sm-2">
                            	<button type="button" class="btn btn-sm btn-success" onclick="window.location.href='../${rpcService}/add'">添加</button>
                            </div>
                            </#if>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>	
                                    		<th>编号</th>
                                    	<#list templateDtos as tm>
                                        	<th>${tm.comment}</th>
                                        </#list>
                                        <#if add==0||del==0><th>操作</th></#if>
                                    </tr>
                                </thead>
                                <tbody id="list">
                                    
                                </tbody>
                            </table>
                            <div id="demo7" style="text-align:center;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<@mapperEl '/include/cssjs_down.html'/>
<script type="text/javascript" src="../plugin/layui/oliverui.js"></script>
<script type="text/x-dot-template" id="customer-tpl">
	   {{~it.property:p:index}}
		 		<tr> 
		 			  <td>{{!p.id}}</td>
		 			  <#list templateDtos as tm>
		 			  	<#if tm.type='datetime'>
		 			  	<td>{{! new Date(p.${tm.lowerAttribute}).Format("yyyy-MM-dd hh:mm:ss")}}</td>
		 			  	<#else>
                      	<td>{{!p.${tm.lowerAttribute}}}</td>
                      	</#if>
                      </#list>
                      <#if add==0||del==0><td>
						 <#if add==0><button type="button" class="btn btn-outline btn-primary" style="margin-right: 10px;" onclick="window.location.href='../${rpcService}/add?id={{!p.id}}'">编辑</button></#if>
						 <#if del==0><button type="button" class="btn btn-outline btn-danger" onclick="del({{!p.id}},this)">删除</button></#if>
					  </td></#if>
                </tr>
	   {{~ }}
</script>
<script type="text/javascript">
var ui;
$(function(){
	Oliver.use(['ajax','laypage','dot'],function(u){
		ui=u;
		list('');
	});
});
function list(name){
	ui.laypage({
		url : "../${rpcService}/list",
		data :{
			id : name
		},
		elem : "#list",
		cont : "demo7",
		rows : 10,
		versions : "v2",
		success : function(res){
			var tpl = doT.template($("#customer-tpl").text());
			var data = {property:res.data.result};
        	return tpl(data);
		}
	});
}
<#if del==0>
function del(id,obj){
	var index=parent.layer.confirm('是否删除该管理员？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			parent.layer.close(index);
			$(obj).ajax({
	    		url : "../${rpcService}/del?id="+id,
	    		icon : 1,
	    		loading : "删除中...",
	    		success : function(result){
	    			if(result.code==0){
						location.href="../${rpcService}/${rpcService}Page";
					}else{
						showToast(result);
					}
	    		}
	    	});
		})
}
</#if>
</script>
</body>

</html>
