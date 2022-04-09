package com.example.trafficinfo.models;

// Author: Liam Rutherford
// Matriculation Number: S2024208

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RSSItem
{
    private String title = null;
    private String description = null;
    private String link = null;
    private String pubDate = null;
    private String startDate = null;
    private String endDate = null;
    private String georss = null;

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateOutFormat =
            new SimpleDateFormat("EEEE h:mm a (MMM d)");   // Only includes date, not time

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat dateInFormat =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");     // Only includes date, not time

    public void setTitle(String title)     {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description)     {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getPubDateFormatted() {
        try {
            if (pubDate != null) {              // make sure pubDate exists
                Date date = dateInFormat.parse(pubDate);
                assert date != null;
                return dateOutFormat.format(date);
            }
            else {
                return "No date in RSS feed";
            }
        }
        catch (ParseException e) {
            return "No date in RSS feed";      // don't throw exception
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGeorss() {
        return georss;
    }

    public void setGeorss(String georss) {
        this.georss = georss;
    }


}