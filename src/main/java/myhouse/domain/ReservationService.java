package myhouse.domain;

import myhouse.data.DataAccessException;
import myhouse.data.ReservationFileRepository;
import myhouse.data.ReservationRepository;
import myhouse.models.Host;
import myhouse.models.Reservation;
import myhouse.models.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository repo;

    public ReservationService(ReservationRepository repo) {
        this.repo = repo;
    }

    public List<Reservation> findAllReservations() throws DataAccessException {
        return repo.findAll(repo.createListOfReservations());
    }

    public List<Reservation> findAllByHost(UUID hostId, boolean displayExpired) throws DataAccessException {

        return repo.findReservationsByHost(hostId, displayExpired);
    }

    public Result validateReservation(Reservation reservation) {
        Result result = new Result();

        return result;
    }

    public Result validateReservationDates(LocalDate start, LocalDate end, Host host) throws DataAccessException {
        Result result = new Result();
        List<Reservation> reservationList = findAllByHost(host.getHostId(), false);

        if (start.isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date cannot be in the past.");
            return result;
        }

        if (end.isBefore(start) || end.isEqual(start)) {
            result.addErrorMessage("End date must come after start date.");
            return result;
        }

        if (reservationList.size() != 0) {
            for (Reservation res : reservationList) {

                if (start.isBefore(res.getStartDate()) && end.isAfter(res.getStartDate())) {
                    result.addErrorMessage("End date overlaps an existing reservation.");
                }

                if (start.isBefore(res.getEndDate()) && end.isAfter(res.getEndDate()) || start.isEqual(res.getStartDate())) {
                    result.addErrorMessage("Start date overlaps an existing reservation.");
                }

                if (start.isAfter(res.getStartDate()) && (end.isBefore(res.getEndDate()) || end.isEqual(res.getEndDate()))) {
                    result.addErrorMessage("Reservation overlaps existing reservation");
                }

                if (!result.isSuccessful()) {
                    return result;
                }

            }
        }

        return result;
    }

    public Result validateStartDate(LocalDate start) {
        Result result = new Result();
        if (start.isBefore(LocalDate.now())) {
            result.addErrorMessage("Start date cannot be in the past.");
        }

        return result;
    }

    public Result saveReservation(Reservation res) throws DataAccessException {
        Result result = new Result();

        if (!repo.saveReservation(res)) {
            result.addErrorMessage("Could not save reservation. PLease try again.");
        }

        return result;
    }

}
