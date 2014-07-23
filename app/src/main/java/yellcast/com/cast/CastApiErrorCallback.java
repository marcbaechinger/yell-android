package yellcast.com.cast;

import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by marcbaechinger on 21.07.14.
 */
public class CastApiErrorCallback implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = CastApiErrorCallback.class.getCanonicalName();

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "error while connection cast API");
    }
}
