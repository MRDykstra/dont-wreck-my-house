package myhouse;

import myhouse.data.ReservationFileRepository;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        ReservationFileRepository repo = new ReservationFileRepository("./dont-wreck-my-house-data/Test/reservations-test");

        repo.createListOfReservations();

    }
}
