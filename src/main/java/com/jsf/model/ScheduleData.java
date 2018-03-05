package com.jsf.model;

import com.jsf.managedbean.ScheduleDataMB;

import java.util.Calendar;

public class ScheduleData {

    private int id;
    private long date;
    private int value;
    private String note;

    public ScheduleData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScheduleData(long date, int value, String note) {
        this.date = date;
        this.value = value;
        this.note = note;
    }

    public ScheduleData(ScheduleDataMB scheduleDataMB) {
        Calendar c = Calendar.getInstance();
        c.setTime(scheduleDataMB.getDate());
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        c.set(Calendar.MILLISECOND, 000);

        this.date = c.getTime().getTime();
        this.value = scheduleDataMB.getValue();
        this.note = scheduleDataMB.getNote();
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
