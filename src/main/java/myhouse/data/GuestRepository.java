package myhouse.data;

import myhouse.models.Guest;

import java.util.List;

public interface GuestRepository {

    // Interface for Guest Repository

    public List<Guest> findAll() throws DataAccessException;

    public Guest findById(int guestId) throws DataAccessException;

    public List<Guest> findAllByState(String state) throws DataAccessException;

}
