package com.linwang.uitls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

public class MobileUtils {
    private static String MOBILE_REGEX = "^(134|135|136|137|138|139|147|150|151|152|157|158|159|182|183|184|187|188|130|131|132|145|155|156|185|186|133|149|153|177|180|181|189|170|173|175|176|178)\\d{8}$";
    
    /**
     * @Title: MobileUtils.java
     * @Prject: util
     * @Description: 判断是否为手机号码
     * @version: V1.0 
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        if(Strings.isNullOrEmpty(mobile)){
            return false;
        }
        Pattern p = Pattern.compile(MOBILE_REGEX);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
    
    public static String maskMobileNo(String mobileNo){       
        StringBuilder sb = new StringBuilder();
        sb.append(mobileNo.substring(0, 3)).append("****").append(mobileNo.substring(7));
        return sb.toString();      
    }
}
