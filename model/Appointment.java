package model;

import java.time.LocalDateTime;

public class Appointment {
    private String appointmentId;
    private LocalDateTime dateTime;
    private Status status;

    public enum Status {
        BOOKED, CANCELLED, COMPLETED
    }

    public Appointment(String appointmentId, LocalDateTime dateTime) {
        this.appointmentId = appointmentId;
        this.dateTime = dateTime;
        this.status = Status.BOOKED;
    }

    public void book() {
        this.status = Status.BOOKED;
        System.out.println("Appointment booked successfully.");
    }

    public void cancel() {
        this.status = Status.CANCELLED;
        System.out.println("Appointment cancelled.");
    }

    public void reschedule(LocalDateTime newTime) {
        if(this.status == Status.BOOKED) {
            this.dateTime = newTime;
            System.out.println("Appointment rescheduled to " + newTime);
        }
    }

    public boolean isUpcoming() {
        return this.dateTime.isAfter(LocalDateTime.now()) && this.status == Status.BOOKED;
    }
}
