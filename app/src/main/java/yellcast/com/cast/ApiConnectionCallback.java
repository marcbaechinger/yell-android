package yellcast.com.cast;

import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;

public class ApiConnectionCallback implements GoogleApiClient.ConnectionCallbacks {
    private final Callback connectionCallback;
    private boolean waitingForReconnect = false;

    public ApiConnectionCallback(Callback apiConnectionCallback) {
        this.connectionCallback = apiConnectionCallback;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (waitingForReconnect) {
            waitingForReconnect = false;
        } else {
            connectionCallback.call();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        waitingForReconnect = true;
    }
}
