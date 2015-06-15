package com.kirill.jsonloadingtest.service;

import android.content.Context;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.kirill.jsonloadingtest.application.JsonTestApplication;
import com.kirill.jsonloadingtest.model.Forecast;
import com.kirill.jsonloadingtest.model.Forecasts;

import java.io.IOException;
import java.util.List;


public class Service {

    public static final String SERVICE_URL = "http://query.yahooapis.com/v1/public/yql?q=select%20item%20from%20weather.forecast%20where%20location%3D%2248907%22&format=json";

    private static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private Context context;
    private HttpRequestFactory requestFactory;

    public Service(Context context) {
        this.context = context;

        requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) {
                request.setParser(new JsonObjectParser(JSON_FACTORY));
            }
        });
    }

    public List<Forecast> getForecasts() {

        try {
            GenericUrl url = new GenericUrl(SERVICE_URL);
            HttpRequest request = requestFactory.buildGetRequest(url);
            HttpResponse httpResponse = request.execute();
            Forecasts forecasts = httpResponse.parseAs(Forecasts.class);
            List<Forecast> forecastList = forecasts.query.results.channel.item.forecasts;
            Log.e(JsonTestApplication.APP_TAG, "forecastList = "+forecastList);
            return forecastList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
