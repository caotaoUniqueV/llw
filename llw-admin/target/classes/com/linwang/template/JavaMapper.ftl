package com.linwang.entity;

import com.linwang.entity.base.BaseModelBean;
<#list templateDtos as cl>
<#if cl.type=='datetime'>
import org.springframework.format.annotation.DateTimeFormat;
</#if>
</#list>

public class ${methodName} extends BaseModelBean{
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	<#list templateDtos as cl>
	<#if cl.type=='varchar'>
	private java.lang.String ${cl.lowerAttribute};<#if cl.comment??&&cl.comment!="">//${cl.comment}</#if>
	<#elseif cl.type=='bigint' || cl.type=='int'>
	private java.lang.Integer ${cl.lowerAttribute};<#if cl.comment??&&cl.comment!="">//${cl.comment}</#if>
	<#elseif cl.type=='datetime'>
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date ${cl.lowerAttribute};<#if cl.comment??&&cl.comment!="">//${cl.comment}</#if>
	<#elseif cl.type=='bit'>
	private java.lang.Boolean ${cl.lowerAttribute};<#if cl.comment??&&cl.comment!="">//${cl.comment}</#if>
	</#if>
	</#list>
   
    public java.lang.Integer getId() {
        return id;
    }
    public void setId(java.lang.Integer id) {
        this.id = id;
    }
    <#list templateDtos as cl>
    <#if cl.type=='varchar'>
	<#assign type="java.lang.String">
	<#elseif cl.type=='bigint' || cl.type=='int'>
	<#assign type="java.lang.Integer">
	<#elseif cl.type=='datetime'>
	<#assign type="java.util.Date">
	<#elseif cl.type=='bit'>
	<#assign type="java.lang.Boolean">
	</#if>
    public ${type} get${cl.capitalAttribute}() {
        return ${cl.lowerAttribute};
    }
    public void set${cl.capitalAttribute}(${type} ${cl.lowerAttribute}) {
        this.${cl.lowerAttribute} = ${cl.lowerAttribute};
    }
    </#list>
}
