package myhouse.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Reservation {

    UUID hostId;
    int reservationId;
    LocalDate startDate;
    LocalDate endDate;
    int guestId;
    BigDecimal total;

    public Reservation() {}

    public Reservation(UUID hostId, int reservationId, LocalDate startDate, LocalDate endDate, int guestId, BigDecimal total) {
        this.hostId = hostId;
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestId = guestId;
        this.total = total;
    }

    public UUID getHostId() {
        return hostId;
    }

    public void setHostId(UUID hostId) {
        this.hostId = hostId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
