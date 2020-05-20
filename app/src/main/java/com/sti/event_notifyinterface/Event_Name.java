package com.sti.event_notifyinterface;

public class Event_Name {
     String description,eventDate,eventTime,title,venue;
     int reminderSet;


    public Event_Name() {
        reminderSet=0;

    }

    public Event_Name(String description, String eventDate, String eventTime, String title, String venue, int reminderSet) {
        this.description = description;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.title = title;
        this.venue = venue;
        this.reminderSet = reminderSet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getReminderSet() {
        return reminderSet;
    }

    public void setReminderSet(int reminderSet) {
        this.reminderSet = reminderSet;
    }
}
