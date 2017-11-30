package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    List<String> mSummary1_list, mIcon1_list;
    List<Double> mTemperature1_list, mPrecip1_list, mHumidity1_list, mVisibility1_list, mPressure1_list, mWindSpeed1_list, mCloudCover1_list;
    List<Long> mWindBearing1_list;
    String mLocationData;
    SharedPreferences sf;
    Context c;

    public MyAdapter2(Context context, String locationData, List<String> summary_list, List<String> icon1_list, List<Double> temp_list1, List<Double> precip1_list,
                      List<Double> humidity1_list, List<Double> pressure1_list, List<Double> windSpeed1_list,
                      List<Double> cloudCover1_list, List<Long> windBearing1_list) {
        this.c = context;
        mLocationData = locationData;
        mSummary1_list = new ArrayList<>();
        mSummary1_list.addAll(summary_list);
        mIcon1_list = new ArrayList<>();
        mIcon1_list.addAll(icon1_list);
        mTemperature1_list = new ArrayList<>();
        mTemperature1_list.addAll(temp_list1);
        mPrecip1_list = new ArrayList<>();
        mPrecip1_list.addAll(precip1_list);
        mHumidity1_list = new ArrayList<>();
        mHumidity1_list.addAll(humidity1_list);
        mPressure1_list = new ArrayList<>();
        mPressure1_list.addAll(pressure1_list);
        mWindSpeed1_list = new ArrayList<>();
        mWindSpeed1_list.addAll(windSpeed1_list);
        mCloudCover1_list = new ArrayList<>();
        mCloudCover1_list.addAll(cloudCover1_list);
        mWindBearing1_list = new ArrayList<>();
        mWindBearing1_list.addAll(windBearing1_list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater1 = LayoutInflater.from(c);
        View view1 = inflater1.inflate(R.layout.horly_data, parent, false);
        ViewHolder vh1 = new ViewHolder(view1);
        return vh1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sf = PreferenceManager.getDefaultSharedPreferences(c);
        holder.hLocation.setText(mLocationData);
        switch (mIcon1_list.get(position)) {
            case "clear-day":
                holder.hSunImg.setImageResource(R.drawable.sun);
                break;
            case "rain":
                holder.hSunImg.setImageResource(R.drawable.rain);
                break;
            case "cloudy":
                holder.hSunImg.setImageResource(R.drawable.cloudy);
            case "partly-cloudy-day":
                holder.hSunImg.setImageResource(R.drawable.partlycloud);
                break;
            case "fog":
                holder.hSunImg.setImageResource(R.drawable.fog);
                break;
            case "clear-night":
                holder.hSunImg.setImageResource(R.drawable.clearnight);
                break;
            case "partly-cloudy-night":
                holder.hSunImg.setImageResource(R.drawable.cloudy);
                break;
            case "snow":
                holder.hSunImg.setImageResource(R.drawable.snow);
        }
        holder.summary1.setText("Summary:" + mSummary1_list.get(position));
        switch (sf.getString("temp", "")) {
            case "F":
                holder.temperature1.setText("Temperature:" + mTemperature1_list.get(position) + "F");
                break;
            case "C":
                holder.temperature1.setText("Temperature:" + (int) (mTemperature1_list.get(position) / 3.4) + "C");
        }
        switch (sf.getString("windSpeed", "km/h")) {
            case "km/h":
                holder.wind1.setText("Wind:" + mWindSpeed1_list.get(position) + "km/h");
                break;
            case "mph":
                int d3 = (int) (mWindSpeed1_list.get(position) / 1.5);
                holder.wind1.setText("Wind:" + d3 + "mph");
                break;
            case "m/s":
                int d4 = (int) (mWindSpeed1_list.get(position) / 3);
                holder.wind1.setText("Wind:" + d4 + "m/s");
        }
        switch (sf.getString("precipUnit", "mm")) {
            case "mm":
                holder.precip1.setText("Precip:" + mPrecip1_list.get(position) + "mm");
                break;
            case "in":
                holder.precip1.setText("Precip:" + (int) (mPrecip1_list.get(position) / 2.21) + "in");
        }
        holder.humidity1.setText("Humidity:" + mHumidity1_list.get(position) + "%");
        holder.visibility1.setText("CloudCover:" + mCloudCover1_list.get(position) + "km");
        switch (sf.getString("pressure", "hpa")) {
            case "hpa":
                holder.pressure1.setText("Pressure:" + mPressure1_list.get(position) + "hpa");
                break;
            case "kgf/m2":
                holder.pressure1.setText("Pressure:" + (int) (mPressure1_list.get(position) * 10.20) + "kgf/m2");
        }

        long timestamp = sf.getLong("timestamp2",00000);
        switch (sf.getString("dateFormat", "")) {

            case "MMM dd yyyy E":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("MMM dd yyyy E hh:mm:ss ").format(timestamp));
                break;
            case "E MMM dd yyyy":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("E MMM dd yyyy").format(timestamp));
                break;
            case "E dd MMM yyyy":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("E dd MMM yyyy").format(timestamp));
                break;
            case "E yyyy MMM dd":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("E yyyy MMM dd").format(timestamp));
                break;
            case "E yyyy dd MMM":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("E yyyy dd MMM").format(timestamp));
                break;
            case "dd MMM yyyy":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("dd MMM yyyy").format(timestamp));
                break;
            case "MMM dd yyyy":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("MMM dd yyyy").format(timestamp));
                break;
            case "yyyy dd MMM":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("yyyy dd MMM").format(timestamp));
                break;
            case "dd yyyy MMM":
                holder.updateTime1.setText("Last Update:" + new SimpleDateFormat("dd yyyy MMM").format(timestamp));
        }
    }

    @Override
    public int getItemCount() {
        Log.i("SIZE", String.valueOf(mSummary1_list.size()));
        return mSummary1_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView summary1, temperature1, precip1, humidity1, visibility1, pressure1, wind1, updateTime1, hLocation;
        ImageView hSunImg;
        RelativeLayout rl;
        AdView mAdView;

        public ViewHolder(View itemView) {
            super(itemView);
            hLocation = (TextView) itemView.findViewById(R.id.hLocation);
            hSunImg = (ImageView) itemView.findViewById(R.id.hSunImg);
            summary1 = (TextView) itemView.findViewById(R.id.summary1);
            temperature1 = (TextView) itemView.findViewById(R.id.temperature1);
            updateTime1 = (TextView) itemView.findViewById(R.id.updateTime1);
            precip1 = (TextView) itemView.findViewById(R.id.precip1);
            humidity1 = (TextView) itemView.findViewById(R.id.humidity1);
            visibility1 = (TextView) itemView.findViewById(R.id.visibility1);
            pressure1 = (TextView) itemView.findViewById(R.id.pressure1);
            wind1 = (TextView) itemView.findViewById(R.id.wind1);
        }
    }
}
