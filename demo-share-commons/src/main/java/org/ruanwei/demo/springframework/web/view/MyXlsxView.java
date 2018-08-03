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


// Compatible with Apache POI 3.5 and higher.
public class MyXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //        UserEntity pizza = (UserEntity) model.get("user");
        //
        //        Sheet sheet = workbook.createSheet("sheet 1");
        //        CellStyle style = workbook.createCellStyle();
        //        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        //        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //        style.setAlignment(CellStyle.ALIGN_CENTER);
        //        Row row = null;
        //        Cell cell = null;
        //        int rowCount = 0;
        //        int colCount = 0;
        //
        //        // Create header cells
        //        row = sheet.createRow(rowCount++);
        //
        //        cell = row.createCell(colCount++);
        //        cell.setCellStyle(style);
        //        cell.setCellValue("Name");
        //
        //        cell = row.createCell(colCount++);
        //        cell.setCellStyle(style);
        //        cell.setCellValue("Age");
        //
        //        cell = row.createCell(colCount++);
        //        cell.setCellStyle(style);
        //        cell.setCellValue("Birthday");
        //
        //        // Create data cells
        //        row = sheet.createRow(rowCount++);
        //        colCount = 0;
        //        row.createCell(colCount++).setCellValue(pizza.getName());
        //        row.createCell(colCount++).setCellValue(pizza.getAge());
        //        row.createCell(colCount++).setCellValue(pizza.getBirthday().toString());
        //
        //        for (int i = 0; i < 3; i++)
        //            sheet.autoSizeColumn(i, true);

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
