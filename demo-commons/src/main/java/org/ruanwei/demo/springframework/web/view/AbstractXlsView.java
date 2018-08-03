
/*
 * 
 * 由于org.springframework.web.servlet.view.document.AbstractPdfViw支持的poi较老，重写一个于此
 */
package org.ruanwei.demo.springframework.web.view;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * 由于org.springframework.web.servlet.view.document.AbstractXlsxViw支持的poi较老，重写一个于此
 */
public abstract class AbstractXlsView extends org.springframework.web.servlet.view.document.AbstractXlsView {

	/**
	 * Default Constructor.
	 * <p>Sets the content type of the view to
	 * {@code "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}.
	 */
	public AbstractXlsView() {
		setContentType("application/vnd.ms-excel");
	}

	/**
	 * This implementation creates an {@link XSSFWorkbook} for the XLSX format.
	 */
	@Override
	protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
		return new HSSFWorkbook();
	}

}

