package com.inc.tarik.project99.repository;

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

    private File TEST_FILE = new File("test_db.xls");

    private Repository repository;

    private Database database;

    private List<RowDTO> data;

    @BeforeEach
    void setUp() {
        database = new Database();
        repository = new Repository(database);
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
        repository.exportToXLS(TEST_FILE);
        repository.importFromXLS(TEST_FILE);
        assertEquals(data, database.stream().toList());
    }

    @Test
    void exportToXlsx() {
        repository.exportToXLS(TEST_FILE);
    }
}