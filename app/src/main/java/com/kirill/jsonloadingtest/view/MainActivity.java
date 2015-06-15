package com.kirill.jsonloadingtest.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kirill.jsonloadingtest.R;
import com.kirill.jsonloadingtest.application.JsonTestApplication;
import com.kirill.jsonloadingtest.model.Forecast;
import com.kirill.jsonloadingtest.service.Service;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ProgressBar progressBar;
    private ForecastsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        adapter = new ForecastsAdapter(this, R.layout.list_item_forecast, new ArrayList<Forecast>());
        adapter.setNotifyOnChange(true);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        update();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                update();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void update() {
        ForecastsTask task = new ForecastsTask();
        task.execute();
    }

    private Service service() {
        return ((JsonTestApplication)getApplication()).service;
    }

    private class ForecastsTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            adapter.clear();
        }

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            return service().getForecasts();
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);

            progressBar.setVisibility(View.GONE);
            adapter.addAll(forecasts);
        }
    }
}
