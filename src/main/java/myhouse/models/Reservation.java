package myhouse.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Reservation {

    Host host;
    int reservationId;
    LocalDate startDate;
    LocalDate endDate;
    Guest guest;
    BigDecimal total;
    boolean cancelled;

    public Reservation() {}

    public Reservation(Host host, int reservationId, LocalDate startDate, LocalDate endDate, Guest guest, BigDecimal total, boolean cancelled) {
        this.host = host;
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        this.total = total;
        this.cancelled = cancelled;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
