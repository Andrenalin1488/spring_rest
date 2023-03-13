package com.zakharov.util;

import com.zakharov.entity.User;
import com.zakharov.handler.FieldInfoHandler;
import com.zakharov.handler.TypeInfoHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class UserUtil {

    private final FieldInfoHandler fieldInfoHandler;
    private final TypeInfoHandler typeInfoHandler;

    public UserUtil(FieldInfoHandler fieldInfoHandler, TypeInfoHandler typeInfoHandler) {
        this.fieldInfoHandler = fieldInfoHandler;
        this.typeInfoHandler = typeInfoHandler;
    }

    public ByteArrayInputStream userToExcel(List<User> users) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(typeInfoHandler.getTypeName(User.class));

            addHeader(sheet);

            addUsers(users, sheet);

            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error");
        }
    }

    private void addUsers(List<User> users, Sheet sheet) {
        int[] rowIdx = {1};

        users.forEach(user -> {
            Row row = sheet.createRow(rowIdx[0]++);

            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getNickName());
            row.createCell(2).setCellValue(user.getAccount_id());
            row.createCell(3).setCellValue(user.getFamily_name());
            row.createCell(4).setCellValue(user.getGenre());
            row.createCell(5).setCellValue(user.getTotal_score());
        });
    }

    private void addHeader(Sheet sheet) {
        Row header = sheet.createRow(0);

        int[] count = {0};
        Map<String, String> name = fieldInfoHandler.getFieldNameMap(User.class);
        name.forEach((key, value) -> {
            Cell cell = header.createCell(count[0]++);
            cell.setCellValue(value);
        });
    }
}
