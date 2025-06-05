package io.github.stawkey.roomreservation.entity;

import io.github.stawkey.roomreservation.dto.ReservationStatus;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "reservations")
public class Reservation extends Auditable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="room_id", nullable = false)
    private Room room;

    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;

    @Column(name="end_date", nullable = false)
    private OffsetDateTime endDate;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "description")
    private String description;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
