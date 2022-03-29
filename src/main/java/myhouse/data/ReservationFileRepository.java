package myhouse.data;

import myhouse.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReservationFileRepository implements ReservationRepository{

    private final String directoryPath;

    public ReservationFileRepository(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public List<File> createListOfReservations() {
        List<File> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            files = paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException ex) {

        }

        return files;
    }

    @Override
    public List<Reservation> findAll(List<File> files) throws DataAccessException {
        ArrayList<Reservation> result = new ArrayList<>();
        String ext = ".csv";

        for (File file : files) {
            String fileName = "";

            if (file.getName().endsWith(ext)) {
                fileName = file.getName().substring(0, file.getName().length() - ext.length());
        }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine();
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    String[] fields = line.split(",", -1);
                    if (fields.length == 5) {
                        Reservation reservation = new Reservation();
                        reservation.setHostId(UUID.fromString(fileName));
                        reservation.setReservationId(Integer.parseInt(fields[0]));
                        reservation.setStartDate(LocalDate.parse(fields[1], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        reservation.setEndDate(LocalDate.parse(fields[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        reservation.setGuestId(Integer.parseInt(fields[3]));
                        BigDecimal total = new BigDecimal(String.valueOf(fields[4]));
                        reservation.setTotal(total);
                        result.add(reservation);
                    }
                }

            } catch (FileNotFoundException ex) {
                // ignore
            } catch (IOException ex) {
                // do nothing
                throw new DataAccessException(ex.getMessage(), ex);

            }
        }
        return result;
    }

    @Override
    public List<Reservation> findReservationsByHost(int hostId) throws DataAccessException {
        List<Reservation> result = new ArrayList<>();
        List<File> files = createListOfReservations();

        result = findAll(files).stream()
                .filter(f -> f.getHostId().equals(hostId))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public ReservationRepository findById(UUID reservationId) throws DataAccessException {
        return null;
    }

    @Override
    public Reservation makeReservation(Reservation reservation) throws DataAccessException {
        return null;
    }

    @Override
    public boolean updateReservation(Reservation reservation) throws DataAccessException {
        return false;
    }

    @Override
    public boolean deleteById(UUID reservationId) throws DataAccessException {
        return false;
    }

    @Override
    public void saveReservation(Reservation reservation) {

    }
}
