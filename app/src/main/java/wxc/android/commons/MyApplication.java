package wxc.android.commons;

import android.app.Application;

import wxc.android.commons.lib.rxapi.RxApiClient;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RxApiClient.setBaseUrl("https://api.douban.com/v2/movie/");
    }
}
