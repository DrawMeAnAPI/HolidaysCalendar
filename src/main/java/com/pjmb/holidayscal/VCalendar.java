package com.pjmb.holidayscal;

import java.util.List;

public class VCalendar {
    private static final String VERSION = "2.0";
    private static final String PRODID = "-//PJMBusnel Inc.//EN";
    private static final String CALSCALE = "GREGORIAN";

    private final List<VEvent> events;

    public VCalendar(List<VEvent> events) {
        this.events = events;
    }

    public String toIcsFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN:VCALENDAR\n")
                .append("VERSION:").append(VERSION).append("\n")
                .append("PRODID:").append(PRODID).append("\n")
                .append("CALSCALE:").append(CALSCALE).append("\n");
        for (VEvent event : events) {
            sb.append(event.toIcsFormat()).append("\n");
        }
        sb.append("END:VCALENDAR\n");
        return sb.toString();
    }
}