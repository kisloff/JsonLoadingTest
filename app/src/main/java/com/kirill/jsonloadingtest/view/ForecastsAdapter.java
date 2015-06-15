package com.kirill.jsonloadingtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kirill.jsonloadingtest.R;
import com.kirill.jsonloadingtest.application.JsonTestApplication;
import com.kirill.jsonloadingtest.model.Forecast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class ForecastsAdapter extends ArrayAdapter<Forecast> {

    private int resource;

    public ForecastsAdapter(Context context, int resource, List<Forecast> forecasts) {
        super(context, resource, forecasts);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        Forecast forecast = getItem(position);

        ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progress_bar);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.image);

        String url ="http://blog.worldpokertour.com/wp-content/uploads/2011/12/WPT-Royal-Flush-Girls-Bikini-29.jpg";

        ImageTask task = new ImageTask(imageView, progressBar, url);
        task.execute();

        TextView dateTextView = (TextView)convertView.findViewById(R.id.date);
        dateTextView.setText(forecast.date);

        TextView dayTextView = (TextView)convertView.findViewById(R.id.day);
        dayTextView.setText(forecast.day);

        TextView highTextView = (TextView)convertView.findViewById(R.id.high);
        highTextView.setText(String.valueOf(forecast.high));

        TextView lowTextView = (TextView)convertView.findViewById(R.id.low);
        lowTextView.setText(String.valueOf(forecast.low));

        TextView textTextView = (TextView)convertView.findViewById(R.id.text);
        textTextView.setText(forecast.text);

        return convertView;
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(JsonTestApplication.APP_TAG, "Error getting bitmap", e);
        }
        return bm;
    }

    private class ImageTask extends AsyncTask<Void, Void, Bitmap> {

        private ImageView imageView;
        private ProgressBar progressBar;
        private String url;

        public ImageTask(ImageView imageView, ProgressBar progressBar, String url) {
            this.imageView = imageView;
            this.progressBar = progressBar;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            return getImageBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
