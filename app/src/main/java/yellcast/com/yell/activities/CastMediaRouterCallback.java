package yellcast.com.yell.activities;

import android.support.v7.media.MediaRouter;

import com.google.android.gms.cast.CastDevice;

/**
 * Created by marcbaechinger on 22.07.14.
 */
public class CastMediaRouterCallback extends MediaRouter.Callback {

    private YellActivity activity;

    public CastMediaRouterCallback(YellActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
        CastDevice selectedDevice = CastDevice.getFromBundle(info.getExtras());
        activity.onDeviceSelected(selectedDevice);
        String routeId = info.getId();
    }

    @Override
    public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
        CastDevice selectedDevice = CastDevice.getFromBundle(info.getExtras());
        activity.onDeviceUnselected(selectedDevice);
    }
}