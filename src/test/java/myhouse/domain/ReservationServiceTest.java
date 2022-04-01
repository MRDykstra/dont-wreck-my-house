package myhouse.domain;

import myhouse.data.DataAccessException;
import myhouse.data.ReservationRepositoryDouble;
import myhouse.models.Guest;
import myhouse.models.Host;
import myhouse.models.Reservation;
import myhouse.models.Result;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class ReservationServiceTest {

    ReservationService service;

    @BeforeEach
    void init() {

            service = new ReservationService(new ReservationRepositoryDouble());

    }

    @Test
    void shouldFindAllReservations() throws DataAccessException {
        List<Reservation> all = service.findAllReservations();

        assertEquals(4, all.size());
        assertEquals(new BigDecimal("2550"), all.get(3).getTotal());
    }

    @Test
    void shouldFindAllReservationsForGivenHost() throws DataAccessException {
        List<Reservation> byHost = service.findAllByHost(UUID.fromString("3edda6bc-ab95-49a8-8962-d50b53f84b15"), false);

        assertEquals(2, byHost.size());
        assertEquals(1, byHost.get(0).getReservationId());
        assertEquals(2, byHost.get(1).getReservationId());
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
                3,
                LocalDate.of(2022, 10, 11),
                LocalDate.of(2022, 10, 18),
                guest,
                new BigDecimal("400"),
                false
        );

        service.saveReservation(toSave);
        List<Reservation> all = service.findAllReservations();


        assertEquals(5, all.size());
        assertEquals(3, all.get(4).getReservationId());
    }

    @Test
    void shouldNotValidateInvalidDates() throws DataAccessException {
        Result result;

        Host host = new Host(UUID.fromString("3edda6bc-ab95-49a8-8962-d50b53f84b15"),
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

        // Same Dates
       LocalDate start = LocalDate.of(2022, 7, 30);
       LocalDate end = LocalDate.of(2022, 8, 6);

        result = service.validateReservationDates(start, end, host);

        assertFalse(result.isSuccessful());

        // Start Date is After, End Date is the same
        start = LocalDate.of(2022, 8, 2);
        end = LocalDate.of(2022, 8, 6);

        result = service.validateReservationDates(start, end, host);

        assertFalse(result.isSuccessful());

        // Start Date is After, End Date is before
        start = LocalDate.of(2022, 8, 2);
        end = LocalDate.of(2022, 8, 5);

        result = service.validateReservationDates(start, end, host);

        assertFalse(result.isSuccessful());

        // Start Date is After, End Date is After
        start = LocalDate.of(2022, 8, 2);
        end = LocalDate.of(2022, 8, 7);

        result = service.validateReservationDates(start, end, host);

        assertFalse(result.isSuccessful());

        // Start Date is Before, End Date is After
        start = LocalDate.of(2022, 7, 29);
        end = LocalDate.of(2022, 8, 2);

        result = service.validateReservationDates(start, end, host);

        assertFalse(result.isSuccessful());

        // Start Date is Before, End Date is the same
        start = LocalDate.of(2022, 7, 29);
        end = LocalDate.of(2022, 8, 6);

        result = service.validateReservationDates(start, end, host);

        assertFalse(result.isSuccessful());
    }



}
