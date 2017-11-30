package ankushkumarkamboj.weatherworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Fragment4 extends Fragment {
    SharedPreferences sf;
    TextView email;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_4, container, false);
        AdView mAdView = (AdView) v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        sf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        email = (TextView) v.findViewById(R.id.email);
        email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:ankushkambojds@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Issue:");
                startActivity(intent);
            }
        });
    }

}
