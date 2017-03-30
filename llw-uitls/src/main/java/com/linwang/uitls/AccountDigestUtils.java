package com.linwang.uitls;

import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.alibaba.fastjson.JSONObject;

public class AccountDigestUtils {
	private static final Logger LOGGER = LogManager.getLogger(AccountDigestUtils.class);
	
	private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String ALGORITHM_NAME = "Blowfish";
    
	/**
	 * 加密密码，先进行密码字段的sha1加密，再和用户名一起进行MD5加密
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public static String getMd5Pwd(String userName,String pwd){
		return DigestUtils.md5Hex(DigestUtils.sha1Hex(pwd) + userName);
	}
	/**
	 * 实体信息进行加密处理
	 * @param userIdentity
	 * @param secretKey
	 * @param extParams
	 * @return
	 */
	public static String serialize(Object userIdentity,String secretKey,Map<String,Object> extParams) {
		if(userIdentity instanceof String){
			return encrypt((String)userIdentity, secretKey);
		}
		JSONObject json = (JSONObject)JSONObject.toJSON(userIdentity);
		if(extParams != null){
			for(String key : extParams.keySet()){
				json.put(key, extParams.get(key));
			}
		}
        return encrypt(json.toString(), secretKey);
    }
	/**
	 * 解密还原实体信息数据
	 * @param type
	 * @param value
	 * @param secretKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unserialize(Class<T> type,String value,String secretKey) {
		if(type.getName().equals("java.lang.String")){
			return (T)decrypt(value, secretKey);
		}
        String text = decrypt(value, secretKey);
        JSONObject json = JSONObject.parseObject(text);
        return JSONObject.toJavaObject(json, type);
    }
	
	private static String encrypt(String text, String key) {
        try {
            SecretKey secretKey = new SecretKeySpec(DigestUtils.md5(key), ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeBase64URLSafeString(cipher.doFinal(text.getBytes(DEFAULT_CHARSET)));
        } catch (Exception e) {
        	LOGGER.error("",e);
            return null;
        }
    }

	private static String decrypt(String cipherText, String key) {
        try {
            SecretKey secretKey = new SecretKeySpec(DigestUtils.md5(key), ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] data = Base64.decodeBase64(cipherText);
            return new String(cipher.doFinal(data), DEFAULT_CHARSET);
        } catch (Exception e) {
        	return "";
        }
    }
}
