package com.zakharov.service;

import com.zakharov.dao.Dao;
import com.zakharov.entity.User;
import com.zakharov.exception.IncorrectCellValueException;
import com.zakharov.util.UserUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
@NoArgsConstructor
@Transactional
@Data
public class FileService {

    private Dao<User> userDao;
    private UserUtil userUtil;

    @Autowired
    public FileService(Dao<User> userDao, UserUtil userUtil) {
        this.userUtil = userUtil;
        this.userDao = userDao;
    }

    public void uploadFile(MultipartFile multipartFile) {
        FileInputStream file;
        try {
            File tempFile = File.createTempFile("sdf", ".xlsx");
            multipartFile.transferTo(tempFile);
            file = new FileInputStream(tempFile);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                if (i > 0) {
                    Row row = sheet.getRow(i);
                    User user = new User();
                    Object[] cellsData = new Object[6];
                    for (int j = 0; j < 6; j++) {
                        Cell cell = row.getCell(j);
                        cellsData[j] = switch (cell.getCellType()) {
                            case STRING, BLANK -> cell.getStringCellValue();
                            case NUMERIC -> cell.getNumericCellValue();
                            default -> throw new IncorrectCellValueException("Wrong cell value");
                        };

                    }

                    if (cellsData[0] instanceof String && ((String) cellsData[0]).isEmpty()) return;
                    user.setNickName((String) cellsData[1]);
                    user.setAccount_id((((Double) cellsData[2]).longValue()));
                    user.setFamily_name((String) cellsData[3]);
                    user.setGenre((String) cellsData[4]);
//                    user.setFirst_point((((Double) cellsData[5]).longValue()));
//                    user.setSecond_point((((Double) cellsData[6]).longValue()));
                    user.setTotal_score((((Double) cellsData[5]).longValue()));
                    userDao.save(user);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public ByteArrayInputStream download() {

        List<User> users = userDao.getAll();
        return userUtil.userToExcel(users);
    }
}
