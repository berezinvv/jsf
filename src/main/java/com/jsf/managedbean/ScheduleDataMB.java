package com.jsf.managedbean;


import com.jsf.dao.ScheduleDataDAO;
import com.jsf.model.ScheduleData;
import org.primefaces.event.SelectEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@ManagedBean(name = "ScheduleDataMB")
@SessionScoped
public class ScheduleDataMB {

    private String email;

    private Date dateFrom;
    private Date dateTo;
    private Date date;
    private int value;
    private String note;
    private List<ScheduleData> scheduleDataList = new ArrayList<>();
    private String scheduleDataString = "[]";

    public ScheduleDataMB() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDate() {
        return new Date();
    }

    public void setDate(Date date) {
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

    public List<ScheduleData> getScheduleDataList() {
        return scheduleDataList;
    }

    public void setScheduleDataList(List<ScheduleData> scheduleDataList) {

        this.scheduleDataList = scheduleDataList;
    }

    public String getScheduleDataString() {

        return scheduleDataString;
    }

    public void setScheduleDataString(String scheduleDataString) {
        this.scheduleDataString = scheduleDataString;
    }

    public void findScheduleData(SelectEvent event) {
        scheduleDataList.clear();
        scheduleDataString = "[";

        if (dateFrom != null && dateTo != null) {
            scheduleDataList = ScheduleDataDAO.findAllByPeriod((Long) dateFrom.getTime(), (Long) dateTo.getTime());
        }
        for (ScheduleData sD : scheduleDataList) {
            Map map = new HashMap<Long, Integer>();
            map.put(sD.getDate(), sD.getValue());

            scheduleDataString += "[" + sD.getDate() + "," + sD.getValue() + "],";
        }
        scheduleDataString += "]";
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("linechart");
    }

    public void clear() {
        if (ScheduleDataDAO.deleteAll()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully completed (clear)",
                            "Successfully completed (clear)"));
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Not successful",
                            "Not successful"));
        }
    }

    public void generate() {

        if (ScheduleDataDAO.findAll()) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Data can`t be added",
                            " Data can`t be added"));
            return;
        }


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString = "01-01-1970";
        Random randomInt = new Random();


        for (int i = 0; i < 50; i++) {
            ScheduleData scheduleData = new ScheduleData();

            Date beginDate = new Date();
            try {
                beginDate = sdf.parse(dateInString);
            } catch (Exception e) {
            }

            long randomDate = ThreadLocalRandom.current().nextLong(beginDate.getTime(), new Date().getTime());
            scheduleData.setDate(randomDate);

            int v = randomInt.nextInt(201);
            scheduleData.setNote("item_[" + v + "]");
            scheduleData.setValue(v);
            ScheduleDataDAO.create(scheduleData);
        }
    }


    public String add() {
        return "page2";
    }

    public void addData(ScheduleDataMB scheduleDataMB) {

        boolean createSD = ScheduleDataDAO.create(new ScheduleData(scheduleDataMB));
        if (createSD) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully completed",
                            "Successfully completed"));
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Not successful",
                            "Not successful"));
        }

    }

}
