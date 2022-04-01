package myhouse.data;

import myhouse.models.Guest;
import myhouse.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GuestRepositoryDouble implements GuestRepository {

    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        Guest guest1 = new Guest(
                1,
                "Tara",
                "Wildspring",
                "wildspring@Sketchy.biznet",
                "(123) 4567890",
                "MN");

        Guest guest2 = new Guest(
                2,
                "Tarantino",
                "Wildsprung",
                "wildsprung@Sketchy.biznet",
                "(012) 3456789",
                "WI");

        guests.add(guest1);
        guests.add(guest2);
    }

    @Override
    public List<Guest> findAll() {
        return guests;
    }

    @Override
    public Guest findById(int guestId) throws DataAccessException {
        Guest result = new Guest();

        for (Guest g : findAll()) {
            if (g.getGuestId() == guestId) {
                result = g;
            }
        }

        return result;
    }

    @Override
    public List<Guest> findAllByState(String state) {
        return findAll().stream()
                .filter(a -> a.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }


}



