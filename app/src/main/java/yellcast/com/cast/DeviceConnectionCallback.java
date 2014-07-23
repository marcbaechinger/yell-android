package yellcast.com.cast;

import com.google.android.gms.cast.CastDevice;

/**
 * Created by marcbaechinger on 23.07.14.
 */
public interface DeviceConnectionCallback {
    public void onUnselectDevice(CastDevice selectedDevice);
    public void onSelectDevice(CastDevice selectedDevice);
}
