package com.example.chint.json_sample;

import android.os.AsyncTask;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ArrayList<ListInfo> al;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.lview);
        al = new ArrayList<ListInfo>();
        new MyTask().execute();
    }

    class MyTask extends AsyncTask<Void, Void, Void>{
        String JSON_URL;
        @Override
        protected void onPreExecute() {
           JSON_URL = "http://api.dailymile.com/entries.json";
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String json = br.readLine();
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonentries = jsonObject.getJSONArray("entries");
                for(int i = 0; i<jsonentries.length(); i++) {
                    JSONObject jsonarray = jsonentries.getJSONObject(i);
                    JSONObject jsonlocation = jsonarray.getJSONObject("location");
                    JSONObject jsonuser = jsonarray.getJSONObject("user");
                    String s = jsonlocation.getString("name");
                    al.add(new ListInfo(jsonuser.getString("photo_url"), jsonuser.getString("display_name"), jsonlocation.getString("name")));
                }
                br.close();
                is.close();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            MyAdapter ma = new MyAdapter(getApplicationContext(), R.layout.layout_row, al);
            lv.setAdapter(ma);
        }

    }
}
