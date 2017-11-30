package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyAdapter22 extends RecyclerView.Adapter<MyAdapter22.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    View view;

    public MyAdapter22() {

    }

    @Override
    public MyAdapter22.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.horly_data, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyAdapter22.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 47;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
