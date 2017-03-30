package com.linwang.uitls.web;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class DownLoadCommonView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = (String) model.get("fileName");
		String contentType = (String) model.get("contentType");
		
		setContentType(contentType);
		
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		
		byte[] buffer = (byte[]) model.get("buffer");
		OutputStream ous = new BufferedOutputStream(response.getOutputStream());
		ous.write(buffer);
		ous.flush();
		ous.close();
	}

}
