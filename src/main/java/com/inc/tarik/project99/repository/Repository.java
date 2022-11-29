package com.inc.tarik.project99.repository;

import com.inc.tarik.project99.database.Database;
import com.inc.tarik.project99.dto.Faculty;
import com.inc.tarik.project99.dto.RowDTO;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.time.Year;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
@AllArgsConstructor
public class Repository {
    private final File DATABASE_FILE = new File("database.xls");

    private Database database;


    public void load() {
        importFromXLS(DATABASE_FILE);
    }

    public void save() {
        exportToXLS(DATABASE_FILE);
    }

    public void importFromXLS(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            Iterable<Row> sheet = workbook.getSheetAt(0);
            List<RowDTO> rows = StreamSupport.stream(sheet.spliterator(), false)
                    .map(this::toRowIfCorrect)
                    .filter(RowDTO::isNotEmpty)
                    .toList();

            database.importAll(rows);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportToXLS(File file) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("1");
            fillSheet(sheet);
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillSheet(XSSFSheet sheet) {
        AtomicInteger index = new AtomicInteger(0);
        database.stream().forEach(rowDTO -> fillRow(rowDTO, sheet, index.getAndIncrement()));
    }

    private void fillRow(RowDTO rowDTO, XSSFSheet sheet, int index) {
        Row topRow = sheet.createRow(index);
        Field[] rowFields = Arrays.stream(rowDTO.getClass().getDeclaredFields())
                .filter(field -> !field.getName().equals("EMPTY"))
                .toArray(Field[]::new);

        for (int i = 0; i < rowFields.length; i++) {
            Field field = rowFields[i];
            field.setAccessible(true);
            try {
                topRow.createCell(i).setCellValue(field.get(rowDTO).toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private RowDTO toRowIfCorrect(Row row) {
        try {
            Integer id = Integer.valueOf(row.getCell(0).getStringCellValue());
            String name = row.getCell(1).getStringCellValue();
            Faculty faculty = Faculty.valueOf(row.getCell(2).getStringCellValue());
            String kpiDiploma = checkIfKpiDiplomaIsCorrect(row.getCell(3).getStringCellValue());
            String stateDiploma = checkIfStateDiplomaIsCorrect(row.getCell(4).getStringCellValue());
            String protocolNum = row.getCell(5).getStringCellValue();
            Year kpiDiplomaYear = Year.parse(row.getCell(6).getStringCellValue());
            Year stateDiplomaYear = Year.parse(row.getCell(7).getStringCellValue());

            return new RowDTO(id, name, faculty,
                    kpiDiploma, stateDiploma, protocolNum,
                    kpiDiplomaYear, stateDiplomaYear);
        } catch (Exception e) {
            return RowDTO.EMPTY;
        }
    }

    // TODO
    private String checkIfStateDiplomaIsCorrect(String stateDiploma) {
        return stateDiploma;
    }

    // TODO
    private String checkIfKpiDiplomaIsCorrect(String kpiDiploma) {
        return kpiDiploma;
    }


}
