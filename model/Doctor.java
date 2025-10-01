package model;

public class Doctor {
    private String doctorId;
    private String name;
    private String phone;
    private String schedule;

    public Doctor(String doctorId, String name, String phone, String schedule) {
        this.doctorId = doctorId;
        this.name = name;
        this.phone = phone;
        this.schedule = schedule;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
