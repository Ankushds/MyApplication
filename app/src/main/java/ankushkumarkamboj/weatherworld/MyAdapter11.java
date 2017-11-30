package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MyAdapter11 extends RecyclerView.Adapter<MyAdapter11.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    View view;
    public MyAdapter11(){

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

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
