package common.fetchData;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class fetchData extends Service {
    private String TAG = "TEST";

    private class DownloadArray extends AsyncTask<String, Void, JSONObject> {

        public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        }

        private JSONObject downloadUrl(String myUrl) {
            InputStream is = null;
            JSONObject jsonObject = null;
            try {
                URL url = new URL(myUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("===", "The response is: " + response);
                is = conn.getInputStream();
                try {
                    jsonObject = new JSONObject(readIt(is));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return jsonObject;
        }

        @Override
        protected JSONObject doInBackground(String... url) {
            return downloadUrl(url[0]);
        }
    }

    public class LocalBinder extends Binder {
        fetchData getService() {
            return fetchData.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private JSONObject getData() {
        JSONObject data = null;
        try {
            data = new DownloadArray().execute("http://192.168.43.160:3000/api/task").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public void fetch() {
        final Handler h = new Handler();
        try {
            h.postDelayed(new Runnable() {
                private long time = 0;

                @Override
                public void run() {
                    time += 1000;
                    getData();
                    Log.d("TimerExample", "Going for... " + time);
                    h.postDelayed(this, 10000);
                }
            }, 1000); // 1 second delay (takes millis)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
