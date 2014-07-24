package yellcast.com.cast;

import static com.google.android.gms.cast.Cast.Listener;

public class ReceiverApplicationListener extends Listener {

    @Override
    public void onApplicationDisconnected(int statusCode) {
        super.onApplicationDisconnected(statusCode);
    }

    @Override
    public void onApplicationStatusChanged() {
        super.onApplicationStatusChanged();
    }

    @Override
    public void onVolumeChanged() {
        super.onVolumeChanged();
    }
}
