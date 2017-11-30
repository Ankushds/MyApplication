package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
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

public class Fragment3 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recylerView3;
    LinearLayoutManager linearLayoutManager;
    MyAdapter3 myAdapter2;
    double mLat, mLon;
    SharedPreferences sf;
    String locationData, cityName;
    Context context;
    SwipeRefreshLayout swipe;
    View view;
    MyDatabase myDatabase;
    int pos;
    ConnectivityInfo cInfo;
    LinearLayout ll;
    Map<Object,Object> map;
    Random random;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_3, container, false);
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
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) getActivity().findViewById(R.id.tabStrip);
        ll = (LinearLayout) tabStrip.getChildAt(0);
        pos = viewPager.getCurrentItem();
        TextView textView = (TextView) ll.getChildAt(pos);
        cityName = (String) textView.getText();
        map = myDatabase.getLocData(0, cityName);
        locationData = (String) map.get("location");
        mLat = Double.valueOf(String.valueOf(map.get("lat")));
        mLon = Double.valueOf(String.valueOf(map.get("lon")));
        sf.edit().putString("calcu0", String.valueOf(mLat)).apply();
        sf.edit().putString("calcu1",String.valueOf(mLon)).apply();
        cInfo = new ConnectivityInfo(context);
        if (cInfo.getMyNetworkInfo()) {
            new MyTask3().execute();
        } else {
            new MyTask33().execute();
        }
    }

    @Override
    public void onRefresh() {
        cInfo = new ConnectivityInfo(context);
        if (cInfo.getMyNetworkInfo()) {
            new MyTask3().execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            swipe.setRefreshing(false);
        }
    }

    private class MyTask3 extends AsyncTask<String, Void, String> {
        JSONObject jo20, jo21, jo22;
        JSONArray ja20;
        String line, data = null;
        List<String> summary2_list = new ArrayList<>();
        List<String> icon2_list = new ArrayList<>();
        List<Double> tempMin2_list = new ArrayList<>();
        List<Double> tempMax2_list = new ArrayList<>();
        List<Double> cloudCover2_list = new ArrayList<>();
        List<Double> moonPhase2_list = new ArrayList<>();
        List<Double> windSpeed2_list = new ArrayList<>();
        List<Double> precipProb2_list = new ArrayList<>();
        List<Long> windBearing2_list = new ArrayList<>();
        List<Long> time_list = new ArrayList<>();
        ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) view.findViewById(R.id.pb);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            random = new Random();
            int r = random.nextInt(6);
            String surl;
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
                URL url = new URL("https://api.darksky.net/forecast/" + surl + mLat + "," + mLon + "?exclude=currently,minutely,hourly,alerts,flags");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = (InputStream) connection.getContent();
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                if ((line = bufferReader.readLine()) != null) {
                    builder.append(line);
                    data = builder.toString();
                    myDatabase.addData(6, cityName, data);
                    parsingDailyData(data);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sf.edit().putLong("timestamp3", System.currentTimeMillis()).apply();
            return data;
        }

        private void parsingDailyData(String data) {
            try {
                jo20 = new JSONObject(data);
                jo21 = jo20.getJSONObject("daily");
                ja20 = jo21.getJSONArray("data");
                for (int j = 0; j < ja20.length(); j++) {
                    jo22 = ja20.getJSONObject(j);
                    summary2_list.add(j, jo22.getString("summary"));
                    time_list.add(j, jo22.getLong("time"));
                    icon2_list.add(j, jo22.getString("icon"));
                    tempMin2_list.add(j, jo22.getDouble("temperatureMin"));
                    tempMax2_list.add(j, jo22.getDouble("temperatureMax"));
                    cloudCover2_list.add(j, jo22.getDouble("cloudCover"));
                    moonPhase2_list.add(j, jo22.getDouble("moonPhase"));
                    windSpeed2_list.add(j, jo22.getDouble("windSpeed"));
                    windBearing2_list.add(j, jo22.getLong("windBearing"));
                    precipProb2_list.add(j, jo22.getDouble("precipProbability"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                recylerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
                recylerView3.setLayoutManager(new LinearLayoutManager(context));
                myAdapter2 = new MyAdapter3(context, locationData, summary2_list, icon2_list, tempMin2_list, tempMax2_list, cloudCover2_list, windSpeed2_list, windBearing2_list);
                recylerView3.setAdapter(myAdapter2);
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            } else {
                recylerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
                recylerView3.setLayoutManager(new LinearLayoutManager(context));
                recylerView3.setAdapter(new MyAdapter33());
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    private class MyTask33 extends AsyncTask<String, Void, String> {
        JSONObject jo20, jo21, jo22;
        JSONArray ja20;
        double tempMin2, tempMax2, cloudCover2, moonPhase2, windSpeed2, precipProb2, visibility2;
        String summary2, icon2, dat = null;
        long windBearing2, time;
        List<String> summary2_list = new ArrayList<>();
        List<String> icon2_list = new ArrayList<>();
        List<Double> tempMin2_list = new ArrayList<>();
        List<Double> tempMax2_list = new ArrayList<>();
        List<Double> cloudCover2_list = new ArrayList<>();
        List<Double> moonPhase2_list = new ArrayList<>();
        List<Double> windSpeed2_list = new ArrayList<>();
        List<Double> precipProb2_list = new ArrayList<>();
        List<Long> windBearing2_list = new ArrayList<>();
        List<Long> time_list = new ArrayList<>();
        ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) view.findViewById(R.id.pb);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            dat = myDatabase.getData(6, cityName);
            parsingDailyData(dat);
            return dat;
        }

        private void parsingDailyData(String data) {
            if (data != null) {
                try {
                    jo20 = new JSONObject(data);
                    jo21 = jo20.getJSONObject("daily");
                    ja20 = jo21.getJSONArray("data");
                    for (int j = 0; j < ja20.length(); j++) {
                        jo22 = ja20.getJSONObject(j);
                        summary2 = jo22.getString("summary");
                        summary2_list.add(j, summary2);
                        time = jo22.getLong("time");
                        time_list.add(j, time);
                        icon2 = jo22.getString("icon");
                        icon2_list.add(j, icon2);
                        tempMin2 = jo22.getDouble("temperatureMin");
                        tempMin2_list.add(j, tempMin2);
                        tempMax2 = jo22.getDouble("temperatureMax");
                        tempMax2_list.add(j, tempMax2);
                        cloudCover2 = jo22.getDouble("cloudCover");
                        cloudCover2_list.add(j, cloudCover2);
                        moonPhase2 = jo22.getDouble("moonPhase");
                        moonPhase2_list.add(j, moonPhase2);
                        windSpeed2 = jo22.getDouble("windSpeed");
                        windSpeed2_list.add(j, windSpeed2);
                        windBearing2 = jo22.getLong("windBearing");
                        windBearing2_list.add(j, windBearing2);
                        precipProb2 = jo22.getDouble("precipProbability");
                        precipProb2_list.add(j, precipProb2);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                recylerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
                linearLayoutManager = new LinearLayoutManager(context);
                recylerView3.setLayoutManager(linearLayoutManager);
                myAdapter2 = new MyAdapter3(context, locationData, summary2_list, icon2_list, tempMin2_list, tempMax2_list, cloudCover2_list, windSpeed2_list, windBearing2_list);
                recylerView3.setAdapter(myAdapter2);
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            } else {
                recylerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
                linearLayoutManager = new LinearLayoutManager(context);
                recylerView3.setLayoutManager(linearLayoutManager);
                recylerView3.setAdapter(new MyAdapter33());
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}

