package myhouse.ui;

public enum MenuOptions {
    EXIT(0, "Exit"),
    VIEW_RESERVATIONS(1, "View Reservations"),
    MAKE_RESERVATION(2, "Make a Reservation"),
    EDIT_RESERVATION(3, "Edit a Reservation"),
    CANCEL_RESERVATION(4, "Cancel a Reservation");

    private int index;
    private String name;

    MenuOptions(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
