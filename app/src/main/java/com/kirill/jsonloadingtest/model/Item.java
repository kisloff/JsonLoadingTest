package com.kirill.jsonloadingtest.model;

import com.google.api.client.util.Key;

import java.util.List;


public class Item {
    @Key("forecast")
    public List<Forecast> forecasts;
}
