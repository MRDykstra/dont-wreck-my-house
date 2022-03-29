package data;

import myhouse.data.DataAccessException;
import myhouse.data.ReservationFileRepository;
import myhouse.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationFileRepositoryTest {

    private final String testDirectory = "./dont-wreck-my-house-data/Test/reservations-seed/";
    private final String seedFilePath = "./dont-wreck-my-house-data/Test/reservations-seed/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    private final String testFilePath = "./dont-wreck-my-house-data/Test/reservations-test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    private ReservationFileRepository repository = new ReservationFileRepository(testDirectory);

    @BeforeEach
    void setUp() throws IOException {
        Files.copy(Paths.get(seedFilePath),
                Paths.get(testFilePath),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAllHosts(){
        List<File> files = repository.createListOfReservations();

        assertEquals(1, files.size());
        assertEquals("2e72f86c-b8fe-4265-b4f1-304dea8762db.csv", files.get(0).getName());
    }

    @Test
    void shouldFindAllReservations() throws DataAccessException {
        List<File> files = repository.createListOfReservations();
        List<Reservation> reservations = repository.findAll(files);

        assertEquals(12, reservations.size());
        assertEquals("2e72f86c-b8fe-4265-b4f1-304dea8762db", reservations.get(0).getHostId().toString());
    }



}