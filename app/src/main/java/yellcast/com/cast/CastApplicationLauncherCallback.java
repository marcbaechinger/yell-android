package yellcast.com.cast;

import android.os.Bundle;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;

import yellcast.com.yell.YellChannelCallback;

/**
 * Created by marcbaechinger on 21.07.14.
 */
public class CastApplicationLauncherCallback implements GoogleApiClient.ConnectionCallbacks {
    private static final String TAG = CastApplicationLauncherCallback.class.getCanonicalName();

    private boolean waitingForReconnect = false;
    private ChromeCastManager manager;
    private Cast.MessageReceivedCallback callback;

    public CastApplicationLauncherCallback(ChromeCastManager manager, Cast.MessageReceivedCallback callback) {
        this.manager = manager;
        this.callback = callback;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (waitingForReconnect) {
            waitingForReconnect = false;
            reconnectChannels();
        } else {
            manager.launchApplication(YellChannelCallback.NAMESPACE, callback);
        }
    }

    private void teardown() {

    }

    private void reconnectChannels() {
        
    }

    @Override
    public void onConnectionSuspended(int i) {
        waitingForReconnect = true;
    }
}
