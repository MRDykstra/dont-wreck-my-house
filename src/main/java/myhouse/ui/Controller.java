package myhouse.ui;

import myhouse.data.DataAccessException;
import myhouse.domain.GuestService;
import myhouse.domain.HostService;
import myhouse.domain.ReservationService;
import myhouse.models.Guest;
import myhouse.models.Host;
import myhouse.models.Reservation;
import myhouse.models.Result;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class Controller {

    private final View view;
    private final ReservationService reservationService;
    private final HostService hostService;
    private final GuestService guestService;

    public Controller(View view, ReservationService reservationService, HostService hostService, GuestService guestService) {
        this.view = view;
        this.reservationService = reservationService;
        this.hostService = hostService;
        this.guestService = guestService;
    }

    public void mainMenu() throws DataAccessException {
        MenuOptions option;
        do {
            option = view.mainMenu();
            switch (option) {
                case VIEW_RESERVATIONS:
                    viewReservationsByState(false);
                    view.enterToContinue();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    view.displayHeader("Doesn't Exist");
                    break;
                case CANCEL_RESERVATION:
                    view.displayHeader("Doesn't Exist");
                    break;
            }
        } while (option != MenuOptions.EXIT);
    }

    public List<Reservation> viewReservationsByState(boolean displayCancelled) throws DataAccessException {

        boolean loop = true;
        while (loop) {
            String state = view.chooseState();
            List<Host> hostList = hostService.findHostsByState(state);

            if (hostList == null || hostList.isEmpty()) {
                view.printLine("There are no hosts in that state currently");
                if (!view.yesOrNo("Would you like to check another state? [y/n]: ")) {
                    break;
                } else {
                    continue;
                }
            }

            String hostId = view.chooseHost(hostList);

            if (hostId == null || hostId.isBlank()) {
                return null;
            }

            List<Reservation> reservations = reservationService.findAllByHost(UUID.fromString(hostId), true);

            if (reservations.size() == 0) {
                view.printLine("That host has no reservations currently.");
            } else {
                view.displayReservationsByHost(reservations, displayCancelled);
                return reservations;
            }

            if (!view.yesOrNo("Select another host? [y/n]: ")) {
                loop = false;
                return reservations;
            }
        }

        return null;
    }

    public void makeReservation() throws DataAccessException {
        Result result = new Result();

        List<Reservation> reservationList = new ArrayList<>();
        Reservation toSave = new Reservation();

        LocalDate startDate = null;
        LocalDate endDate = null;
        BigDecimal total = BigDecimal.ZERO;

        boolean makingReservation = true;
        boolean loop = true;
        Host host = new Host();
        Guest guest = new Guest();
        String state = "";

        while (makingReservation) {

            guest = getGuest();

            if (guest.getFirstName() == null) {
                makingReservation = false;
                break;
            }

            toSave.setGuest(guest);
            state = guest.getState();

            if (!view.yesOrNo("Choose a host from the same state? [y/n]: ")) {
                state = view.chooseState();
            }

            host = getHost(state);

            if (host.getHostId() == null) {
                makingReservation = false;
                break;
            }

            toSave.setHost(host);

            loop = true;
            reservationList = reservationService.findAllByHost(host.getHostId(), false);

            while (loop) {
                if (reservationList.size() == 0) {
                    view.printLine("Host has no current reservations.");
                } else {
                    view.displayReservationsByHost(reservationList, false);
                }

                boolean choosingStart = true;

                while (choosingStart) {
                    startDate = view.chooseDate("Enter reservation start date [MM/DD/YYYY]: ");
                    result = reservationService.validateStartDate(startDate);
                    if (!result.isSuccessful()) {
                        continue;
                    }
                    choosingStart = false;
                }

                endDate = view.chooseDate("Enter reservation end date [MM/DD/YYYY]: ");

                result = reservationService.validateReservationDates(startDate, endDate, host);
                if (!result.isSuccessful()) {
                    view.displayErrorMessages(result);
                    continue;
                }

                loop = false;
            }

            toSave.setStartDate(startDate);
            toSave.setEndDate(endDate);

            total = view.displayMakeReservationConfirmation(toSave);

            if (!view.yesOrNo("Is that correct? [y/n]: ")) {
                if (!view.yesOrNo("Make another reservation? [y/n]: ")) {
                    break;
                }
                loop = true;
                continue;
            }

            toSave.setTotal(total);

            makingReservation = false;
            result = reservationService.saveReservation(toSave);
            if (!result.isSuccessful()) {
                view.displayErrorMessages(result);
                break;
            }

            reservationList = reservationService.findAllByHost(host.getHostId(), false);

            if (reservationList.size() == 0) {
                view.displayReservation(toSave, false);
                view.enterToContinue();
                break;
            }

            view.displayReservationsByHost(reservationList, false);
            view.enterToContinue();

        }
    }

    public void cancelReservation() throws DataAccessException {
        List<Reservation> reservations = viewReservationsByState(false);
        if (reservations == null || reservations.isEmpty()) {
            return;
        }


    }

    public void updateReservation() throws DataAccessException {
        LocalDate startDate = null;
        LocalDate endDate = null;
        BigDecimal total;
        Result result = new Result();
        boolean loop = true;
        boolean updating = true;

        while (updating) {
            List<Reservation> reservationList = viewReservationsByState(true);

            Reservation reservation = view.chooseReservation(reservationList);

            if (reservation == null) {
                return;
            }

            Reservation toUpdate = reservation;

            Host host = hostService.findHostById(reservation.getHost().getHostId());

            if (host == null) {
                return;
            }


            view.displayReservation(reservation, true);

            while (loop) {

                boolean choosingStart = true;

                while (choosingStart) {
                    startDate = view.chooseDate("Enter reservation start date [MM/DD/YYYY]: ");
                    result = reservationService.validateStartDate(startDate);
                    if (!result.isSuccessful()) {
                        continue;
                    }
                    choosingStart = false;
                }

                endDate = view.chooseDate("Enter reservation end date [MM/DD/YYYY]: ");

                result = reservationService.validateReservationDates(startDate, endDate, host);
                if (!result.isSuccessful()) {
                    view.displayErrorMessages(result);
                    continue;
                }

                loop = false;
            }

            toUpdate.setStartDate(startDate);
            toUpdate.setEndDate(endDate);

            view.displayReservation(reservation, true);
            view.displayReservation(toUpdate, true);
            if (!view.yesOrNo("Is this correct? [y/n]: ")) {
                if (!view.yesOrNo("Edit another reservation? [y/n]: ")) {
                    updating = false;
                    return;
                } else {
                    continue;
                }
            }

            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.setCancelled(false);




        }
    }

    public Guest getGuest() throws DataAccessException {
        boolean loop = true;
        String state = "";
        Guest guest = new Guest();

        while (loop) {
            view.displayHeader("Guest Selection");
            state = view.chooseState();
            List<Guest> guestList = guestService.findAllByState(state);
            int guestId = view.chooseGuest(guestList);
            if (guestId == 0) {
                loop = false;
                break;
            }
            guest = guestService.findById(guestId);

            view.displayGuest(guest, 28);
            if (!view.yesOrNo("Continue with this guest? [y/n]: ")) {
                continue;
            }

            loop = false;
        }

        return guest;
    }

    public Host getHost(String state) throws DataAccessException {
        boolean loop = true;
        Host host = new Host();

        while (loop) {
            view.displayHeader("Host Selection");
            List<Host> hostList = hostService.findHostsByState(state);
            String hostId = view.chooseHost(hostList);
            if (hostId == null) {
                loop = false;
                return host;
            }

            host = hostService.findHostById(UUID.fromString(hostId));

            view.displayHost(host, 14, true);
            if (!view.yesOrNo("Continue with this host? [y/n]: ")) {
                continue;
            }

            loop = false;
        }

        return host;
    }

}
