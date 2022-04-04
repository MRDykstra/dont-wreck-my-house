package myhouse.domain;

import myhouse.data.DataAccessException;
import myhouse.data.ReservationRepository;
import myhouse.models.Host;
import myhouse.models.Reservation;
import myhouse.models.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    // Reservation Service

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

    public Result validateReservationDates(LocalDate start, LocalDate end, Host host, int reservationId) throws DataAccessException {
        Result result = new Result();
        boolean update = false;
        int index = 0;
        List<Reservation> reservationList = findAllByHost(host.getHostId(), false);


        if (end.isBefore(start) || end.isEqual(start)) {
            result.addErrorMessage("End date must come after start date.");
            return result;
        }

        for (int i = 0; i < reservationList.size(); i++) {
            if (reservationList.get(i).getReservationId() == reservationId) {
                update = true;
                index = i;
            }
        }

        if (update) {
            reservationList.remove(index);
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

    public Result updateReservation(Reservation res) throws DataAccessException {
        Result result = new Result();

        if (!repo.updateReservation(res)) {
            result.addErrorMessage("Could not update reservation. PLease try again.");
        }

        return result;
    }
}
