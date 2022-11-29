package com.inc.tarik.project99.repository;

import com.inc.tarik.project99.config.ApplicationConfig;
import com.inc.tarik.project99.database.Database;
import com.inc.tarik.project99.dto.Faculty;
import com.inc.tarik.project99.dto.RowDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoryTest {

    private final File TEST_FILE_XLS = new File("test_db.xls");

    private final File TEST_FILE_CSV = new File("test_db.csv");

    private Repository repository;

    private Database database;

    private List<RowDTO> data;

    @BeforeEach
    void setUp() {
        database = new Database();
        ApplicationConfig config = new ApplicationConfig();
        repository = new Repository(database, config.getCsvMapper(), config.getCsvSchema());
        data = List.of(
                new RowDTO(1, "1", Faculty.FL,
                        "1", "1", "1",
                        Year.of(2022), Year.of(2022)),
                new RowDTO(2, "2", Faculty.FL,
                        "2", "2", "2",
                        Year.of(2022), Year.of(2022))
        );
        database.importAll(data);

    }

    @Test
    void importFromXlsx() {
        repository.exportToXLS(TEST_FILE_XLS);
        repository.importFromXLS(TEST_FILE_XLS);
        assertEquals(data, database.stream().toList());
    }

    @Test
    void exportToXlsx() {
        repository.exportToXLS(TEST_FILE_XLS);
    }

    @Test
    void exportToCSV() {
        repository.exportToCSV(TEST_FILE_CSV);
    }

    @Test
    void importFromCSV() {
        repository.exportToCSV(TEST_FILE_CSV);
        repository.importFromCSV(TEST_FILE_CSV);
        assertEquals(data, database.stream().toList());
    }
}