package myhouse.domain;

import myhouse.data.DataAccessException;
import myhouse.data.GuestRepositoryDouble;
import myhouse.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



public class GuestServiceTest {

    GuestService service;

    @BeforeEach
    void init() {
        service = new GuestService(new GuestRepositoryDouble());
    }

    @Test
    void shouldFindAllGuests() throws DataAccessException {
        List<Guest> guests = service.findAll();

        assertEquals(2, guests.size());
    }

    @Test
    void shouldFindAllByState() throws DataAccessException {
        List<Guest> guests = service.findAllByState("wi");

        assertEquals(1, guests.size());
        assertEquals("Wildsprung", guests.get(0).getLastName());
    }

    @Test
    void shouldFindGuestById() throws DataAccessException {
        Guest guest = service.findById(2);

        assertNotNull(guest);
        assertEquals("Wildsprung", guest.getLastName());
    }

}
