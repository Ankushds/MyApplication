package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Fragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SharedPreferences sf;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MyAdapter2 myAdapter2;
    Context context;
    double mLat, mLon;
    long timestamp;
    String a0, a1, locationData, cityName;
    SwipeRefreshLayout swipe;
    View view;
    int pos = 0;
    ConnectivityInfo cInfo;
    MyDatabase myDatabase;
    LinearLayout ll;
    TextView textView;
    Map<Object, Object> map;
    Random random;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        context = getActivity().getBaseContext();
        sf = PreferenceManager.getDefaultSharedPreferences(context);
        myDatabase = new MyDatabase(context);
        ViewPager vp = (ViewPager) getActivity().findViewById(R.id.viewPager);
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) getActivity().findViewById(R.id.tabStrip);
        ll = (LinearLayout) tabStrip.getChildAt(0);
        pos = vp.getCurrentItem();
        textView = (TextView) ll.getChildAt(pos);
        cityName = (String) textView.getText();
        cInfo = new ConnectivityInfo(context);
        map = myDatabase.getLocData(0, cityName);
        locationData = (String) map.get("location");
        mLat = Double.valueOf(String.valueOf(map.get("lat")));
        mLon = Double.valueOf(String.valueOf(map.get("lon")));
        if (cInfo.getMyNetworkInfo()) {
            new MyTask2().execute();
        } else {
            new MyTask22().execute();
        }
    }

    @Override
    public void onRefresh() {
        cInfo = new ConnectivityInfo(context);
        if (cInfo.getMyNetworkInfo()) {
            new MyTask2().execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            swipe.setRefreshing(false);
        }
    }

    public class MyTask2 extends AsyncTask<String, Void, String> {
        String line, data = null;
        JSONObject jo10, jo11, jo12;
        JSONArray ja10;
        List<String> summary_list = new ArrayList<>();
        List<String> icon1_list = new ArrayList<>();
        List<Double> temp_list1 = new ArrayList<>();
        List<Double> precip1_list = new ArrayList<>();
        List<Double> humidity1_list = new ArrayList<>();
        List<Double> pressure1_list = new ArrayList<>();
        List<Double> windSpeed1_list = new ArrayList<>();
        List<Double> cloudCover1_list = new ArrayList<>();
        List<Long> windBearing1_list = new ArrayList<>();
        ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) view.findViewById(R.id.pb);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            random = new Random();
            String surl;
            int r = random.nextInt(6);
            try {
                switch (r) {
                    case 0:
                        surl = "e7f61686b4e69b0d9086b3a9581c65e6/";
                        break;
                    case 1:
                        surl = "f0fb04079f07f6faebf6f43cbb4b3375/";
                        break;
                    case 2:
                        surl = "e027c26391730a19047ccdd573866d49/";
                        break;
                    case 3:
                        surl = "e6bd42a4965b2463fe9353ed4c17883d/";
                        break;
                    case 4:
                        surl = "81ee5d9e415aad286a0054f667a80c06/";
                        break;
                    case 5:
                        surl = "07fe14f393318035c986ba4d902bc9d7/";
                        break;
                    default:
                        surl = "07fe14f393318035c986ba4d902bc9d7/";
                }
                URL url = new URL("https://api.darksky.net/forecast/" + surl + mLat + "," + mLon + "?exclude=currently,minutely,daily,alerts,flags");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = (InputStream) connection.getContent();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                if ((line = bufferReader.readLine()) != null) {
                    builder.append(line);
                    data = builder.toString();
                    myDatabase.addData(5, cityName, data);
                    sf.edit().putLong("timestamp2", System.currentTimeMillis()).apply();
                    parsingHourData(data);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;

        }


        public void parsingHourData(String data) {
            try {
                jo10 = new JSONObject(data);
                jo11 = jo10.getJSONObject("hourly");
                ja10 = jo11.getJSONArray("data");
                for (int i = 0; i < ja10.length(); i++) {
                    jo12 = ja10.getJSONObject(i);
                    summary_list.add(i, jo12.getString("summary"));
                    icon1_list.add(i, jo12.getString("icon"));
                    temp_list1.add(i, jo12.getDouble("temperature"));
                    precip1_list.add(i, jo12.getDouble("precipProbability"));
                    humidity1_list.add(i, jo12.getDouble("humidity"));
                    pressure1_list.add(i, jo12.getDouble("pressure"));
                    windSpeed1_list.add(i, jo12.getDouble("windSpeed"));
                    cloudCover1_list.add(i, jo12.getDouble("cloudCover"));
                    windBearing1_list.add(i, jo12.getLong("windBearing"));
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                myAdapter2 = new MyAdapter2(context, locationData, summary_list, icon1_list, temp_list1, precip1_list, humidity1_list, pressure1_list, windSpeed1_list, cloudCover1_list, windBearing1_list);
                recyclerView.setAdapter(myAdapter2);
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            } else {
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
                linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new MyAdapter22());
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            }
        }
    }

    private class MyTask22 extends AsyncTask<String, Void, String> {
        double temperature1, precip1, humidity1, pressure1, windSpeed1, cloudCover1;
        String summary, icon1, dat = null;
        long windBearing1;
        JSONObject jo10, jo11, jo12;
        JSONArray ja10;
        List<String> summary_list = new ArrayList<>();
        List<String> icon1_list = new ArrayList<>();
        List<Double> temp_list1 = new ArrayList<>();
        List<Double> precip1_list = new ArrayList<>();
        List<Double> humidity1_list = new ArrayList<>();
        List<Double> pressure1_list = new ArrayList<>();
        List<Double> windSpeed1_list = new ArrayList<>();
        List<Double> cloudCover1_list = new ArrayList<>();
        List<Long> windBearing1_list = new ArrayList<>();
        ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) view.findViewById(R.id.pb);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            dat = myDatabase.getData(5, cityName);
            parsingHourData(dat);
            return dat;
        }

        public void parsingHourData(String data) {
            if (data != null) {
                try {
                    jo10 = new JSONObject(data);
                    jo11 = jo10.getJSONObject("hourly");
                    ja10 = jo11.getJSONArray("data");
                    for (int i = 0; i < ja10.length(); i++) {
                        jo12 = ja10.getJSONObject(i);
                        summary = jo12.getString("summary");
                        summary_list.add(i, summary);
                        icon1 = jo12.getString("icon");
                        icon1_list.add(i, icon1);
                        temperature1 = jo12.getDouble("temperature");
                        temp_list1.add(i, temperature1);
                        precip1 = jo12.getInt("precipProbability");
                        precip1_list.add(i, precip1);
                        humidity1 = jo12.getDouble("humidity");
                        humidity1_list.add(i, humidity1);
                        pressure1 = jo12.getDouble("pressure");
                        pressure1_list.add(i, pressure1);
                        windSpeed1 = jo12.getDouble("windSpeed");
                        windSpeed1_list.add(i, windSpeed1);
                        cloudCover1 = jo12.getDouble("cloudCover");
                        cloudCover1_list.add(i, cloudCover1);
                        windBearing1 = jo12.getInt("windBearing");
                        windBearing1_list.add(i, windBearing1);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(String d) {
            super.onPostExecute(d);
            if (d != null) {
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
                linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);
                myAdapter2 = new MyAdapter2(context, locationData, summary_list, icon1_list, temp_list1, precip1_list, humidity1_list, pressure1_list, windSpeed1_list, cloudCover1_list, windBearing1_list);
                recyclerView.setAdapter(myAdapter2);
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            } else {
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);
                linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new MyAdapter22());
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            }
        }
    }
}


