package myhouse.data;

import myhouse.models.Reservation;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {

    List<File> createListOfReservations();

    List<Reservation> findReservationsByHost(int hostId) throws DataAccessException;

    ReservationRepository findById(UUID reservationId) throws DataAccessException;

    Reservation makeReservation(Reservation reservation) throws DataAccessException;

    boolean updateReservation(Reservation reservation) throws DataAccessException;

    boolean deleteById(UUID reservationId) throws DataAccessException;

    void saveReservation(Reservation reservation);


    List<Reservation> findAll(List<File> files) throws DataAccessException;
}
