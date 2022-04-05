package myhouse.data;

import myhouse.models.Reservation;

import java.io.File;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {

    // Interface for the Reservation Repository

    List<File> createListOfReservations();

    List<Reservation> findReservationsByHost(UUID hostId, boolean current) throws DataAccessException;

    boolean saveReservation(Reservation reservation) throws DataAccessException;

    boolean updateReservation(Reservation reservation) throws DataAccessException;

    List<Reservation> findAll(List<File> files) throws DataAccessException;
}
