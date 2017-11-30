package ankushkumarkamboj.weatherworld;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectivityInfo {
    ConnectivityManager cm;
    NetworkInfo info;
    Context c;
    boolean bool, ip;
    InetAddress ipAddr = null;

    public ConnectivityInfo(Context context) {
        this.c = context;
    }

    public boolean getMyNetworkInfo() {
        cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            bool = true;

        } else {
            bool = false;
        }
        Log.i("manegar", String.valueOf(bool));
        return bool;
    }

    public boolean isInternetAvailable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ipAddr = InetAddress.getByName("google.com");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (ipAddr != null) {
                    ip = true;
                } else {
                    ip = false;
                }
            }
        }).start();
        return ip;
    }
}

