package com.kirill.jsonloadingtest.model;

import com.google.api.client.util.Key;


public class Forecast {

    @Key("date")
    public String date;

    @Key("day")
    public String day;

    @Key("high")
    public int high;

    @Key("low")
    public int low;

    @Key("text")
    public String text;

    @Override
    public String toString() {
        return "Forecast{" +
                "date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", high=" + high +
                ", low=" + low +
                ", text='" + text + '\'' +
                '}';
    }
}
