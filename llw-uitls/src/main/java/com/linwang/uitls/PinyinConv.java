package com.linwang.uitls;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Strings;

/**
 * 
 * @date 2010-1-22
 * 
 * @bugs 不支持多音字处理
 */

public class PinyinConv {
	private static final Log log = LogFactory.getLog(PinyinConv.class);
	private static String replaceSameChars(String chines){
		if(chines == null){
			chines = "";
		}
		chines = chines.replaceAll(",", "");
		chines = chines.replaceAll("，", "");
		chines = chines.replaceAll("\\.", "");
		chines = chines.replaceAll("。", "");
		chines = chines.replaceAll("、", "");
		return chines;
	}
	
	/**  
    * 汉字转换位汉语拼音首字母，英文字符不变  
    * @param chines 汉字  
    * @return 拼音  
    */  
	public static String converterToFirstSpell(String chines){
    	chines = replaceSameChars(chines);
        String pinyinName = "";   
        char[] nameChar = chines.toCharArray();   
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {   
            if (nameChar[i] > 128) {   
                try {   
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);   
                } catch (Exception e) {   
                	log.error("在转换汉字<"+chines+">成拼音时出错！");
                }   
            }else{   
                pinyinName += nameChar[i];   
            }   
        }   
        return pinyinName;   
    }
	    
    /**  
    * 汉字转换位汉语拼音，英文字符不变  
    * @param chines 汉字  
    * @return 拼音  
    */  
    public static String converterToSpell(String chines){      
    	chines = replaceSameChars(chines);
        String pinyinName = "";   
        char[] nameChar = chines.toCharArray();   
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
        for (int i = 0; i < nameChar.length; i++) {   
            if (nameChar[i] > 128) {   
                try {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
                } catch (Exception e) {   
                	log.error("在转换汉字<"+chines+">成拼音时出错！");
                }   
            }else{   
                pinyinName += nameChar[i];   
            }   
        }   
        return pinyinName;   
    }
    
    public static char getFirstLetter (String chines) {
    	String pinyin = converterToSpell(chines);
    	if (Strings.isNullOrEmpty(pinyin)) {
			return 0;
		}
    	return pinyin.charAt(0);
    }
}

