package myhouse.ui;

import myhouse.models.Guest;
import myhouse.models.Host;
import myhouse.models.Reservation;
import myhouse.models.Result;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MenuOptions mainMenu() {
        displayHeader("Main Menu");
        MenuOptions result = MenuOptions.EXIT;

        for (MenuOptions option : MenuOptions.values()) {
            io.printf("%s. %s%n", option.getIndex(), option.getName());
        }

        int choice = io.readInt("Select 0-4: ", 0, 4);

        for (MenuOptions option : MenuOptions.values()) {
            if (choice == option.getIndex()) {
                result = option;
            }
        }
        return result;
    }

    public String chooseState() {
        String stateAbbreviation = "";

        while (!stateAbbreviation.trim().equalsIgnoreCase("0")) {
            stateAbbreviation = io.readRequiredString("What state would you like to view? (Two letter state abbreviation or 0 to exit): ");
            if (stateAbbreviation.charAt(0) == '0') {
                break;
            }
            if (stateAbbreviation.length() != 2) {
                io.println("Enter the two-digit abbreviation for the chosen state");
            }
            if (stateAbbreviation.isBlank()) {
                io.println("Must Enter a state initial or 0 to exit");
            }
            if (stateAbbreviation.length() == 2) {
                return stateAbbreviation;
            }
        }
        return stateAbbreviation;
    }

    public String chooseHost(List<Host> hosts) {
        int choice = 0;
        int skip = 0;
        int loopCount = 0;
        boolean loop = true;
        String hostId = "";

        while (loop) {
            List<Host> display = new ArrayList<>();
            display = hosts.stream()
                    .skip(skip)
                    .limit(25)
                    .collect(Collectors.toList());

            int size = displayHosts(display, 14, false);


            if (loopCount > 0) {
                if (display.size() == 25) {
                    io.println("Viewing next 25, Enter 0 to exit or view next group.");
                    choice = io.readInt("Select Host by their index: ", 0, Math.min(25, size));
                } else {
                    io.println("0: Exit");
                    choice = io.readInt("Select Host by their index: ", 0, Math.min(25, size));
                }
            } else {
                if (display.size() == 25) {
                    io.println("Viewing first 25, Enter 0 to exit or view next group.");
                    choice = io.readInt("Select Host by their index: ", 0, Math.min(25, size));
                } else {
                    io.println("0: Exit");
                    choice = io.readInt("Select Host by their index: ", 0, Math.min(25, size));
                }
            }

            if (choice == 0 && display.size() == 25) {
                if (io.readBoolean("Would you like to view the next group? [y/n]: ")) {
                    skip += 25;
                } else {
                    loop = false;
                }
            } else if (display.size() <= 25 && choice != 0) {
                loop = false;
                Host host = display.get(choice - 1);
                hostId = host.getHostId().toString();
                return hostId;
            } else {
                return null;
            }
            loopCount++;
        }

        return null;
    }

    public int chooseGuest(List<Guest> guests) {
        int choice = 0;
        int skip = 0;
        int loopCount = 0;
        int guestId = 0;
        boolean loop = true;

        while (loop) {
            List<Guest> display = new ArrayList<>();
            display = guests.stream()
                    .skip(skip)
                    .limit(25)
                    .collect(Collectors.toList());

            int size = displayGuests(display, 28);

            if (loopCount > 0) {
                if (display.size() == 25) {
                    io.println("Viewing next 25, Enter 0 to exit or view next group.");
                    choice = io.readInt("Select Guest by their index: ", 0, Math.min(25, size));
                } else {
                    io.println("0: Exit");
                    choice = io.readInt("Select Guest by their index: ", 0, Math.min(25, size));
                }
            } else {
                if (display.size() == 25) {
                    io.println("Viewing first 25, Enter 0 to exit or view next group.");
                    choice = io.readInt("Select Guest by their index: ", 0, Math.min(25, size));
                } else {
                    io.println("0: Exit");
                    choice = io.readInt("Select Guest by their index: ", 0, Math.min(25, size));
                }
            }

            if (choice == 0 && display.size() == 25) {
                if (io.readBoolean("Would you like to view the next group? [y/n]: ")) {
                    skip += 25;
                } else {
                    loop = false;
                }
            } else if (display.size() <= 25 && choice != 0) {
                loop = false;
                Guest guest = display.get(choice - 1);
                guestId = guest.getGuestId();
                return guestId;
            } else {
                return 0;
            }
            loopCount++;
        }

        return 0;
    }

    public Reservation chooseReservation(List<Reservation> reservations) {
        int index = 0;
        index = io.readInt("Enter reservation id or 0 to exit: ");

        if (index == 0) {
            return null;
        }

        for (Reservation res : reservations) {
            if (res.getReservationId() == index) {
                return res;
            }
        }

        return null;
    }

    public boolean yesOrNo(String message) {
        return io.readBoolean(message);
    }

    public String formatString(String input, int length) {
        StringBuilder constructor = new StringBuilder(input);
        for (int i = 0; constructor.length() < length; i++) {
            constructor.append(" ");
        }

        return constructor.toString();

    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public LocalDate chooseDate(String prompt) {
        return io.readLocalDate(prompt);
    }

    /**
     * Display Methods
     */

    public void printLine(String message) {
        io.println(message);
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayHost(Host host, int nameDisplayLength, boolean displayRate) {

        io.print(hostDisplayFormat(host, nameDisplayLength, displayRate));
    }

    public int displayHosts(List<Host> hosts, int nameDisplayLength, boolean rate) {
        int index = 1;
        displayHeader("Hosts");

        for (Host h : hosts) {

            String host = hostDisplayFormat(h, nameDisplayLength, rate);
            String display = String.format("%s.\t%s", index, host);

            io.print(display);

            index++;

        }

        return index;
    }

    public String hostDisplayFormat(Host host, int nameDisplayLength, boolean displayRate) {
        String result = "";
        String emailDisplay = "";
        String locationDisplay = "";
        String nameDisplay = "";
        String cityDisplay = "";

        nameDisplay = host.getLastName();
        nameDisplay = formatString(nameDisplay, nameDisplayLength);

        emailDisplay = "Email: " + host.getEmail();
        emailDisplay = formatString(emailDisplay, 40);

        cityDisplay = String.format("%s", host.getCity());
        cityDisplay = formatString(cityDisplay, 17);
        locationDisplay = String.format("%s %s", cityDisplay, host.getState());

        if (displayRate) {
            result = String.format(nameDisplay + "| " + emailDisplay + "| Phone: %s | " + locationDisplay + " | Standard Rate / Weekend Rate: $%s / $%s%n",
                    host.getPhone(),
                    host.getStandardRate(), host.getWeekendRate());
        } else {
            result = String.format(nameDisplay + "| " + emailDisplay + "| Phone: %s | " + locationDisplay + "%n",
                    host.getPhone());
        }

        return result;
    }

    public void displayGuest(Guest guest, int nameDisplayLength) {

        io.print(guestDisplayFormat(guest, nameDisplayLength));
    }

    public int displayGuests(List<Guest> guests, int nameDisplayLength) {
        int index = 1;
        displayHeader("Guests");

        for (Guest g : guests) {

            String guest = guestDisplayFormat(g, nameDisplayLength);
            String display = String.format("%s.\t%s", index, guest);

            io.print(display);

            index++;
        }

        return index;
    }

    public String guestDisplayFormat(Guest guest, int nameDisplayLength) {
        String result = "";
        String emailDisplay = "";
        String nameDisplay = "";

        nameDisplay = String.format("%s, %s", guest.getLastName(), guest.getFirstName());
        nameDisplay = formatString(nameDisplay, nameDisplayLength);

        emailDisplay = "Email: " + guest.getEmail();
        emailDisplay = formatString(emailDisplay, 40);

        result = String.format(nameDisplay + "| " + emailDisplay + "| Phone: %s | %s%n",
                guest.getPhone(),
                guest.getState());

        return result;
    }

    public void displayReservationsByHost(List<Reservation> reservations) {
        displayHeader("Reservations for Host");
        io.printf("Host Email: %s%n", reservations.get(0).getHost().getEmail());
        displayHeader(String.format("%s | %s, %s",
                reservations.get(0).getHost().getLastName(),
                reservations.get(0).getHost().getCity(),
                reservations.get(0).getHost().getState()
        ));

        for (Reservation res : reservations) {
            displayReservation(res);
        }

    }

    public void displayReservation(Reservation reservation) {
        io.print(reservationDisplayFormat(reservation));
    }

    public String reservationDisplayFormat(Reservation res) {
        String result = "";
        String idDisplay = "";
        String nameDisplay = "";
        String totalDisplay = "";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String startDay = res.getStartDate().getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH);
        String endDay = res.getEndDate().getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH);

        idDisplay = String.format("ID: %s.  ", res.getReservationId());
        idDisplay = formatString(idDisplay, 10);

        nameDisplay = String.format("Guest: %s, %s",
                res.getGuest().getLastName(),
                res.getGuest().getFirstName());

        nameDisplay = formatString(nameDisplay, 28);

        totalDisplay = String.format("Total Cost: $%s", res.getTotal());
        totalDisplay = formatString(totalDisplay, 22);

        result = String.format(idDisplay + nameDisplay + "| DATES: %s, %s - %s, %s  " + totalDisplay + "| GUEST EMAIL: %s%n",
                startDay,
                res.getStartDate().format(dateFormat),
                endDay,
                res.getEndDate().format(dateFormat),
                res.getGuest().getEmail());

        return result;
    }

    public void displayReservationDates(Reservation reservation) {

        String startDay = reservation.getStartDate().getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH);
        String endDay = reservation.getEndDate().getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH);
        String display = "";

        display = String.format("Stay Dates: %s, %s - %s, %s%n", startDay, reservation.getStartDate(), endDay, reservation.getEndDate());

        io.print(display);
        io.println("");
    }

    public void displayStayCost(Reservation reservation) {
        int weekdayNights = 0;
        int weekendNights = 0;
        long stayLength = 0;
        String totalString = "";
        LocalDate start = reservation.getStartDate();
        LocalDate end = reservation.getEndDate();
        BigDecimal weekendTotal = BigDecimal.ZERO;
        BigDecimal weekdayTotal = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        List<LocalDate> stayDays = new ArrayList<>();

        stayLength = DAYS.between(start, end);

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();

            if (day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY) {
                weekendTotal = weekendTotal.add(reservation.getHost().getWeekendRate());
                weekendNights++;
            } else {
                weekdayTotal = weekdayTotal.add(reservation.getHost().getStandardRate());
                weekdayNights++;
            }
            stayDays.add(date);
        }

        DayOfWeek firstDay = stayDays.get(0).getDayOfWeek();
        total = total.add(weekdayTotal);
        total = total.add(weekendTotal);

        if (firstDay == DayOfWeek.FRIDAY || firstDay == DayOfWeek.SATURDAY) {
            io.printf("%s Weekend Nights @ $%s = %s%n", weekendNights, reservation.getHost().getWeekendRate(), weekendTotal);
            io.printf("%s Weekday Nights @ $%s = %s%n", weekdayNights, reservation.getHost().getStandardRate(), weekdayTotal);
            totalString = String.format("TOTAL: %s Nights for $%s", stayLength, total);

            displayHeader(totalString);
        } else {
            io.printf("%s Weekday Nights @ $%s = %s%n", weekdayNights, reservation.getHost().getStandardRate(), weekdayTotal);
            io.printf("%s Weekend Nights @ $%s = %s%n", weekendNights, reservation.getHost().getWeekendRate(), weekendTotal);
            totalString = String.format("TOTAL: %s Nights for $%s", stayLength, total);

            displayHeader(totalString);
        }
    }

    public BigDecimal calculateStayCost(Reservation reservation) {

        LocalDate start = reservation.getStartDate();
        LocalDate end = reservation.getEndDate();

        BigDecimal total = BigDecimal.ZERO;

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();

            if (day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY) {
                total = total.add(reservation.getHost().getWeekendRate());

            } else {
                total = total.add(reservation.getHost().getStandardRate());

            }
        }

        return total;
    }

    public void displayMakeReservationConfirmation(Reservation res) {

        String header = String.format("\tReservation Confirmation\t");
        displayHeader(header);
        io.print("Guest: ");
        displayGuest(res.getGuest(), 27);

        io.print("Host:  ");
        displayHost(res.getHost(), 27, true);

        displayReservationDates(res);
        displayStayCost(res);

    }

    public void displayErrorMessages(Result result) {
        int index = 1;
        for (String s : result.getErrorMessages()) {
            io.printf("%s. [ERROR] %s.%n", index, s);
            index++;
        }

    }

}
