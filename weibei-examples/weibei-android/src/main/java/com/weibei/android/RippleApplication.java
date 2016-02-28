
package com.weibei.android;

import android.app.Application;
import com.weibei.android.client.AndroidClient;

public class WeibeiApplication extends Application {
    public AndroidClient client;
    public AndroidClient getClient() {
        return client;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        client = new AndroidClient();
        client.connect("wss://s-east.weibei.com");
    }

}
