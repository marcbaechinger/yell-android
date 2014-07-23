package yellcast.com.yell;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.cast.CastDevice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yellcast.com.cast.ChromeCastManager;
import yellcast.com.cast.DeviceConnectionCallback;
import yellcast.com.yell.model.YellModel;
import yellcast.com.yell.model.YellModelListener;
import yellcast.com.yell.model.YellNode;

/**
 * Created by marcbaechinger on 23.07.14.
 */
public class YellApplication extends Application implements YellModelListener {

    private static final String APPLICATION_ID = "4FF83149";
    private final YellModel model;

    private ChromeCastManager chromeCastManager;

    public YellApplication() {
        model = new YellModel();
        model.addYellModelListener(this);
    }

    public ChromeCastManager getChromeCastManager() {
        if (chromeCastManager == null) {
            chromeCastManager = new ChromeCastManager(new YellChannelCallback(this), new DeviceConnectionCallback() {
                @Override
                public void onUnselectDevice(CastDevice selectedDevice) {
                    initModel(new ArrayList<YellNode>());
                }
                @Override
                public void onSelectDevice(CastDevice selectedDevice) {}
            });
            chromeCastManager.init(this, APPLICATION_ID);
        }
        return chromeCastManager;
    }

    public void initModel(List<YellNode> initialNodes) {
        model.initModel(initialNodes);
    }

    public void sendAddYellNode(YellNode node) {
        if (chromeCastManager.isConnected()) {
            try {
                JSONObject message = new JSONObject();
                message.put("action", "add");
                message.put("node", node.toJson());
                chromeCastManager.sendMessage(message.toString(), YellChannelCallback.NAMESPACE);
            } catch (JSONException e) {
                Log.e("error creating json message", e.getMessage());
            }
        }
    }

    public void sendRemoveYellNode(YellNode node) {
        if (chromeCastManager.isConnected()) {
            try {
                JSONObject message = new JSONObject();
                message.put("action", "remove");
                message.put("node", node.toJson());
                chromeCastManager.sendMessage(message.toString(), YellChannelCallback.NAMESPACE);
            } catch (JSONException e) {
                Log.e("error creating json message", e.getMessage());
            }
        }
    }

    @Override
    public void addYellNode(YellNode node) {
        sendAddYellNode(node);
    }

    @Override
    public void removeYellNode(YellNode node) {
        sendRemoveYellNode(node);
    }

    @Override
    public void init(List<YellNode> nodes) {}

    public YellModel getModel() {
        return model;
    }
}
