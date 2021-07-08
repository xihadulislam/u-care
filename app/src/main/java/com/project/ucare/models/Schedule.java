package com.project.ucare.models;

import java.io.Serializable;
import java.util.List;

public class Schedule implements Serializable {
    String id;
    String userId;
    String medicineName;
    String medicineType;
    String medicineUnit;
    String startDate;
    String duration;
    String intake;
    String reminderTime;
    List<String> days;
    boolean isEnable;


    public Schedule() {
    }

    public Schedule(String id, String userId, String medicineName, String medicineType,
                    String medicineUnit, String startDate,
                    String duration, String intake, String reminderTime,
                    List<String> days, boolean isEnable) {
        this.id = id;
        this.userId = userId;
        this.medicineName = medicineName;
        this.medicineType = medicineType;
        this.medicineUnit = medicineUnit;
        this.startDate = startDate;
        this.duration = duration;
        this.intake = intake;
        this.reminderTime = reminderTime;
        this.days = days;
        this.isEnable = isEnable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getMedicineUnit() {
        return medicineUnit;
    }

    public void setMedicineUnit(String medicineUnit) {
        this.medicineUnit = medicineUnit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}

