package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class WeatherForcast extends AppCompatActivity {
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forcast);
        progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);

        ForecastQuery myForcast = new ForecastQuery();
        myForcast.execute();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String uv;
        String max;
        String min;
        String current;
        String icon;
        Bitmap weather;
        String ret = null;

        TextView maxTemp;
        TextView minTemp;
        TextView uvIndex;
        TextView currentTemp;
        ImageView weatherIcon;

        String queryURL = " http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        String uvUrl = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

        @Override
        protected String doInBackground(String... strings) {
            try {       // Connect to the server:
                URL url = new URL(queryURL);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                //Set up the XML parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");

                //Iterate over the XML tags:
                int EVENT_TYPE;         //While not the end of the document:
                while ((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    switch (EVENT_TYPE) {
                        case START_TAG:         //This is a start tag < ... >
                            String tagName = xpp.getName(); // What kind of tag?
                            if (tagName.equals("temperature")) {
                                current = xpp.getAttributeValue(null, "value");
                                publishProgress(25, 50, 75);
                                max = xpp.getAttributeValue(null, "max");
                                publishProgress(25, 50, 75);
                                min = xpp.getAttributeValue(null, "min");

                            }
                            if (tagName.equals("weather")) {
                                icon = xpp.getAttributeValue(null, "icon");
                            }
                            break;

                        case END_TAG:           //This is an end tag: </ ... >
                            break;
                        case TEXT:              //This is text between tags < ... > Hello world </ ... >
                            break;
                    }
                    publishProgress(25, 50, 75);
                    xpp.next(); // move the pointer to next XML element
                }
                url = new URL(uvUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                inStream = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject uvValue = new JSONObject(result);
                uv = String.valueOf(uvValue.getDouble("value"));

                String iconUrlString = "http://openweathermap.org/img/w/" + icon + ".png";
                Bitmap image = null;
                url = new URL(iconUrlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    image = BitmapFactory.decodeStream(urlConnection.getInputStream());
                }

                if (fileExistance(icon)) {
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(icon + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bm = BitmapFactory.decodeStream(fis);
                    Log.i("Download", "Image Loaded from file");
                    publishProgress(100);

                } else {
                    FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i("Download", "Image Downloaded");
                    publishProgress(100);
                }
                weather = image;
                Log.i("Image Name", icon + ".png");

            } catch (
                    MalformedURLException mfe) {
                ret = "Malformed URL exception";
            } catch (
                    IOException ioe) {
                ret = "IO Exception. Is the Wifi connected?";
            } catch (
                    XmlPullParserException pe) {
                ret = "XML Pull exception. The XML is not properly formed";
            } catch (
                    JSONException e) {
                ret = "JSON error";
            }


            //What is returned here will be passed as a parameter to onPostExecute:
            return ret;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);

            maxTemp = findViewById(R.id.maxTemp);
            minTemp = findViewById(R.id.minTemp);
            uvIndex = findViewById(R.id.uvIndex);
            currentTemp = findViewById(R.id.currentTemp);
            weatherIcon = findViewById(R.id.weatherIcon);

            String v1 = getString(R.string.maxTemp) + max;
            String v2 = getString(R.string.minTemp) + min;
            String v3 = getString(R.string.currentTemp) + current;
            String v4 = getString(R.string.uvIndex) + uv;

            maxTemp.setText(v1);
            minTemp.setText(v2);
            currentTemp.setText(v3);
            uvIndex.setText(v4);
            weatherIcon.setImageBitmap(weather);

            progress.setVisibility(View.INVISIBLE);
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

    }
}
