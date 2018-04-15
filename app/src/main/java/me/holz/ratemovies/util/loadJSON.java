package me.holz.ratemovies.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class loadJSON implements Runnable {

    public String result;
    public Thread thread;

    private String url;

    public loadJSON(String url)
    {
        thread = new Thread(this, "loadjson");
        this.url = url;
    }

    @Override
    public void run() {
        StringBuilder res = new StringBuilder();
        URL url = null;
        try {
            url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                res.append(line);
            }
            rd.close();

            result = res.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
