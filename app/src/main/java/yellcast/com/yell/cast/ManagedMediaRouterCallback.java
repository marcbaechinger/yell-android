package yellcast.com.yell.cast;

import android.content.Context;
import android.support.v7.media.MediaRouter;

import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.api.GoogleApiClient;

/**
* Created by marcbaechinger on 21.07.14.
*/
class ManagedMediaRouterCallback extends MediaRouter.Callback {

    private ChromeCastManager manager;

    public ManagedMediaRouterCallback(ChromeCastManager manager, Context context) {
        this.manager = manager;
    }

    @Override
    public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
        CastDevice selectedDevice = CastDevice.getFromBundle(info.getExtras());
        manager.onRouteSelected(selectedDevice);
    }

    @Override
    public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
        manager.onRouteUnselected();
    }
}
