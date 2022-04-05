package myhouse.data;

import myhouse.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class HostFileRepository implements HostRepository {

    // Host Repository

    private final String filePath;

    public HostFileRepository(@Value("${hostFilePath}") String filePath) {
        this.filePath = filePath;
    }

    public List<Host> findAll() throws DataAccessException {
        List<Host> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 10) {
                    Host host = new Host();
                    host.setHostId(UUID.fromString(fields[0]));
                    host.setLastName(fields[1]);
                    host.setEmail(fields[2]);
                    host.setPhone(fields[3]);
                    host.setAddress(fields[4]);
                    host.setCity(fields[5]);
                    host.setState(fields[6]);
                    host.setPostalCode(fields[7]);
                    host.setStandardRate(new BigDecimal(String.valueOf(fields[8])));
                    host.setWeekendRate(new BigDecimal(String.valueOf(fields[9])));
                    result.add(host);
                }
            }

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
        return result;
    }

    public List<Host> findAllByState(String state) throws DataAccessException {
        return findAll().stream()
                .filter(a -> a.getState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }

    public Host findHostById(UUID hostId) throws DataAccessException {
        Host result = new Host();

        for (Host h : findAll()) {
            if (h.getHostId().toString().equals(hostId.toString())) {
                result = h;
            }
        }

        return result;
    }


}
