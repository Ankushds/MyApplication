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

import java.text.SimpleDateFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context c;
    String lData;
    List<Double> m_ageList;
    List<String> m_phaseList;
    List<Integer> m_illumList;
    List<Integer> m_numList;
    SharedPreferences sf;
    SimpleDateFormat sdf2;
    long l;

    public MyAdapter(Context activity, List<Integer> n, String locationData, List<Double> age_list, List<String> phase_list, List<Integer> illum_list) {
        this.c = activity;
        this.lData = locationData;
        this.m_ageList = age_list;
        this.m_phaseList = phase_list;
        this.m_illumList = illum_list;
        this.m_numList = n;
        sf = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View v = layoutInflater.inflate(R.layout.adapter_5, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.loc.setText(lData);
        holder.angle.setText(m_phaseList.get(position));
        holder.ill.setText("Illumination:" + m_illumList.get(position));
        holder.days.setText("Age:" + m_ageList.get(0));

        switch (m_numList.get(position)) {
            case 0:
                holder.moonImage.setImageResource(R.drawable.m10);
                break;
            case 1:
                holder.moonImage.setImageResource(R.drawable.m18);
                break;
            case 2:
                holder.moonImage.setImageResource(R.drawable.m17);
                break;
            case 3:
                holder.moonImage.setImageResource(R.drawable.m15);
                break;
            case 4:
                holder.moonImage.setImageResource(R.drawable.m14);
                break;
            case 5:
                holder.moonImage.setImageResource(R.drawable.m13);
                break;
            case 6:
                holder.moonImage.setImageResource(R.drawable.m12);
                break;
            case 7:
                holder.moonImage.setImageResource(R.drawable.m11);
                break;
        }
        long timestamp = sf.getLong("timestamp5", 00000);
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

        sdf2 = new SimpleDateFormat("E");

        l = System.currentTimeMillis();
        int i = 24 * 60 * 60 * 1000;
        holder.day1.setText(sdf2.format(l + i));
        holder.day2.setText(sdf2.format(l + (2 * i)));
        holder.day3.setText(sdf2.format(l + (3 * i)));
        holder.day4.setText(sdf2.format(l + (4 * i)));
        holder.day5.setText(sdf2.format(l + (5 * i)));
        switch (m_numList.get(1)) {
            case 0:
                holder.ml1.setImageResource(R.drawable.m10);
                break;
            case 1:
                holder.ml1.setImageResource(R.drawable.m18);
                break;
            case 2:
                holder.ml1.setImageResource(R.drawable.m17);
                break;
            case 3:
                holder.ml1.setImageResource(R.drawable.m15);
                break;
            case 4:
                holder.ml1.setImageResource(R.drawable.m14);
                break;
            case 5:
                holder.ml1.setImageResource(R.drawable.m13);
                break;
            case 6:
                holder.ml1.setImageResource(R.drawable.m12);
                break;
            case 7:
                holder.ml1.setImageResource(R.drawable.m11);
                break;
        }
        switch (m_numList.get(2)) {
            case 0:
                holder.ml2.setImageResource(R.drawable.m10);
                break;
            case 1:
                holder.ml2.setImageResource(R.drawable.m18);
                break;
            case 2:
                holder.ml2.setImageResource(R.drawable.m17);
                break;
            case 3:
                holder.ml2.setImageResource(R.drawable.m15);
                break;
            case 4:
                holder.ml2.setImageResource(R.drawable.m14);
                break;
            case 5:
                holder.ml2.setImageResource(R.drawable.m13);
                break;
            case 6:
                holder.ml2.setImageResource(R.drawable.m12);
                break;
            case 7:
                holder.ml2.setImageResource(R.drawable.m11);
                break;
        }
        switch (m_numList.get(3)) {
            case 0:
                holder.ml3.setImageResource(R.drawable.m10);
                break;
            case 1:
                holder.ml3.setImageResource(R.drawable.m18);
                break;
            case 2:
                holder.ml3.setImageResource(R.drawable.m17);
                break;
            case 3:
                holder.ml3.setImageResource(R.drawable.m15);
                break;
            case 4:
                holder.ml3.setImageResource(R.drawable.m14);
                break;
            case 5:
                holder.ml3.setImageResource(R.drawable.m13);
                break;
            case 6:
                holder.ml3.setImageResource(R.drawable.m12);
                break;
            case 7:
                holder.ml3.setImageResource(R.drawable.m11);
                break;
        }
        switch (m_numList.get(4)) {
            case 0:
                holder.ml4.setImageResource(R.drawable.m10);
                break;
            case 1:
                holder.ml4.setImageResource(R.drawable.m18);
                break;
            case 2:
                holder.ml4.setImageResource(R.drawable.m17);
                break;
            case 3:
                holder.ml4.setImageResource(R.drawable.m15);
                break;
            case 4:
                holder.ml4.setImageResource(R.drawable.m14);
                break;
            case 5:
                holder.ml4.setImageResource(R.drawable.m13);
                break;
            case 6:
                holder.ml4.setImageResource(R.drawable.m12);
                break;
            case 7:
                holder.ml4.setImageResource(R.drawable.m11);
                break;
        }
        switch (m_numList.get(5)) {
            case 0:
                holder.ml5.setImageResource(R.drawable.m10);
                break;
            case 1:
                holder.ml5.setImageResource(R.drawable.m18);
                break;
            case 2:
                holder.ml5.setImageResource(R.drawable.m17);
                break;
            case 3:
                holder.ml5.setImageResource(R.drawable.m15);
                break;
            case 4:
                holder.ml5.setImageResource(R.drawable.m14);
                break;
            case 5:
                holder.ml5.setImageResource(R.drawable.m13);
                break;
            case 6:
                holder.ml5.setImageResource(R.drawable.m12);
                break;
            case 7:
                holder.ml5.setImageResource(R.drawable.m11);
                break;
        }

        holder.angle1.setText(m_phaseList.get(1));
        holder.angle2.setText(m_phaseList.get(2));
        holder.angle3.setText(m_phaseList.get(3));
        holder.angle4.setText(m_phaseList.get(4));
        holder.angle5.setText(m_phaseList.get(5));
        holder.i1.setText(String.valueOf("Illumination:" + m_illumList.get(1)));
        holder.i2.setText(String.valueOf("Illunination:" + m_illumList.get(2)));
        holder.i3.setText(String.valueOf("Illunination:" + m_illumList.get(3)));
        holder.i4.setText(String.valueOf("Illunination:" + m_illumList.get(4)));
        holder.i5.setText(String.valueOf("Illunination:" + m_illumList.get(5)));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView loc, lastUpdate, time, angle, ill, days;
        TextView day1, day2, day3, day4, day5, day6, i1, i2, i3, i4, i5, i6;
        TextView angle1, angle2, angle3, angle4, angle5, angle6;
        ImageView ml1, ml2, ml3, ml4, ml5, ml6, moonImage;
        RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            loc = (TextView) itemView.findViewById(R.id.loc);
            lastUpdate = (TextView) itemView.findViewById(R.id.lUpdae);
            angle = (TextView) itemView.findViewById(R.id.angle);
            ill = (TextView) itemView.findViewById(R.id.ill);
            days = (TextView) itemView.findViewById(R.id.days);
            day1 = (TextView) itemView.findViewById(R.id.day1);
            day2 = (TextView) itemView.findViewById(R.id.day2);
            day3 = (TextView) itemView.findViewById(R.id.day3);
            day4 = (TextView) itemView.findViewById(R.id.day4);
            day5 = (TextView) itemView.findViewById(R.id.day5);
            i1 = (TextView) itemView.findViewById(R.id.i1);
            i2 = (TextView) itemView.findViewById(R.id.i2);
            i3 = (TextView) itemView.findViewById(R.id.i3);
            i4 = (TextView) itemView.findViewById(R.id.i4);
            i5 = (TextView) itemView.findViewById(R.id.i5);
            angle1 = (TextView) itemView.findViewById(R.id.angle1);
            angle2 = (TextView) itemView.findViewById(R.id.angle2);
            angle3 = (TextView) itemView.findViewById(R.id.angle3);
            angle4 = (TextView) itemView.findViewById(R.id.angle4);
            angle5 = (TextView) itemView.findViewById(R.id.angle5);
            ml1 = (ImageView) itemView.findViewById(R.id.mI1);
            ml2 = (ImageView) itemView.findViewById(R.id.mI2);
            ml3 = (ImageView) itemView.findViewById(R.id.mI3);
            ml4 = (ImageView) itemView.findViewById(R.id.mI4);
            ml5 = (ImageView) itemView.findViewById(R.id.mI5);
            moonImage = (ImageView) itemView.findViewById(R.id.moonImage);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl);
        }
    }
}
