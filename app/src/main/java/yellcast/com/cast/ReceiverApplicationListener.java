package yellcast.com.cast;

import static com.google.android.gms.cast.Cast.Listener;

/**
 * Created by marcbaechinger on 21.07.14.
 */
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
