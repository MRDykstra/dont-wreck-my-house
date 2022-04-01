package myhouse.data;

import myhouse.models.Guest;
import myhouse.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestFileRepositoryTest {

    private final String seedFilePath = "./dont-wreck-my-house-data/Test/guests-seed.csv";
    private final String testFilePath = "./dont-wreck-my-house-data/Test/guests-test.csv";
    private GuestFileRepository repo = new GuestFileRepository(testFilePath);

    @BeforeEach
    void setUp() throws IOException {
        Files.copy(Paths.get(seedFilePath),
                Paths.get(testFilePath),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAllGuests() throws DataAccessException {
        List<Guest> all = repo.findAll();

        assertEquals(1000, all.size());
        assertEquals("DC", all.get(1).getState());
    }

    @Test
    void shouldFindById() throws DataAccessException {
        Guest guest = repo.findById(2);

        assertEquals("Olympie", guest.getFirstName());


    }

    @Test
    void shouldFindGuestsByState() throws DataAccessException {
        List<Guest> guests = repo.findAllByState("TX");

        assertEquals(107, guests.size());
        assertEquals("Dodimead", guests.get(0).getLastName());

        List<Guest> guestsMN = repo.findAllByState("mn");

        assertEquals(24, guestsMN.size());
        assertEquals("MN", guestsMN.get(0).getState());
        assertEquals("Warland", guestsMN.get(0).getLastName());
    }

}
