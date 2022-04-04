package myhouse.data;

import myhouse.models.Guest;
import myhouse.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class GuestFileRepository implements GuestRepository {

    private final String filePath;

    public GuestFileRepository(@Value("${guestFilePath}") String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() throws DataAccessException {
        List<Guest> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 6) {
                    Guest guest = new Guest();
                    guest.setGuestId(Integer.parseInt(fields[0]));
                    guest.setFirstName(fields[1]);
                    guest.setLastName(fields[2]);
                    guest.setEmail(fields[3]);
                    guest.setPhone(fields[4]);
                    guest.setState(fields[5]);

                    result.add(guest);
                }
            }

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
        return result;
    }

    @Override
    public Guest findById(int guestId) throws DataAccessException {

        List<Guest> guestList = findAll();
        Guest result = new Guest();

        for (Guest g : guestList) {
            if (g.getGuestId() == guestId) {
                result = g;
            }
        }

        return result;
    }

    @Override
    public List<Guest> findAllByState(String state) throws DataAccessException {
        return findAll().stream()
                .filter(a -> a.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }

}
