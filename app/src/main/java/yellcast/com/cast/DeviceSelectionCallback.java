package yellcast.com.cast;

import com.google.android.gms.cast.CastDevice;

public interface DeviceSelectionCallback {
    public void onDeselectDevice(CastDevice selectedDevice);
    public void onSelectDevice(CastDevice selectedDevice);
}
