package ankushkamboj.sevicebiinder;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.net.URL;

public class MyService extends Service {
    private  final IBinder binder = new MyBinder();
    public URL[] urls;

    public class MyBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new DoBackgroundTask().execute(String.valueOf(urls));
        return START_STICKY;
    }
    private class DoBackgroundTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }


    }
}
