<#macro mapperEl value>${r"#{"}${value}}</#macro>
<#macro mapperE5 value>				 ${r"#{"}${value}}</#macro>
<#macro mapperE3 value>${r"#{entity."}${value}}</#macro>
<#macro mapperE4 value>${r"#{condition."}${value}}</#macro>
<#macro mapperEl2 value2>${r"#{"}${value2}}</#macro>
<#macro orderBy value2>${r"${"}${value2}}</#macro>
<#macro aggregate_sql value2>${r"${"}${value2}}</#macro>
<#macro numberCloumsColumn>${r"${"}${'numberCloums.column'}}</#macro>
<#macro numberCloumsNumber>${r"#{"}${'numberCloums.number'}}</#macro>
<#macro expressionColumn>${r"${"}${'expression.column'}}</#macro>
<#macro expressionOperator>${r"${"}${'expression.operator'}}</#macro>
<#macro expressionValue>${r"#{"}${'expression.value'}}</#macro>
<#macro expressionValue1>${r"#{"}${'expression.value1'}}</#macro>
<#macro pageSize>${r"#{"}${'pageSize'}}</#macro>
<#macro pageBeginIndex>${r"#{"}${'pageBeginIndex'}}</#macro>
<#macro value>							${r"#{"}${'value'}}</#macro>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperName}">
         <resultMap id="BaseResultMap" type="com.linwang.entity.${methodName}" extends="associationMap">
			<id column="id" property="id" />
			<#list templateDtos as cl>
			<result column="${cl.onColumnName}" property="${cl.columnName}" />
			</#list>
		 </resultMap>
		 <sql id="Base_Column_List" >
				`id`,
				<#list templateDtos as cl>
	            <#if !cl_has_next>`${cl.onColumnName}`
	            <#else>`${cl.onColumnName}`,</#if>
	            </#list>
		 </sql>
		 <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Integer" >
		    SELECT 
		    <include refid="Base_Column_List" />
		    FROM `${tableName}`
		    WHERE `id` = <@mapperEl2 'id'/>
		  </select>
		  <select id="getByCondition" resultMap="BaseResultMap" parameterType="java.util.Map" >
		    SELECT 
		    <include refid="Base_Column_List" />
		    FROM `${tableName}`
		    <include refid="map_params_where_condition" />
			<if test="orderBy == null" >
		      ORDER BY `id` DESC
		    </if>
		    <if test="orderBy != null" >
		      ORDER BY <@orderBy 'orderBy'/>
		    </if>
			LIMIT 1
		  </select>
		  <select id="aggregate" parameterType="java.util.Map" resultType="java.util.Map">
		    SELECT <@orderBy 'aggregate_sql'/> FROM `${tableName}`
		    <include refid="map_params_where_condition" />
		  </select>
		  <delete id="deleteByPrimaryKey" parameterType="java.lang.Integer" >
		    DELETE FROM `${tableName}`
		    WHERE `id` = <@mapperEl2 'id'/>
		  </delete>
         <insert id="insert${methodName}" parameterType="com.linwang.entity.${methodName}">
         	 <selectKey resultType="java.lang.Integer" keyProperty="id" order="AFTER" >
		      SELECT LAST_INSERT_ID()
		     </selectKey>
             insert into `${tableName}`(
	             <#list templateDtos as cl>
	             <#if !cl_has_next>`${cl.onColumnName}`
	             <#else>`${cl.onColumnName}`,</#if>
	             </#list>
             ) 
	         values 
	         (
	         	<#list templateDtos as cl2>
	         	<#if !cl2_has_next><@mapperE5 cl2.columnName/>
	         	<#else><@mapperE5 cl2.columnName/>,</#if>
	         	</#list>    
		      )    
	      </insert>
	      <insert id="insertSelective" parameterType="com.linwang.entity.${methodName}" >
			    <selectKey resultType="java.lang.Integer" keyProperty="id" order="AFTER" >
			      SELECT LAST_INSERT_ID()
			    </selectKey>
			    INSERT INTO `${tableName}`
			    <trim prefix="(" suffix=")" suffixOverrides="," >
					  <#list templateDtos as cl>
		              <if test="${cl.columnName}!= null" >
						`${cl.onColumnName}`,
					  </if>
		              </#list>
			    </trim>
			    <trim prefix="values (" suffix=")" suffixOverrides="," >
					  <#list templateDtos as cl2>
		         	   <if test="${cl2.columnName} != null" >
						<@mapperEl cl2.columnName/>,
					   </if>
		         	  </#list> 
			    </trim>
		  </insert>
          <update id="updateByPrimaryKeySelective" parameterType="com.linwang.entity.${methodName}" >
			UPDATE `${tableName}`
			<set >
			  <#list templateDtos as cl2>
		         <if test="${cl2.columnName} != null" >
					`${cl2.onColumnName}` = <@mapperEl cl2.columnName/>,
			  	 </if>	
		      </#list> 
			</set>
			WHERE `id` = <@mapperEl2 'id'/>
		  </update>
		  <update id="updateByPrimaryKey" parameterType="com.linwang.entity.${methodName}" >
			UPDATE `${tableName}` SET
				<#list templateDtos as cl2>
					<#if !cl2_has_next>`${cl2.onColumnName}` = <@mapperEl cl2.columnName/>
	         		<#else>`${cl2.onColumnName}` = <@mapperEl cl2.columnName/>,</#if>
		        </#list>
			WHERE `id` = <@mapperEl2 'id'/>
		  </update>
		  <update id="updateByCondition" parameterType="java.util.Map" >
			UPDATE `${tableName}`
			<set>
			  <#list templateDtos as cl2>
		         <if test="entity.${cl2.columnName} != null" >
					`${cl2.onColumnName}` = <@mapperE3 cl2.columnName/>,
			  	 </if>	
		      </#list> 
			</set>
			<include refid="map_params_where_condition" />
		  </update>
		  <update id="updateIncreateNumbers" parameterType="java.util.Map" >
			UPDATE `${tableName}`
			<set>
			  <foreach collection="increateNumbers" item="numberCloums">
				`<@numberCloumsColumn/>` = `<@numberCloumsColumn/>` + <@numberCloumsNumber/>,
			  </foreach>
			</set>
			<include refid="map_params_where_condition" />
		  </update>
		  <sql id="map_params_where_condition" >
		    <trim prefix="WHERE" prefixOverrides="AND">
		    	<if test="id != null" >
			       AND `id` = <@mapperEl2 'id'/>
			    </if>
		      <#list templateDtos as cl2>
		      	<if test="${cl2.onColumnName} != null" >
			       AND `${cl2.onColumnName}` = <@mapperEl cl2.columnName/>
			    </if>
		      </#list>
			  <if test="condition != null">
			  	  <#list templateDtos as cl2>
				  <if test="condition.${cl2.columnName} != null" >
			        AND `${cl2.onColumnName}` = <@mapperE4 cl2.columnName/>
				  </if>
				  </#list>
			  </if>
			  <if test="vo != null and !vo.expressionChainList.empty">
		        AND
		        <foreach collection="vo.expressionChainList" item="expressionChain" separator="OR">
		          <if test="!expressionChain.expressionList.empty">
		            <foreach collection="expressionChain.expressionList" item="expression" separator="AND">
		              <choose>
		                <when test="expression.type == 0">
		                  `<@expressionColumn/>` <@expressionOperator/>
		                </when>
		                <when test="expression.type == 1">
		                  `<@expressionColumn/>` <@expressionOperator/> <@expressionValue/>
		                </when>
		                <when test="expression.type == 2">
		                  `<@expressionColumn/>` <@expressionOperator/> <@expressionValue/> AND <@expressionValue1/>
		                </when>
		                <when test="expression.type == 3">
		                  `<@expressionColumn/>` <@expressionOperator/>
		                  <foreach collection="expression.values" item="value" open="(" separator="," close=")">
		                     	<@value/>
		                     
		                  </foreach>
		                </when>
		              </choose>
		            </foreach>
		          </if>
		        </foreach>
		      </if>
		      <if test="expressionChainList != null and !expressionChainList.empty">
		        AND
		        <foreach collection="expressionChainList" item="expressionChain" separator="OR">
		          <if test="!expressionChain.expressionList.empty">
		            <foreach collection="expressionChain.expressionList" item="expression" separator="AND">
		              <choose>
		                <when test="expression.type == 0">
		                  `<@expressionColumn/>` <@expressionOperator/>
		                </when>
		                <when test="expression.type == 1">
		                  `<@expressionColumn/>` <@expressionOperator/> <@expressionValue/>
		                </when>
		                <when test="expression.type == 2">
		                  `<@expressionColumn/>` <@expressionOperator/> <@expressionValue/> AND <@expressionValue1/>
		                </when>
		                <when test="expression.type == 3">
		                  `<@expressionColumn/>` <@expressionOperator/>
		                  <foreach collection="expression.values" item="value" open="(" separator="," close=")">
		                     	<@value/>
		                     
		                  </foreach>
		                </when>
		              </choose>
		            </foreach>
		          </if>
		        </foreach>
		      </if>
		    </trim>
		 </sql>
         <select id="getList" resultMap="BaseResultMap" parameterType="java.util.Map" >
		    SELECT
		    <include refid="Base_Column_List" />
		    FROM `${tableName}`
		    <include refid="map_params_where_condition" />
		    <if test="orderBy == null" >
		      ORDER BY `id` DESC
		    </if>
		    <if test="orderBy != null" >
		      ORDER BY <@orderBy 'orderBy'/>
		    </if>
		    <if test="pageBeginIndex != null and pageSize != null" >
		      LIMIT <@pageBeginIndex/>,<@pageSize/>
		    </if>
		    <if test="pageBeginIndex == null and pageSize != null" >
		      LIMIT <@pageSize/>
		    </if>
		    <if test="pageBeginIndex != null and pageSize == null" >
		      LIMIT <@pageBeginIndex/>
		    </if>
		  </select>
		  <delete id="delete" parameterType="java.util.Map" >
		    DELETE
		    FROM `${tableName}`
		    <include refid="map_params_where_condition" />
		  </delete>
		  <select id="getCount" resultType="java.lang.Integer" parameterType="java.util.Map" >
		    SELECT COUNT(id)
		    FROM `${tableName}`
		    <include refid="map_params_where_condition" />
		  </select>
</mapper>