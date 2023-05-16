package com.aptech.project2.Model;

import java.time.LocalDate;

public class Member {
    private String id;
    private String name;
    private String address;
    private String gender;
    private String phone;
    private String schedule;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public Member() {
    }

    public Member(String id, String name, String address, String gender, String phone, String schedule, LocalDate startDate, LocalDate endDate, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
