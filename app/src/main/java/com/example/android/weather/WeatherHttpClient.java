package com.example.android.weather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String APP_ID = "&appid=47c113f6a562bdb39c3e1993b54354d8";
    private static String IMG_URL = "http://openweathermap.org/img/w/";


    public String getWeatherData(String location) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(BASE_URL + location + APP_ID)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }

    public byte[] getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(IMG_URL + code + ".png")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(false);
            con.connect();

            // Let's read the response
            Integer status = con.getResponseCode();
            String Sstatus = status.toString();
            Log.d("STATUSimg", Sstatus );
            Log.d("CODE", code);
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }
}
