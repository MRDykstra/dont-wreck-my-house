package myhouse.data;

import myhouse.data.DataAccessException;
import myhouse.data.ReservationRepository;
import myhouse.models.Guest;
import myhouse.models.Host;
import myhouse.models.Reservation;
import myhouse.models.Result;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    private final ArrayList<Reservation> res = new ArrayList<>();

    public ReservationRepositoryDouble() {

        Guest guest = new Guest(1,
                "Doran",
                "Fred",
                "twoFirstNames@weirdpeople.net",
                "800-192-3121",
                "FL");

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

        Host host2 = new Host(UUID.fromString("a0d911e7-4fde-4e4a-bdb7-f047f15615e8"),
                "Hostier",
                "McHostierson",
                "Hostier@Hostier.biz",
                "cal-lme-ples",
                "I live here 2",
                "Inthiscity",
                "IO",
                "five#",
                new BigDecimal("445"),
                new BigDecimal("595"));

        Reservation one1 = new Reservation(
                host,
                1,
                LocalDate.of(2022, 7, 30),
                LocalDate.of(2022, 8, 6),
                guest,
                new BigDecimal("2550"),
                false);
        Reservation one2 = new Reservation(
                host,
                2,
                LocalDate.of(2022, 7, 30),
                LocalDate.of(2022, 8, 6),
                guest,
                new BigDecimal("2550"),
                false);
        Reservation two1 = new Reservation(
                host2,
                1,
                LocalDate.of(2022, 7, 30),
                LocalDate.of(2022, 8, 6),
                guest,
                new BigDecimal("2550"),
                false);
        Reservation two2 = new Reservation(
                host2,
                2,
                LocalDate.of(2022, 7, 30),
                LocalDate.of(2022, 8, 6),
                guest,
                new BigDecimal("2550"),
                false);
        res.add(one1);
        res.add(one2);
        res.add(two1);
        res.add(two2);
    }

    @Override
    public List<File> createListOfReservations() {
        return null;
    }

    @Override
    public List<Reservation> findAll(List<File> files) throws DataAccessException {
        return res;
    }

    @Override
    public List<Reservation> findReservationsByHost(UUID hostId, boolean current) throws DataAccessException {
        return res.stream()
                .filter(a -> a.getHost().getHostId().toString().equals(hostId.toString()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateReservation(Reservation reservation) throws DataAccessException {
        return false;
    }

    @Override
    public boolean saveReservation(Reservation reservation) {
        res.add(reservation);

        return true;
    }

}
