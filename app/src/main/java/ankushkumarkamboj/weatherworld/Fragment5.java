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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Fragment5 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    Context context;
    double mLat, mLon;
    String a0, a1, a2, locationData, cityName;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipe;
    View view;
    int pos;
    SharedPreferences sf;
    MyDatabase myDatabase;
    ConnectivityInfo cInfo;
    LinearLayout ll;
    Map<Object, Object> map;
    Random random;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_5, container, false);
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
        cInfo = new ConnectivityInfo(context);
        if (cInfo.getMyNetworkInfo()) {
            new MyMoonTask().execute();
        } else {
            new MyMoonTaskOff().execute();

        }
    }

    @Override
    public void onRefresh() {
        cInfo = new ConnectivityInfo(context);
        if (cInfo.getMyNetworkInfo()) {
            new MyMoonTask().execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            swipe.setRefreshing(false);
        }
    }

    private class MyMoonTask extends AsyncTask<String, Void, String> {
        List<Double> age_list = new ArrayList<>();
        List<String> phase_list = new ArrayList<>();
        List<Integer> illum_list = new ArrayList<>();
        List<Integer> number_list = new ArrayList<>();
        double mp, age;
        int illum, num;
        ProgressBar progressBar;
        String line, phase, data = null;
        BufferedReader bufferedReader;
        StringBuilder builder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) view.findViewById(R.id.pb);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String surl;
            random = new Random();
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
                URL url = new URL("https://api.darksky.net/forecast/" + surl + mLat + "," + mLon + "?exclude=currently,minutely,hourly,alerts,flags");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is));
                builder = new StringBuilder();
                if ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                    data = builder.toString();
                    myDatabase.addData(7, cityName, data);
                    parseData(data);

                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sf.edit().putLong("timestamp5", System.currentTimeMillis()).apply();
            return data;
        }

        private void parseData(String s) throws JSONException {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject jsonObject1 = jsonObject.getJSONObject("daily");
            JSONArray jsonArray = jsonObject1.getJSONArray("data");
            JSONObject jsonObject2;
            for (int i = 0; i < 8; i++) {
                jsonObject2 = jsonArray.getJSONObject(i);
                mp = jsonObject2.getDouble("moonPhase");
                Log.i("ankush", String.valueOf(mp));
                int mpp = (int) (mp * 100);

                if (mpp == 0) {
                    num = 0;
                    phase = "New Moon";
                } else if (mpp > 0 && mpp < 25) {
                    num = 1;
                    phase = "Waxing Crescent";
                } else if (mpp == 25) {
                    num = 2;
                    phase = "First Quarter";
                } else if (mpp > 25 && mpp < 50) {
                    num = 3;
                    phase = "Waxing Gibbous";
                } else if (mpp == 50) {
                    num = 4;
                    phase = "Full Moon";
                } else if (mpp > 50 || mpp < 75) {
                    num = 5;
                    phase = "Wanning Gibbous";
                } else if (mpp == 75) {
                    num = 6;
                    phase = "Last quarter";
                } else {
                    num = 7;
                    phase = "Wanning Crescent";
                }
                number_list.add(i, num);
                phase_list.add(i, phase);
                age = 29.53 * mp;
                age = Double.parseDouble(new DecimalFormat("##.##").format(age));
                age_list.add(i, age);
                if (mp == 0 || mp < 0.50) {
                    illum = (int) (mp * 2 * 100);
                } else {
                    illum = (int) (100 - mp * 100);
                }
                illum_list.add(i, illum);
                Log.i("aaaaaa", String.valueOf(number_list));
            }
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                MyAdapter myAdapter = new MyAdapter(context, number_list, locationData, age_list, phase_list, illum_list);
                recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(myAdapter);
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            } else {
                recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new MyAdapter5());
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    private class MyMoonTaskOff extends AsyncTask<String, Void, String> {
        List<Double> age_list = new ArrayList<>();
        List<String> phase_list = new ArrayList<>();
        List<Integer> illum_list = new ArrayList<>();
        List<Integer> number_list = new ArrayList<>();
        double mp, age;
        int illum, num;
        ProgressBar progressBar;
        String phase, dat = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) view.findViewById(R.id.pb);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            dat = myDatabase.getData(7, cityName);
            if (dat != null) {
                try {
                    parseData(dat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return dat;
        }

        private void parseData(String s) throws JSONException {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject jsonObject1 = jsonObject.getJSONObject("daily");
            JSONArray jsonArray = jsonObject1.getJSONArray("data");
            JSONObject jsonObject2;
            for (int i = 0; i < 8; i++) {
                jsonObject2 = jsonArray.getJSONObject(i);
                mp = jsonObject2.getDouble("moonPhase");
                int mpp = (int) (mp * 100);

                if (mpp == 0) {
                    num = 0;
                    phase = "New Moon";
                } else if (mpp > 0 && mpp < 25) {
                    num = 1;
                    phase = "Waxing Crescent";
                } else if (mpp == 25) {
                    num = 2;
                    phase = "First Quarter";
                } else if (mpp > 25 && mp < 50) {
                    num = 3;
                    phase = "Waxing Gibbous";
                } else if (mpp == 50) {
                    num = 4;
                    phase = "Full Moon";
                } else if (mpp > 50 || mp < 75) {
                    num = 5;
                    phase = "Wanning Gibbous";
                } else if (mpp == 75) {
                    num = 6;
                    phase = "Last quarter";
                } else {
                    num = 7;
                    phase = "Wanning Crescent";
                }
                number_list.add(i, num);
                phase_list.add(i, phase);
                age = 29.53 * mp;
                age = Double.parseDouble(new DecimalFormat("##.##").format(age));
                age_list.add(i, age);
                if (mp == 0 || mp < 0.50) {
                    illum = (int) (mp * 2 * 100);
                } else {
                    illum = (int) (mp * 0.5 * 100);
                }
                illum_list.add(i, illum);
            }
        }


        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                MyAdapter myAdapter = new MyAdapter(context, number_list, locationData, age_list, phase_list, illum_list);
                recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter);
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);

            } else {
                recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new MyAdapter5());
                if (swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}
