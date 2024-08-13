package com.pjmb.holidayscal;

import lombok.AllArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class VEvent {
    private final String summary;
    private final String dtStart;
    private final String dtEnd;

    public String toString() {
        return "BEGIN:VEVENT\n" +
                "DTSTART;VALUE=DATE:" + dtStart + "\n" +
                "DTEND;VALUE=DATE:" + dtEnd + "\n" +
                "DTSTAMP:" + getCurrentDateTime() + "\n" +
                "SUMMARY:" + summary + "\n" +
                "TRANSP:TRANSPARENT" + "\n" +
                "UID:" + UUID.randomUUID() + "\n" +
                "END:VEVENT";
    }

    private String getCurrentDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        return df.format(new Date());
    }
}
