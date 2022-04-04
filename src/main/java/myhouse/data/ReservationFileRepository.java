package myhouse.data;

import myhouse.models.Guest;
import myhouse.models.Host;
import myhouse.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ReservationFileRepository implements ReservationRepository {

    private final String directoryPath;
    private final HostFileRepository hostRepo;
    private final GuestFileRepository guestRepo;

    public ReservationFileRepository(@Value("${reservationFilePath}") String directoryPath, HostFileRepository hostRepo, GuestFileRepository guestRepo) {
        this.directoryPath = directoryPath;
        this.hostRepo = hostRepo;
        this.guestRepo = guestRepo;
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
            Host host = hostRepo.findHostById(UUID.fromString(fileName));

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine();
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    String[] fields = line.split(",", -1);
                    if (fields.length == 5) {
                        Reservation reservation = new Reservation();
                        reservation.setHost(host);
                        reservation.setReservationId(Integer.parseInt(fields[0]));
                        reservation.setStartDate(LocalDate.parse(fields[1], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        reservation.setEndDate(LocalDate.parse(fields[2], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        Guest guest = guestRepo.findById(Integer.parseInt(fields[3]));
                        reservation.setGuest(guest);
                        BigDecimal total = new BigDecimal(String.valueOf(fields[4])).setScale(2, RoundingMode.HALF_EVEN);
                        reservation.setTotal(total);
                        reservation.setCancelled(false);
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
    public List<Reservation> findReservationsByHost(UUID hostId, boolean displayExpired) throws DataAccessException {
        List<File> files = createListOfReservations();
        List<Reservation> all = findAll(files);
        List<Reservation> result = new ArrayList<>();

        for (Reservation res : all) {
            if (res.getHost().getHostId().toString().equals(hostId.toString())) {
                result.add(res);
            }
        }

        if (!displayExpired) {
            return result.stream()
                    .filter(peep -> peep.getEndDate().isAfter(LocalDate.now()))
                    .sorted(Comparator.comparing(Reservation::getStartDate))
                    .collect(Collectors.toList());
        } else {
            return result.stream()
                    .sorted(Comparator.comparing(Reservation::getStartDate))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public boolean saveReservation(Reservation reservation) throws DataAccessException {

        int id = 0;
        String filePath = "";
        boolean create = false;
        String header = "";

        id = getNextId(findReservationsByHost(reservation.getHost().getHostId(), false));

        List<File> files = createListOfReservations();
        reservation.setReservationId(id);

        for (File f : files) {
            if (f.getName().equals(reservation.getHost().getHostId().toString() + ".csv")) {
                filePath = f.getPath();
            }
        }

        if (filePath.isBlank()) {
            filePath = directoryPath + reservation.getHost().getHostId().toString() + ".csv";
            create = true;
        }

        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            if (create) {
                bw.write("id,start_date,end_date,guest_id,total");
                bw.newLine();
            }
            bw.write(serialize(reservation));
            bw.newLine();
            return true;

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }

        return false;
    }

    @Override
    public boolean updateReservation(Reservation reservation) throws DataAccessException {
        List<Reservation> reservationList = findReservationsByHost(UUID.fromString(reservation.getHost().getHostId().toString()), false);


        for (int i = 0; i < reservationList.size(); i++) {
            if (reservationList.get(i).getReservationId() == reservation.getReservationId()) {
                reservationList.set(i, reservation);
                writeAll(reservationList);
                return true;
            }
        }

        return false;
    }

    private void writeAll(List<Reservation> reservations) throws DataAccessException {
        String filePath = directoryPath + reservations.get(0).getHost().getHostId().toString() + ".csv";

        List<Reservation> printSort = reservations.stream()
                .sorted(Comparator.comparing(Reservation::getReservationId))
                .collect(Collectors.toList());


        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id,start_date,end_date,guest_id,total");
            for (Reservation res : printSort) {
                if(!res.isCancelled()) {
                writer.println(serialize(res));
                }
            }

        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);

        }

    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getReservationId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getGuest().getGuestId(),
                reservation.getTotal()
        );
    }

    private int getNextId(List<Reservation> reservationList) {
        int nextId = 0;
        int[] idValues = new int[reservationList.size()];
        int missingValue = 0;

        for (int i = 0; i < reservationList.size(); i++) {
            idValues[i] = reservationList.get(i).getReservationId();
        }

        nextId = reservationList.size() * (reservationList.size() + 1) / 2;

        for (int i = 0; i < idValues.length; i++) {
            nextId -= idValues[i];
        }

        if (missingValue != 0) {
            return nextId;
        }


        for (Reservation res : reservationList) {
            if (nextId < res.getReservationId()) {
                nextId = res.getReservationId();
            }
        }
        return nextId + 1;
    }
}
