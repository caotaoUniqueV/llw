package com.linwang.uitls.web;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

public class ExcelView extends AbstractXlsxView {

	/**
	 * 使用：
	 * model.addAttribute("fileName", "提现记录");
	 * model.addAttribute("titles", titles);
	 * model.addAttribute("paramList", paramList);
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String fileName = (String) model.get("fileName");
		Sheet sheet = workbook.createSheet(fileName);
		Row row = sheet.createRow(0);
		int columnIndex = 0;
		Cell cell = null;

		String[] titles = (String[]) model.get("titles");
		@SuppressWarnings("unchecked")
		List<String[]> paramList = (List<String[]>) model.get("paramList");
		for (String title : titles) {
			cell = row.createCell(columnIndex++, Cell.CELL_TYPE_STRING);
			cell.setCellValue(title);
		}

		for (int i = 0; i < paramList.size(); i++) {
			String[] params = paramList.get(i);
			row = sheet.createRow(i + 1); // 从第二行开始
			columnIndex = 0;
			for (String param : params) {
				cell = row.createCell(columnIndex++, Cell.CELL_TYPE_STRING);
				cell.setCellValue(param);
			}
		}
		
		for (int i = 0; i < titles.length; i++) {
			sheet.autoSizeColumn(i+1);
		}

		fileName = URLEncoder.encode(fileName, "UTF-8");

		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
	}

}

