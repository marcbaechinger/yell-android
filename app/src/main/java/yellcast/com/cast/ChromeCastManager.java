package yellcast.com.cast;

import android.content.Context;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.util.Log;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.IOException;

public class ChromeCastManager {

    private static final String TAG = ChromeCastManager.class.getCanonicalName();
    private GoogleApiClient castApiClient;

    private MediaRouter mediaRouter;
    private MediaRouteSelector mediaRouteSelector;
    private MediaRouter.Callback mediaRouterCallback;

    private CastDevice selectedDevice;

    private Context context;
    private String applicationId;
    private ApplicationMetadata applicationMetadata;
    private String sessionId;
    private DeviceSelectionCallback deviceSelectionCallback;
    private Callback apiConnectionCallbackDelegate;


    public ChromeCastManager(Context context, String applicationId,
                             Callback apiConnectionCallbackDelegate,
                             DeviceSelectionCallback deviceSelectionCallback) {

        this.apiConnectionCallbackDelegate = apiConnectionCallbackDelegate;
        this.deviceSelectionCallback = deviceSelectionCallback;
        this.context = context;
        this.applicationId = applicationId;

        mediaRouterCallback = new ManagedMediaRouterCallback(this, context);

        // get media router instance
        mediaRouter = MediaRouter.getInstance(context);
        mediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast(applicationId))
                .build();
    }


    public GoogleApiClient getCastApiClient() {
        return castApiClient;
    }

    private void setSelectedDevice(CastDevice selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void registerMediaRouteSelector(MediaRouteActionProvider mediaRouteActionProvider) {
        mediaRouteActionProvider.setRouteSelector(mediaRouteSelector);
    }

    public void connect() {
        mediaRouter.addCallback(mediaRouteSelector, mediaRouterCallback,
                MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN);
    }

    public void disconnect() {
        mediaRouter.removeCallback(mediaRouterCallback);
    }

    public void launchApplication(final Callback successCallback) {
        try {
            Cast.CastApi.launchApplication(getCastApiClient(), getApplicationId(), false)
                    .setResultCallback(new ResultCallback<Cast.ApplicationConnectionResult>() {
                        @Override
                        public void onResult(Cast.ApplicationConnectionResult result) {
                            Status status = result.getStatus();
                            if (status.isSuccess()) {
                                applicationMetadata = result.getApplicationMetadata();
                                sessionId = result.getSessionId();
                                successCallback.call();
                            } else {
                                applicationMetadata = null;
                                sessionId = null;
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Failed to launch application", e);
        }
    }
    public void shutdownApplication() {
        if (applicationMetadata != null) {
            Cast.CastApi.stopApplication(getCastApiClient());
        } else {
            Log.i(TAG, "can't shutdown application. Is it launched already?");
        }
    }

    public void registerMessageReceiver(String namespace, Cast.MessageReceivedCallback receiver) {
        if (applicationMetadata == null) {
            Log.w(TAG, "Can't register message receiver. Cast application not launched yet.");
        } else if (applicationMetadata.isNamespaceSupported(namespace)) {
            try {
                Cast.CastApi.setMessageReceivedCallbacks(getCastApiClient(), namespace, receiver);
            } catch (IOException e) {
                Log.e(TAG, "Exception while creating channel", e);
            }
        } else {
            Log.w(TAG, "receiver application does not support namespace " + namespace);
        }
    }

    public void unregisterMessageReceiver(String namespace) {
        if (applicationMetadata != null) {
            try {
                Cast.CastApi.removeMessageReceivedCallbacks(getCastApiClient(), namespace);
            } catch (IOException e) {
                Log.e(TAG, "error while removing message receiver handler of namespace: " + namespace);
            }
        }
    }

    public void sendMessage(final String message, final String namespace) {
        if (castApiClient != null) {
            try {
                Cast.CastApi.sendMessage(castApiClient, namespace, message)
                        .setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Log.d(TAG, "message sent: " + message);
                            }
                        });
            } catch (Exception e) {
                Log.e(TAG, "Exception while sending message", e);
            }
        } else {
            Log.i(TAG, "castApiClient not active. Are you connected to a cast device?");
        }
    }

    public boolean isConnected() {
        return sessionId != null && getCastApiClient() != null && getCastApiClient().isConnected();
    }

    public void onRouteSelected(CastDevice device) {
        Cast.CastOptions.Builder apiOptionsBuilder = Cast.CastOptions
                .builder(device, new ReceiverApplicationListener());

        CastApiErrorCallback connectionFailedCallback = new CastApiErrorCallback();
        castApiClient = new GoogleApiClient.Builder(context)
                .addApi(Cast.API, apiOptionsBuilder.build())
                .addConnectionCallbacks(new ApiConnectionCallback(apiConnectionCallbackDelegate))
                .addOnConnectionFailedListener(connectionFailedCallback)
                .build();

        castApiClient.connect();
        this.setSelectedDevice(device);
        deviceSelectionCallback.onSelectDevice(device);
    }

    public void onRouteUnselected() {
        if (castApiClient != null) {
            castApiClient.disconnect();
            castApiClient = null;
        }
        deviceSelectionCallback.onDeselectDevice(selectedDevice);
        selectedDevice = null;
        applicationMetadata = null;
        sessionId = null;
    }
}
