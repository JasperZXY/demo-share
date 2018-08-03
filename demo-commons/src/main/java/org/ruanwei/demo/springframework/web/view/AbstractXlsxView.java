
/*
 * 
 * 由于org.springframework.web.servlet.view.document.AbstractPdfViw支持的poi较老，重写一个于此
 */
package org.ruanwei.demo.springframework.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

/**
 * 
 * 由于org.springframework.web.servlet.view.document.AbstractXlsxViw支持的poi较老，重写一个于此
 */
public abstract class AbstractXlsxView extends AbstractXlsView {

	/**
	 * Default Constructor.
	 * <p>Sets the content type of the view to
	 * {@code "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}.
	 */
	public AbstractXlsxView() {
		setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	/**
	 * This implementation creates an {@link XSSFWorkbook} for the XLSX format.
	 */
	@Override
	protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
		return new HSSFWorkbook();
	}

}

