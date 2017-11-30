package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {
    List<String> mS2L, mI2L;
    List<Double> mTM2L, mTMX2L, mCC2L, mWindSpeed2_list;
    List<Long> mWB2l;
    SimpleDateFormat sdf, sdf1;
    Context c;
    SharedPreferences sf;
    String mLocationData;


    public MyAdapter3(Context context, String locationData, List<String> summary2_list, List<String> icon2_list, List<Double> tempMin2_list, List<Double> tempMax2_list,
                      List<Double> cloudCover2_list, List<Double> windSpeed2_list, List<Long> windBearing2_list) {
        c = context;
        mLocationData = locationData;
        sdf = new SimpleDateFormat("E");
        mS2L = summary2_list;
        mI2L = icon2_list;
        mTM2L = tempMin2_list;
        mTMX2L = tempMax2_list;
        mCC2L = cloudCover2_list;
        mWB2l = windBearing2_list;
        mWindSpeed2_list = windSpeed2_list;
        sf = PreferenceManager.getDefaultSharedPreferences(context);
        sdf1 = new SimpleDateFormat("hh:mm:ss a");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.daily, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (mI2L.get(position)) {
            case "clear-day":
                holder.img1.setImageResource(R.drawable.sun);
                break;
            case "cloudy":
                holder.img1.setImageResource(R.drawable.cloudy);
            case "rain":
                holder.img1.setImageResource(R.drawable.rain);
                break;
            case "partly-cloudy-day":
                holder.img1.setImageResource(R.drawable.partlycloud);
                break;
            case "fog":
                holder.img1.setImageResource(R.drawable.fog);
                break;
            case "clear-night":
                holder.img1.setImageResource(R.drawable.clearnight);
                break;
            case "partly-cloudy-night":
                holder.img1.setImageResource(R.drawable.cloudy);
                break;
            case "snow":
                holder.img1.setImageResource(R.drawable.snow);
        }
        holder.tv1.setText("Summary:" + mS2L.get(position));

        long timestamp = sf.getLong("timestamp3", 00000);
        switch (sf.getString("dateFormat", "")) {

            case "MMM dd yyyy E":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("MMM dd yyyy E hh:mm:ss ").format(timestamp));
                break;
            case "E MMM dd yyyy":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("E MMM dd yyyy").format(timestamp));
                break;
            case "E dd MMM yyyy":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("E dd MMM yyyy").format(timestamp));
                break;
            case "E yyyy MMM dd":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("E yyyy MMM dd").format(timestamp));
                break;
            case "E yyyy dd MMM":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("E yyyy dd MMM").format(timestamp));
                break;
            case "dd MMM yyyy":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("dd MMM yyyy").format(timestamp));
                break;
            case "MMM dd yyyy":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("MMM dd yyyy").format(timestamp));
                break;
            case "yyyy dd MMM":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("yyyy dd MMM").format(timestamp));
                break;
            case "dd yyyy MMM":
                holder.tv2.setText("Last Update:" + new SimpleDateFormat("dd yyyy MMM").format(timestamp));
        }

        switch (sf.getString("temp", "")) {
            case "F":
                holder.tv3.setText("Temperature:" + mTM2L.get(position) + "/" + mTMX2L.get(position) + "F");
                break;
            case "C":
                holder.tv3.setText("Temperature:" + (int) (mTM2L.get(position) / 3.4) + "/" + (int) (mTMX2L.get(position) / 3.4) + "C");
        }
        String lat0 = sf.getString("calcu0", "");
        String lon1 = sf.getString("calcu1", "");
        Calendar calendar = Calendar.getInstance();
        long l = System.currentTimeMillis();
        long l1 = 24 * 60 * 60 * 1000;
        Date date = new Date(l + position * l1);
        calendar.setTime(date);
        com.luckycatlabs.sunrisesunset.dto.Location location = new com.luckycatlabs.sunrisesunset.dto.Location(lat0, lon1);
        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, calendar.getTimeZone());
        holder.tv4.setText("SunRise:" + calculator.getOfficialSunriseForDate(calendar));
        holder.tv5.setText("SunSet:" + calculator.getOfficialSunsetForDate(calendar));
        holder.d.setText(sdf.format(l + position * l1));
        switch (sf.getString("windSpeed", "km/h")) {
            case "km/h":
                holder.speedWind.setText("WindSpeed:" + mWindSpeed2_list.get(position) + "km/h");
                break;
            case "mph":
                holder.speedWind.setText("WindSpeed:" + (int) (mWindSpeed2_list.get(position) / 1.5) + "mph");
                break;
            case "m/s":
                holder.speedWind.setText("WindSpeed:" + (int) (mWindSpeed2_list.get(position) / 3) + "m/s");
        }
        holder.dLocation.setText("Location:" + mLocationData);
    }

    @Override
    public int getItemCount() {
        return mTM2L.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1, img2;
        RelativeLayout rl;
        AdView mAdView;
        TextView tv1, tv2, tv3, tv4, tv5, speedWind, dLocation, d;

        public ViewHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.container_daily0);
            img1 = (ImageView) itemView.findViewById(R.id.dCloudImage);
            tv1 = (TextView) itemView.findViewById(R.id.dSummary);
            tv2 = (TextView) itemView.findViewById(R.id.lastUpdate2);
            tv3 = (TextView) itemView.findViewById(R.id.temperature);
            tv4 = (TextView) itemView.findViewById(R.id.sunrise);
            tv5 = (TextView) itemView.findViewById(R.id.sunset);
            d = (TextView) itemView.findViewById(R.id.d);
            speedWind = (TextView) itemView.findViewById(R.id.speedWind);
            dLocation = (TextView) itemView.findViewById(R.id.dLocation);
            mAdView = (AdView) itemView.findViewById(R.id.adView);

        }
    }
}
