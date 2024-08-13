package com.pjmb.holidayscal;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HolidaysCal {

    public static final String csvCalendar = "PublicHolidaysList.csv";
    public static final String icsCalendar = "PublicHolidays.ics";

    public static void main(String[] args) {
        List<VEvent> vEvents = readFromCsv(csvCalendar);
        VCalendar vCalendar = new VCalendar(vEvents);
        exportAsICS(vCalendar);
        System.out.println("Public Holidays Calendar Generated !");
    }

    private static void exportAsICS(VCalendar ical) {
        try {
            File file = new File(icsCalendar);
            FileUtils.writeStringToFile(file, ical.toString(), "UTF-8");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
            System.out.println(e.getMessage());
        }
    }

    public static List<VEvent> readFromCsv(String filename) {
        List<VEvent> events = new ArrayList<>();

        URL fileURL = Objects.requireNonNull(HolidaysCal.class.getClassLoader().getResource(filename));
        File file = FileUtils.getFile(fileURL.getPath());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                String name = (columns[0].trim() + " " + columns[1].trim()).trim();
                events.add(createVEvent(name, columns[2])); // 2024
                events.add(createVEvent(name, columns[3])); // 2025
                events.add(createVEvent(name, columns[4])); // 2026
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading from file or parsing dates. Please check your file or date format.");
            System.out.println(e.getMessage());
        }
        return events;
    }

    private static VEvent createVEvent(String name, String dateStr) throws ParseException {
        SimpleDateFormat dfIn = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dfOut = new SimpleDateFormat("yyyyMMdd");
        Date startDate = dfIn.parse(dateStr);
        Date endDate = nextDay(startDate);
        return new VEvent(name, dfOut.format(startDate), dfOut.format(endDate));
    }

    public static Date nextDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }
}
