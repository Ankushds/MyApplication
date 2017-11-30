package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    View view;

    public MyAdapter5() {

    }

    @Override
    public MyAdapter5.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.adapter_5, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter5.ViewHolder holder, int position) {

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
