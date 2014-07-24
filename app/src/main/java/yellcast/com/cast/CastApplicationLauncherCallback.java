package yellcast.com.cast;

import android.os.Bundle;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;

import yellcast.com.yell.YellChannelCallback;

public class CastApplicationLauncherCallback implements GoogleApiClient.ConnectionCallbacks {
    private boolean waitingForReconnect = false;
    private ChromeCastManager manager;
    private Cast.MessageReceivedCallback messageReceivedCallback;

    public CastApplicationLauncherCallback(ChromeCastManager manager, Cast.MessageReceivedCallback messageReceivedCallback) {
        this.manager = manager;
        this.messageReceivedCallback = messageReceivedCallback;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (waitingForReconnect) {
            waitingForReconnect = false;
        } else {
            manager.launchApplication(new Callback() {
                @Override
                public void call() {
                    manager.registerMessageReceiver(YellChannelCallback.NAMESPACE, messageReceivedCallback);
                }
            });
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        waitingForReconnect = true;
    }
}
