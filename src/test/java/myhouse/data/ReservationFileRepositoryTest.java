package myhouse.data;

import myhouse.models.Guest;
import myhouse.models.Host;
import myhouse.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    private final String seedDirectory = "./dont-wreck-my-house-data/Test/reservations-seed/";
    private final String testDirectory = "./dont-wreck-my-house-data/Test/reservations-test/";
    private final String seedFilePath = "./dont-wreck-my-house-data/Test/reservations-seed/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    private final String testFilePath = "./dont-wreck-my-house-data/Test/reservations-test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";
    private final String hostFilePath = "./dont-wreck-my-house-data/Test/hosts-test.csv";
    private final String guestFilePath = "./dont-wreck-my-house-data/Test/guests-test.csv";
    private final HostFileRepository hostRepo = new HostFileRepository(hostFilePath);
    private final GuestFileRepository guestRepo = new GuestFileRepository(guestFilePath);

    private ReservationFileRepository repository = new ReservationFileRepository(testDirectory, hostRepo, guestRepo);

    @BeforeEach
    void setUp() throws IOException {
        Files.copy(Paths.get(seedFilePath),
                Paths.get(testFilePath),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAllHosts(){
        List<File> files = repository.createListOfReservations();

        assertEquals(2, files.size());
        assertEquals("2e72f86c-b8fe-4265-b4f1-304dea8762db.csv", files.get(0).getName());
    }

    @Test
    void shouldFindAllReservations() throws DataAccessException {
        List<File> files = repository.createListOfReservations();
        List<Reservation> reservations = repository.findAll(files);

        assertEquals(25, reservations.size());
        assertEquals("2e72f86c-b8fe-4265-b4f1-304dea8762db", reservations.get(0).getHost().getHostId().toString());
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", reservations.get(14).getHost().getHostId().toString());
    }

    @Test
    void shouldFindAllReservationsUnderHost() throws DataAccessException {
        List<File> files = repository.createListOfReservations();
        List<Reservation> reservations = repository.findAll(files);
        List<Reservation> filtered = repository.findReservationsByHost(UUID.fromString(
                "3edda6bc-ab95-49a8-8962-d50b53f84b15"),
                true);

        assertEquals(13, filtered.size());
        assertEquals(10, filtered.get(7).getReservationId());
    }

    @Test
    void shouldNotFindExpiredReservations() throws DataAccessException {
        List<File> files = repository.createListOfReservations();
        List<Reservation> reservations = repository.findAll(files);
        List<Reservation> filtered = repository.findReservationsByHost(UUID.fromString(
                "3edda6bc-ab95-49a8-8962-d50b53f84b15"),
                false);

        assertEquals(12, filtered.size());
        assertEquals(10, filtered.get(6).getReservationId());
    }

    @Test
    void shouldSaveReservation() throws DataAccessException {
        Host host = new Host(UUID.fromString("2e72f86c-b8fe-4265-b4f1-304dea8762db"),
                "Hosty",
                "McHosterson",
                "Hosty@Hosty.biz",
                "cal-lme-plez",
                "I live here",
                "Inthiscity",
                "IO",
                "five#",
                new BigDecimal("450"),
                new BigDecimal("600"));
        Guest guest = new Guest();

        Reservation toSave = new Reservation(
                host,
                1,
                LocalDate.of(2022, 10, 11),
                LocalDate.of(2022, 10, 18),
                guest,
                new BigDecimal("400"),
                false
        );

        repository.saveReservation(toSave);
        List<File> files = repository.createListOfReservations();
        List<Reservation> reservations = repository.findAll(files);


        assertEquals(26, reservations.size());
        assertEquals("2e72f86c-b8fe-4265-b4f1-304dea8762db", reservations.get(0).getHost().getHostId().toString());
        assertEquals("3edda6bc-ab95-49a8-8962-d50b53f84b15", reservations.get(14).getHost().getHostId().toString());
        assertEquals(13, reservations.get(12).getReservationId());
    }

    @Test
    void shouldSaveReservationForNewHost() throws DataAccessException {
        Host host = new Host(UUID.fromString("b4d1c62b-8521-4f2b-b6c7-2b41625d8588"),
                "Hosty2",
                "McHosterson2",
                "2Hosty@Hosty.biz",
                "cal-lme-plez",
                "I live here",
                "Inthiscity",
                "IO",
                "five#",
                new BigDecimal("450"),
                new BigDecimal("600"));
        Guest guest = new Guest();

        Reservation toSave = new Reservation(
                host,
                1,
                LocalDate.of(2022, 10, 11),
                LocalDate.of(2022, 10, 18),
                guest,
                new BigDecimal("400"),
                false
        );

        repository.saveReservation(toSave);
        List<File> files = repository.createListOfReservations();
        List<Reservation> reservations = repository.findAll(files);


        assertEquals(3, files.size());
        assertEquals("b4d1c62b-8521-4f2b-b6c7-2b41625d8588.csv", files.get(2).getName());

        File testFile = new File(testDirectory + "b4d1c62b-8521-4f2b-b6c7-2b41625d8588.csv");
        assertTrue(testFile.delete());
    }

    @Test
    void shouldUpdateReservation() throws DataAccessException {
        List<Reservation> filtered = repository.findReservationsByHost(UUID.fromString(
                        "2e72f86c-b8fe-4265-b4f1-304dea8762db"),
                true);

        LocalDate newDate = LocalDate.of(2022, 10, 12);
        Reservation reservation = filtered.get(0);
        reservation.setStartDate(newDate);

        assertTrue(repository.updateReservation(reservation));

        filtered = repository.findReservationsByHost(UUID.fromString(
                        "2e72f86c-b8fe-4265-b4f1-304dea8762db"),
                true);

        assertEquals(newDate, filtered.get(7).getStartDate());

    }

    @Test
    void shouldDeleteReservation() throws DataAccessException {

    }



}