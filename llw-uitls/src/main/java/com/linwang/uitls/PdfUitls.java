package com.linwang.uitls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;



public class PdfUitls{
	/**
	 * 生成pdf
	 * @return 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws DocumentException 
	 */
	public static String createdPdf() throws MalformedURLException, IOException, DocumentException{
		Document pdfDoc = new Document();  
		StyleSheet st = new StyleSheet();
        // 将要生成的 pdf 文件的路径输出流  
        FileOutputStream pdfFile = new FileOutputStream(new File("G:/test.pdf"));  
        // 用 Document 对象、File 对象获得 PdfWriter 输出流对象  
        PdfWriter.getInstance(pdfDoc, pdfFile);  
      //页边空白  
        pdfDoc.open();  // 打开 Document 文档  
        List<Element> p = HTMLWorker.parseToList(new FileReader("D:/Users/Administrator/llw/llw-web/src/main/webapp/WEB-INF/views/index.jsp"), st); 
        
        for (int k = 0; k < p.size(); ++k)  
        	pdfDoc.add(p.get(k));  
        pdfDoc.close();  
        return "";
	}
}
