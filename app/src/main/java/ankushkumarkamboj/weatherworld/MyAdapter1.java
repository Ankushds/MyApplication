package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.ViewHolder> {
    double mPrecipIntensity, mPrecipProbability, mTemperature, mDewPoint,
            mHumidity, mWindSpeed, mVisibility, mPressure;
    String str3, str4, str5, mIcon, str7, mLocationData;
    Context c;
    LayoutInflater inflater;
    View view;
    List<String> mIcon_list;
    List<Double> mMinT_list;
    List<Double> mMxT_list;
    Context context;
    SharedPreferences sf;
    SimpleDateFormat sdf;
    MyDatabase mdb;

    public MyAdapter1(Context context, String locationData, String icon, double
            precipIntensity, double precipProbability, double temperature, double dewPoint, double humidity,
                      double windSpeed, double visibility,
                      double pressure, List<String> dIcon_list, List<Double> minTemp_list,
                      List<Double> maxTemp_list) {
        this.c = context;
        this.mLocationData = locationData;
        this.mIcon = icon;
        this.mPrecipIntensity = precipIntensity;
        this.mPrecipProbability = precipProbability;
        this.mTemperature = temperature;
        this.mDewPoint = dewPoint;
        this.mHumidity = humidity;
        this.mWindSpeed = windSpeed;
        this.mVisibility = visibility;
        this.mPressure = pressure;
        this.mIcon_list = dIcon_list;
        this.mMinT_list = minTemp_list;
        this.mMxT_list = maxTemp_list;
        sf = PreferenceManager.getDefaultSharedPreferences(c);
        sdf = new SimpleDateFormat("E");

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.weather_data, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.location.setText(mLocationData);
        switch (mIcon) {
            case "clear-day":
                holder.cloud.setImageResource(R.drawable.sun);
                break;
            case "rain":
                holder.cloud.setImageResource(R.drawable.rain);
                break;
            case "cloudy":
                holder.cloud.setImageResource(R.drawable.cloudy);
                break;
            case "partly-cloudy-day":
                holder.cloud.setImageResource(R.drawable.partlycloud);
                break;
            case "fog":
                holder.cloud.setImageResource(R.drawable.fog);
                break;
            case "clear-night":
                holder.cloud.setImageResource(R.drawable.clearnight);
                break;
            case "partly-cloudy-night":
                holder.cloud.setImageResource(R.drawable.cloudynight);
                break;
            case "snow":
                holder.cloud.setImageResource(R.drawable.snow);
        }

        long timestamp = sf.getLong("timestamp1", 00000);
        switch (sf.getString("dateFormat", "")) {

            case "MMM dd yyyy E":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("MMM dd yyyy E hh:mm:ss ").format(timestamp));
                break;
            case "E MMM dd yyyy":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("E MMM dd yyyy").format(timestamp));
                break;
            case "E dd MMM yyyy":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("E dd MMM yyyy").format(timestamp));
                break;
            case "E yyyy MMM dd":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("E yyyy MMM dd").format(timestamp));
                break;
            case "E yyyy dd MMM":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("E yyyy dd MMM").format(timestamp));
                break;
            case "dd MMM yyyy":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("dd MMM yyyy").format(timestamp));
                break;
            case "MMM dd yyyy":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("MMM dd yyyy").format(timestamp));
                break;
            case "yyyy dd MMM":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("yyyy dd MMM").format(timestamp));
                break;
            case "dd yyyy MMM":
                holder.lastUpdate.setText("Last Update:" + new SimpleDateFormat("dd yyyy MMM").format(timestamp));
        }

        switch (sf.getString("temp", "")) {
            case "F":
                holder.temp.setText(String.valueOf(mTemperature + "F"));
                holder.max_min.setText(mMinT_list.get(0) + "/" + mMxT_list.get(0) + "F");
                holder.dewPoint.setText("DewPoint:" + mDewPoint + "F");
                holder.dt1.setText(mMinT_list.get(1) + "/" + mMxT_list.get(1) + "F");
                holder.dt2.setText(mMinT_list.get(2) + "/" + mMxT_list.get(2) + "F");
                holder.dt3.setText(mMinT_list.get(3) + "/" + mMxT_list.get(3) + "F");
                holder.dt4.setText(mMinT_list.get(4) + "/" + mMxT_list.get(4) + "F");
                holder.dt5.setText(mMinT_list.get(5) + "/" + mMxT_list.get(5) + "F");
                break;
            case "C":
                int d = (int) (mTemperature / 3.4);
                int d1 = (int) (mMinT_list.get(0) / 3.4);
                int d2 = (int) (mMxT_list.get(0) / 3.4);
                int d4 = (int) (mDewPoint / 3.4);
                holder.temp.setText(String.valueOf(d + "C"));
                holder.max_min.setText(d1 + "/" + d2 + "C");
                holder.dewPoint.setText("DewPoint:" + d4 + "C");
                holder.dt1.setText((int) (mMinT_list.get(1) / 3.4) + "/" + (int) (mMxT_list.get(1) / 3.4) + "C");
                holder.dt2.setText((int) (mMinT_list.get(2) / 3.4) + "/" + (int) (mMxT_list.get(2) / 3.4) + "C");
                holder.dt3.setText((int) (mMinT_list.get(3) / 3.4) + "/" + (int) (mMxT_list.get(3) / 3.4) + "C");
                holder.dt4.setText((int) (mMinT_list.get(4) / 3.4) + "/" + (int) (mMxT_list.get(4) / 3.4) + "C");
                holder.dt5.setText((int) (mMinT_list.get(5) / 3.4) + "/" + (int) (mMxT_list.get(5) / 3.4) + "C");
        }
        holder.humidity.setText("Humidity:" + mHumidity + "%");
        switch (sf.getString("windSpeed", "km/h")) {
            case "km/h":
                holder.wind.setText("Wind:" + mWindSpeed + "km/h");
                break;
            case "mph":
                int d3 = (int) (mWindSpeed / 1.5);
                holder.wind.setText("Wind:" + d3 + "mph");
                break;
            case "m/s":
                int d4 = (int) (mWindSpeed / 3);
                holder.wind.setText("Wind:" + d4 + "m/s");
        }
        str3 = String.valueOf(mPrecipIntensity);
        str4 = String.valueOf(mPrecipProbability);
        switch (sf.getString("precipUnit", "mm")) {
            case "mm":
                holder.pIntensity.setText("PrecipIntensity:" + str3 + "mm");
                holder.pProbab.setText("PrecipProbability:" + str4 + "mm");
                break;
            case "in":
                double d6 = mPrecipIntensity / 2.21;
                double d7 = mPrecipProbability / 2.21;
                holder.pIntensity.setText("PrecipIntensity:" + d6 + "in");
                holder.pProbab.setText("PrecipProbability:" + d7 + "in");
        }
        str5 = String.valueOf(mVisibility);
        switch (sf.getString("visibility", "km")) {
            case "km":
                holder.visibility.setText("Visibility:" + str5 + "km");
                break;
            case "mi":
                int d5 = (int) (mVisibility / 1.5);
                holder.visibility.setText("Visibility:" + d5 + "mi");
        }
        str7 = String.valueOf(mPressure);
        switch (sf.getString("pressure", "")) {
            case "hpa":
                holder.pressure.setText("Pressure:" + str7 + "hpa");
                break;
            case "kgf/m2":
                int d8 = (int) (mPressure * 10.20);
                holder.pressure.setText("Pressure:" + d8 + "kfg/m2");
        }
        long t1, m;
        m = 24 * 60 * 60 * 1000;
        t1 = System.currentTimeMillis();
        holder.d1.setText(sdf.format(m + t1));
        holder.d2.setText(sdf.format(2 * m + t1));
        holder.d3.setText(sdf.format(3 * m + t1));
        holder.d4.setText(sdf.format(4 * m + t1));
        holder.d5.setText(sdf.format(5 * m + t1));
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(t1);
        calendar.setTime(date);
        String s0 = sf.getString("cal0", "");
        String s1 = sf.getString("cal1", "");
        com.luckycatlabs.sunrisesunset.dto.Location location = new com.luckycatlabs.sunrisesunset.dto.Location(s0, s1);
        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, calendar.getTimeZone());
        holder.sunrise.setText("SunRise:" + calculator.getOfficialSunriseForDate(calendar));
        holder.sunset.setText("SunSet:" + calculator.getOfficialSunsetForDate(calendar));

        switch (mIcon_list.get(1)) {
            case "clear-day":
                holder.sun1.setImageResource(R.drawable.sun);
                break;
            case "rain":
                holder.sun1.setImageResource(R.drawable.rain);
                break;
            case "cloudy":
                holder.sun1.setImageResource(R.drawable.cloudy);
            case "partly-cloudy-day":
                holder.sun1.setImageResource(R.drawable.partlycloud);
                break;
            case "fog":
                holder.sun1.setImageResource(R.drawable.fog);
                break;
            case "clear-night":
                holder.sun1.setImageResource(R.drawable.clearnight);
                break;
            case "partly-cloudy-night":
                holder.sun1.setImageResource(R.drawable.cloudy);
                break;
            case "snow":
                holder.sun1.setImageResource(R.drawable.snow);
        }

        switch (mIcon_list.get(2)) {
            case "clear-day":
                holder.sun2.setImageResource(R.drawable.sun);
                break;
            case "rain":
                holder.sun2.setImageResource(R.drawable.rain);
                break;
            case "cloudy":
                holder.sun2.setImageResource(R.drawable.cloudy);
            case "partly-cloudy-day":
                holder.sun2.setImageResource(R.drawable.partlycloud);
                break;
            case "fog":
                holder.sun2.setImageResource(R.drawable.fog);
                break;
            case "clear-night":
                holder.sun2.setImageResource(R.drawable.clearnight);
                break;
            case "partly-cloudy-night":
                holder.sun2.setImageResource(R.drawable.cloudy);
                break;
            case "snow":
                holder.sun2.setImageResource(R.drawable.snow);
        }

        switch (mIcon_list.get(3)) {
            case "clear-day":
                holder.sun3.setImageResource(R.drawable.sun);
                break;
            case "rain":
                holder.sun3.setImageResource(R.drawable.rain);
                break;
            case "cloudy":
                holder.sun3.setImageResource(R.drawable.cloudy);
            case "partly-cloudy-day":
                holder.sun3.setImageResource(R.drawable.partlycloud);
                break;
            case "fog":
                holder.sun3.setImageResource(R.drawable.fog);
                break;
            case "clear-night":
                holder.sun3.setImageResource(R.drawable.clearnight);
                break;
            case "partly-cloudy-night":
                holder.sun3.setImageResource(R.drawable.cloudy);
                break;
            case "snow":
                holder.sun3.setImageResource(R.drawable.snow);
        }

        switch (mIcon_list.get(4)) {
            case "clear-day":
                holder.sun4.setImageResource(R.drawable.sun);
                break;
            case "rain":
                holder.sun4.setImageResource(R.drawable.rain);
                break;
            case "cloudy":
                holder.sun4.setImageResource(R.drawable.cloudy);
            case "partly-cloudy-day":
                holder.sun4.setImageResource(R.drawable.partlycloud);
                break;
            case "fog":
                holder.sun4.setImageResource(R.drawable.fog);
                break;
            case "clear-night":
                holder.sun4.setImageResource(R.drawable.clearnight);
                break;
            case "partly-cloudy-night":
                holder.sun4.setImageResource(R.drawable.cloudy);
                break;
            case "snow":
                holder.sun4.setImageResource(R.drawable.snow);
        }

        switch (mIcon_list.get(5)) {
            case "clear-day":
                holder.sun5.setImageResource(R.drawable.sun);
                break;
            case "rain":
                holder.sun5.setImageResource(R.drawable.rain);
                break;
            case "cloudy":
                holder.sun5.setImageResource(R.drawable.cloudy);
            case "partly-cloudy-day":
                holder.sun5.setImageResource(R.drawable.partlycloud);
                break;
            case "fog":
                holder.sun5.setImageResource(R.drawable.fog);
                break;
            case "clear-night":
                holder.sun5.setImageResource(R.drawable.clearnight);
                break;
            case "partly-cloudy-night":
                holder.sun5.setImageResource(R.drawable.cloudy);
                break;
            case "snow":
                holder.sun5.setImageResource(R.drawable.snow);
        }

        holder.s1.setText(mIcon_list.get(1));
        holder.s2.setText(mIcon_list.get(2));
        holder.s3.setText(mIcon_list.get(3));
        holder.s4.setText(mIcon_list.get(4));
        holder.s5.setText(mIcon_list.get(5));

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView location, lastUpdate, time, temp, max_min, dewPoint, humidity, wind, pIntensity, pProbab, visibility, pressure;
        TextView dt1, dt2, dt3, dt4, dt5, s1, s2, s3, s4, s5, d1, d2, d3, d4, d5, sunrise, sunset;
        ImageView gps, cloud, sun1, sun2, sun3, sun4, sun5;

        public ViewHolder(View itemView) {
            super(itemView);
            location = (TextView) itemView.findViewById(R.id.location);
            lastUpdate = (TextView) itemView.findViewById(R.id.lastUpdate);
            time = (TextView) itemView.findViewById(R.id.time);
            temp = (TextView) itemView.findViewById(R.id.temperature1);
            max_min = (TextView) itemView.findViewById(R.id.max_min);
            dewPoint = (TextView) itemView.findViewById(R.id.dewPoint);
            humidity = (TextView) itemView.findViewById(R.id.humidity1);
            wind = (TextView) itemView.findViewById(R.id.wind);
            pIntensity = (TextView) itemView.findViewById(R.id.pIntensity);
            pProbab = (TextView) itemView.findViewById(R.id.pProb);
            visibility = (TextView) itemView.findViewById(R.id.visibility1);
            pressure = (TextView) itemView.findViewById(R.id.pressure1);
            gps = (ImageView) itemView.findViewById(R.id.gpsImg);
            cloud = (ImageView) itemView.findViewById(R.id.cloud);
            d1 = (TextView) itemView.findViewById(R.id.d1);
            d2 = (TextView) itemView.findViewById(R.id.d2);
            d3 = (TextView) itemView.findViewById(R.id.d3);
            d4 = (TextView) itemView.findViewById(R.id.d4);
            d5 = (TextView) itemView.findViewById(R.id.d5);
            sun1 = (ImageView) itemView.findViewById(R.id.sun1);
            sun2 = (ImageView) itemView.findViewById(R.id.sun2);
            sun3 = (ImageView) itemView.findViewById(R.id.sun3);
            sun4 = (ImageView) itemView.findViewById(R.id.sun4);
            sun5 = (ImageView) itemView.findViewById(R.id.sun5);
            dt1 = (TextView) itemView.findViewById(R.id.dt1);
            dt2 = (TextView) itemView.findViewById(R.id.dt2);
            dt3 = (TextView) itemView.findViewById(R.id.dt3);
            dt4 = (TextView) itemView.findViewById(R.id.dt4);
            dt5 = (TextView) itemView.findViewById(R.id.dt5);
            s1 = (TextView) itemView.findViewById(R.id.s1);
            s2 = (TextView) itemView.findViewById(R.id.s2);
            s3 = (TextView) itemView.findViewById(R.id.s3);
            s4 = (TextView) itemView.findViewById(R.id.s4);
            s5 = (TextView) itemView.findViewById(R.id.s5);
            sunrise = (TextView) itemView.findViewById(R.id.sunrise);
            sunset = (TextView) itemView.findViewById(R.id.sunset);
        }
    }
}
