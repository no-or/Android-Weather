package com.example.android.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

public class ShowWeatherActivity extends AppCompatActivity {

    private TextView temp;
    private ImageView weatherImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        temp = (TextView) findViewById(R.id.tempText);
        weatherImg = (ImageView) findViewById(R.id.weatherIcon);

        updateWeather();
    }

    private void updateWeather(){
        String city = "Houston,US";
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                weatherImg.setImageBitmap(img);
            }

            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + " °C");
        }
    }

}
