package com.weibei.android.client;

import com.weibei.android.logging.AndroidHandler;
import com.weibei.client.Client;
import com.weibei.client.transport.impl.JavaWebSocketTransportImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidClient extends Client {
    static {
        Logger logger = Client.logger;
        AndroidHandler handler = new AndroidHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
    }
    public AndroidClient() {
        super(new JavaWebSocketTransportImpl());
    }
}
