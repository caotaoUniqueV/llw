package com.linwang.uitls;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	private static final Log LOGGER = LogFactory.getLog(HttpUtil.class);  
	/**
	 * 用 GET 方法提交数据
	 * 作者：龚国君
	 * @date 2014年1月23日 上午8:50:27
	 * @param @param url
	 * @param @param ParamMap
	 * @param @param headerParamMap
	 * @param @param debug
	 * @param @return
	 * @param @throws ClientProtocolException
	 * @param @throws IOException
	 * @return String
	 * @throws
	 * @param url
	 * @param ParamMap
	 * @param headerParamMap
	 * @param debug
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url,Map<String,Object> ParamMap,Map<String,String> headerParamMap){
		try {
			CloseableHttpResponse execute;
		    CloseableHttpClient client;
		    
		    if(ParamMap != null && ParamMap.size() > 0){
		    	url = formatGetParameter(url, ParamMap);
		    }
		    
		    
		    HttpGet get = new HttpGet(url);
		    if(headerParamMap != null && headerParamMap.size() > 0){
		    	for(String key: headerParamMap.keySet()){
		    		get.addHeader(key,headerParamMap.get(key));
		    	}
		    }
		    client = HttpClients.custom()  
		            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0")  
		            .setDefaultRequestConfig(
		                    RequestConfig.custom()
			                    .setSocketTimeout(60000)
			                    .setConnectTimeout(60000)
			                    .setConnectionRequestTimeout(60000)
	                            .build()).build();
		    if(url.startsWith("https")){
		    	client = createSSLClientDefault();
		    }
	        execute = client.execute(get);
	        String content = EntityUtils.toString(execute.getEntity(),"UTF-8");
        	LOGGER.debug(url);
        	Charset charset = ContentType.getOrDefault(execute.getEntity()).getCharset();
        	LOGGER.debug(charset);
        	LOGGER.debug(execute.getStatusLine().getStatusCode());
        	LOGGER.debug(content);
	        try {
	        	execute.close();
	 	        client.close();
			} catch (Exception e) {}
	        return content;
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		}
	}
	/**
	 * 用POST方式提交数据
	 * 作者：龚国君
	 * @date 2014年1月23日 上午9:00:25
	 * @param @param url
	 * @param @param body
	 * @param @param ParamMap
	 * @param @param headerParamMap
	 * @param @param debug
	 * @param @return
	 * @return String
	 * @throws
	 * @param url
	 * @param body
	 * @param ParamMap
	 * @param headerParamMap
	 * @param debug
	 * @return
	 */
	public static String postMethodUrl(String url,String body,Map<String,Object> paramMap,Map<String,String> headerParamMap){
	    return postMethodUrl(url, body, paramMap, headerParamMap, null);
	}
	public static String postMethodUrl(String url,String body,Map<String,Object> paramMap,Map<String,String> headerParamMap, CloseableHttpClient closeableHttpClient){
		try {
			CloseableHttpResponse execute;
		    CloseableHttpClient client;
		    
		    HttpPost post = new HttpPost(url);
		    if(headerParamMap != null && headerParamMap.size() > 0){
		    	for(String key: headerParamMap.keySet()){
		    		post.addHeader(key,headerParamMap.get(key));
		    	}
		    }
		    if(paramMap != null && paramMap.size() > 0){
		    	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		    	for(String key: paramMap.keySet()){
		    		Object value = paramMap.get(key);
		    		if (value != null) {
		    			nvps.add(new BasicNameValuePair(key,String.valueOf(value)));
					}
		    	}
		    	post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		    }
		    if(body != null && !"".equals(body)){
		    	post.setEntity(new StringEntity(body, "UTF-8"));
		    }
		    if(closeableHttpClient == null){
		        client = HttpClients.custom()  
	                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0")  
	                    .setDefaultRequestConfig(
	                            RequestConfig.custom()
	                                .setSocketTimeout(60000)
	                                .setConnectTimeout(60000)
	                                .setConnectionRequestTimeout(60000)
	                                .build()).build();
	            if(url.startsWith("https")){
	                client = createSSLClientDefault();
	            }
		    }else{
		        client = closeableHttpClient;
		    }
		    
	        execute = client.execute(post);
	        String content = EntityUtils.toString(execute.getEntity());
	        LOGGER.debug(url);
        	Charset charset = ContentType.getOrDefault(execute.getEntity()).getCharset();
        	LOGGER.debug(charset);
        	LOGGER.debug(execute.getStatusLine().getStatusCode());
        	LOGGER.debug(content);
	        execute.close();
	        client.close();
	        return content;
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		}
	}
	
	/**
	 * 格式化GET参数
	 * 作者：龚国君
	 * @date 2014年1月16日 下午6:04:42
	 * @param @param url
	 * @param @param argsMap
	 * @param @return
	 * @return String
	 * @throws
	 * @param url
	 * @param argsMap
	 * @return
	 */
	public static String formatGetParameter(String url,Map<String, Object> argsMap){
		if (url!=null && url.length()>0) {
			if (!url.endsWith("?")) {
				url = url +"?";
			}
			if (argsMap!=null && !argsMap.isEmpty()) {
				Set<Entry<String, Object>> entrySet = argsMap.entrySet();
				Iterator<Entry<String, Object>> iterator = entrySet.iterator();
				while(iterator.hasNext()){
						Entry<String, Object> entry = iterator.next();
						if (entry!=null) {
							String key = entry.getKey();
							String value = (String) entry.getValue();
							url = url + key + "=" + value;
							if (iterator.hasNext()) {
								url = url +"&";
							}
						}
				}
			}
		}
		return url;
	}
	
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain,
				String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients
					.custom()
					.setSSLSocketFactory(sslsf)
					.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0")
					.setDefaultRequestConfig(RequestConfig.custom()
		                    .setSocketTimeout(10000)
		                    .setConnectTimeout(10000)
		                    .setConnectionRequestTimeout(10000)
                            .build())
					.build();
		} catch (KeyManagementException e) {
			LOGGER.error(e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e);
		} catch (KeyStoreException e) {
			LOGGER.error(e);
		}
		return null;
	}
}
