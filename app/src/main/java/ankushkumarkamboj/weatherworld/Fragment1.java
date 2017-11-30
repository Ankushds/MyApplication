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
import android.util.Log;
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


public class Fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Random random;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MyAdapter1 myAdapter1;
    Context context;
    List<Long> sunriseList = new ArrayList<>();
    List<Long> sunsetList = new ArrayList<>();
    SharedPreferences sf;
    double mLat, mLon;
    String locationData, cityName;
    View view;
    PagerSlidingTabStrip tabStrip;
    int pos = 0;
    LinearLayout ll;
    TextView textView;
    SwipeRefreshLayout swipe;
    MyDatabase myDatabase;
    ConnectivityInfo cInfo;
    AdView mAdview;
    Map<Object, Object> map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);
        mAdview = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
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
        tabStrip = (PagerSlidingTabStrip) getActivity().findViewById(R.id.tabStrip);
        ll = (LinearLayout) tabStrip.getChildAt(0);
        pos = viewPager.getCurrentItem();
        textView = (TextView) ll.getChildAt(pos);
        cityName = (String) textView.getText();
        cInfo = new ConnectivityInfo(context);
        map = myDatabase.getLocData(0, cityName);
        Log.i("numberparse", String.valueOf(map.get("lat")));
        if (map != null) {
            mLat = Double.valueOf(String.valueOf(map.get("lat")));
            mLon = Double.valueOf(String.valueOf(map.get("lon")));
            sf.edit().putString("cal0", String.valueOf(mLat)).apply();
            sf.edit().putString("cal1", String.valueOf(mLon)).apply();
            locationData = (String) map.get("location");
            if (cInfo.getMyNetworkInfo()) {
                new MyTask1().execute();
            } else {
                new MyTask11().execute();
            }
        } else {
            Toast.makeText(context, "There is Some Error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        cInfo = new ConnectivityInfo(context);
        if (cInfo.getMyNetworkInfo()) {
            new MyTask1().execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            swipe.setRefreshing(false);
        }

    }

    public class MyTask1 extends AsyncTask<String, Void, String> {

        JSONObject jo0, jo1, jo02, jo03, jo04;
        JSONArray ja00;
        double temperature, dewPoint, humidity,
                windSpeed, visibility, pressure, precipProbability, precipIntensity;
        String icon, line, da = null;
        BufferedReader bufferReader;
        StringBuilder builder;
        List<Long> dTime_list = new ArrayList<>();
        List<String> dIcon_list = new ArrayList<>();
        List<Double> minTemp_list = new ArrayList<>();
        List<Double> maxTemp_list = new ArrayList<>();
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
                URL url = new URL("https://api.darksky.net/forecast/" + surl + mLat + "," + mLon + "?exclude=minutely,hourly,alerts,flags");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream stream = (InputStream) connection.getContent();
                bufferReader = new BufferedReader(new InputStreamReader(stream));
                builder = new StringBuilder();
                if ((line = bufferReader.readLine()) != null) {
                    builder.append(line);
                    da = builder.toString();
                    Log.i("datastring", da);
                    myDatabase.addData(4, cityName, da);
                    parsingCurrentData(da);
                    parsing6Days(da);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sf.edit().putLong("timestamp1", System.currentTimeMillis()).apply();
            return da;
        }

        public void parsingCurrentData(String data) {
            try {
                jo0 = new JSONObject(data);
                jo1 = jo0.getJSONObject("currently");
                icon = jo1.getString("icon");
                precipIntensity = jo1.getDouble("precipIntensity");
                precipProbability = jo1.getDouble("precipProbability");
                temperature = jo1.getDouble("temperature");
                sf.edit().putInt("mytemp", (int) (temperature)).apply();
                dewPoint = jo1.getDouble("dewPoint");
                humidity = jo1.getDouble("humidity");
                windSpeed = jo1.getDouble("windSpeed");
                visibility = jo1.getDouble("visibility");
                Log.i("f1pressure", String.valueOf(jo1.getDouble("pressure")));
                pressure = jo1.getDouble("pressure");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void parsing6Days(String data) {
            try {
                jo02 = new JSONObject(data);
                jo03 = jo02.getJSONObject("daily");
                ja00 = jo03.getJSONArray("data");
                for (int k = 0; k < ja00.length(); k++) {
                    jo04 = ja00.getJSONObject(k);
                    dTime_list.add(k, jo04.getLong("time"));
                    dIcon_list.add(k, jo04.getString("icon"));
                    minTemp_list.add(k, jo04.getDouble("temperatureMin"));
                    maxTemp_list.add(k, jo04.getDouble("temperatureMax"));
                }
                sf.edit().putString("mytemp0" + pos, String.valueOf(minTemp_list.get(0))).apply();
                sf.edit().putString("mytemp1" + pos, String.valueOf(maxTemp_list.get(0))).apply();
                sf.edit().putString("myicon" + pos, dIcon_list.get(0)).apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected void onPostExecute(String d) {
            super.onPostExecute(d);
            if (d != null) {
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                myAdapter1 = new MyAdapter1(context, locationData, icon, precipIntensity, precipProbability, temperature, dewPoint, humidity,
                        windSpeed, visibility, pressure, dIcon_list, minTemp_list, maxTemp_list);
                recyclerView.setAdapter(myAdapter1);
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new MyAdapter11());
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    private class MyTask11 extends AsyncTask<String, Void, String> {

        JSONObject jo0, jo1, jo02, jo03, jo04;
        JSONArray ja00;
        double temperature, dewPoint, humidity,
                windSpeed, visibility, pressure, precipProbability, precipIntensity, minTemp, maxTemp;
        String icon, dIcon, dat = null;
        long dTime, sunriseTime2, sunsetTime2;
        List<Long> dTime_list = new ArrayList<>();
        List<String> dIcon_list = new ArrayList<>();
        List<Double> minTemp_list = new ArrayList<>();
        List<Double> maxTemp_list = new ArrayList<>();
        ProgressBar progressBar;
        MyDatabase mDatabase;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDatabase = new MyDatabase(context);
            progressBar = (ProgressBar) view.findViewById(R.id.pb);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {
            dat = mDatabase.getData(4, cityName);
            if (dat != null) {
                parsingCurrentData(dat);
                parsing6Days(dat);
            }
            return dat;
        }

        public void parsingCurrentData(String data) {
            try {
                jo0 = new JSONObject(data);
                jo1 = jo0.getJSONObject("currently");
                icon = jo1.getString("icon");
                precipIntensity = jo1.getDouble("precipIntensity");
                precipProbability = jo1.getDouble("precipProbability");
                temperature = jo1.getDouble("temperature");
                sf.edit().putInt("mytemp", (int) (temperature)).apply();
                dewPoint = jo1.getDouble("dewPoint");
                humidity = jo1.getDouble("humidity");
                windSpeed = jo1.getDouble("windSpeed");
                visibility = jo1.getDouble("visibility");
                pressure = jo1.getDouble("pressure");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void parsing6Days(String data) {
            try {
                jo02 = new JSONObject(data);
                jo03 = jo02.getJSONObject("daily");
                ja00 = jo03.getJSONArray("data");
                for (int k = 0; k < ja00.length(); k++) {
                    jo04 = ja00.getJSONObject(k);
                    dTime = jo04.getLong("time");
                    dTime_list.add(k, dTime);
                    dIcon = jo04.getString("icon");
                    dIcon_list.add(k, dIcon);
                    minTemp = jo04.getDouble("temperatureMin");
                    minTemp_list.add(k, minTemp);
                    maxTemp = jo04.getDouble("temperatureMax");
                    maxTemp_list.add(k, maxTemp);
                    sunriseTime2 = jo04.getLong("sunriseTime");
                    sunriseList.add(k, sunriseTime2);
                    sunsetTime2 = jo04.getLong("sunsetTime");
                    sunsetList.add(k, sunsetTime2);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                myAdapter1 = new MyAdapter1(context, locationData, icon, precipIntensity, precipProbability, temperature, dewPoint, humidity,
                        windSpeed, visibility, pressure, dIcon_list, minTemp_list, maxTemp_list);
                recyclerView.setAdapter(myAdapter1);

                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            } else {
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);
                linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new MyAdapter11());
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            }
        }
    }
}

