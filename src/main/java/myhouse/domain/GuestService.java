package myhouse.domain;

import myhouse.data.DataAccessException;
import myhouse.data.GuestRepository;
import myhouse.models.Guest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuestService {

    // Guest Service

    private final GuestRepository repo;

    public GuestService(GuestRepository repo) {
        this.repo = repo;
    }

    public List<Guest> findAll() throws DataAccessException {
        return repo.findAll();
    }

    public Guest findById(int guestId) throws DataAccessException {

        return repo.findById(guestId);
    }

    public List<Guest> findAllByState(String state) throws DataAccessException {
        return repo.findAllByState(state);
    }


}
