package org.ruanwei.demo.springframework.web.view;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyXlsView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object obj = model.get("data");
        if (obj != null) {
            if (obj instanceof List) {
                List list = (List) obj;
                if (CollectionUtils.isEmpty(list)) {
                    return;
                }
                Object first = list.get(0);
                Field[] fields = first.getClass().getDeclaredFields();

                Sheet sheet = workbook.createSheet("数据");
                Row row;
                Cell cell;
                int rowCount = 0;
                int colCount = 0;

                row = sheet.createRow(rowCount++);

                for (Field field : fields) {
                    field.setAccessible(true);
                    cell = row.createCell(colCount++);
                    cell.setCellValue(field.getName());
                }

                for (Object item : list) {
                    colCount = 0;
                    row = sheet.createRow(rowCount++);
                    for (Field field : fields) {
                        cell = row.createCell(colCount++);
                        Object itemFild = field.get(item);
                        if (itemFild == null) {
                            cell.setCellValue("");
                        }else {
                            cell.setCellValue(itemFild.toString());
                        }
                    }
                }
            }else {
                Object first = obj;
                Field[] fields = first.getClass().getDeclaredFields();

                Sheet sheet = workbook.createSheet("数据");
                Row row;
                Cell cell;
                int rowCount = 0;
                int colCount = 0;

                row = sheet.createRow(rowCount++);

                for (Field field : fields) {
                    field.setAccessible(true);
                    cell = row.createCell(colCount++);
                    cell.setCellValue(field.getName());
                }

                Object item = obj;
                colCount = 0;
                row = sheet.createRow(rowCount++);

                for (Field field : fields) {
                    Object itemFild = field.get(item);
                    cell = row.createCell(colCount++);
                    if (itemFild == null) {
                        cell.setCellValue("");
                    }else {
                        cell.setCellValue(itemFild.toString());
                    }
                }
            }
        }
    }

}
