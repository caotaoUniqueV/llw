package com.linwang.uitls;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.common.base.Strings;

public class HtmlParsePlaintText {

	/**
	 * 将HTML文本转换为纯文本
	 * @param @param html
	 * @param @param maxNumber
	 * @param @return
	 * @return String
	 * @throws
	 * @param html
	 * @param maxNumber
	 * @return
	 */
	public static String toPlainText(String html,Integer maxNumber){
		if (Strings.isNullOrEmpty(html)) {
			return html;
		}
		html = html.trim();
		Document doc = Jsoup.parse(html);
		String tmp = doc.text();
		if (!Strings.isNullOrEmpty(tmp)) {
			tmp = tmp.replaceAll("&nbsp;", "");
			tmp = tmp.replaceAll("	", "");
			tmp = tmp.replaceAll("\\r\\n", "");
			tmp = tmp.replaceAll("&ldquo;", "“");
			tmp = tmp.replaceAll("&rdquo;", "”");
			tmp = tmp.replaceAll("&amp;", "&");
			tmp = tmp.trim();
		}
		if(maxNumber == null){
			return tmp;
		}
		if(tmp.length() <= maxNumber){
			return tmp;
		}
		return tmp.substring(0,maxNumber) + "...";
	}
	
	/**
	 * 将 TEXTAREA 的内容转换为HTML
	 * @param textArea
	 */
	public static String changeTextAreaToHtml(String textArea){
		if(textArea == null || "".equals(textArea)){
			return textArea;
		}
		textArea = textArea.replace("\n", "<br/>");
		textArea = textArea.replace(" ", "&nbsp;");
		return textArea;
	}
}
