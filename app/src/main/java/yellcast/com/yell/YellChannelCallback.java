package yellcast.com.yell;

import android.util.Log;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yellcast.com.yell.model.YellNode;
import yellcast.com.yell.model.YellNodeType;

public class YellChannelCallback implements Cast.MessageReceivedCallback {

    private static final String TAG = YellChannelCallback.class.getCanonicalName();

    public static final String NAMESPACE = "urn:x-cast:com.yellcast.v1.protocol";
    private final YellApplication application;

    public YellChannelCallback(YellApplication application) {
        this.application = application;
    }

    @Override
    public void onMessageReceived(CastDevice castDevice, String namespace, String message) {
        Log.d(TAG, message);
        try {
            List<YellNode> nodes = new ArrayList<YellNode>();

            JSONObject jsonMsg = new JSONObject(message);
            if ("status".equals(jsonMsg.getString("action"))) {
                JSONArray yells = jsonMsg.getJSONArray("yells");
                for (int i = 0; i < yells.length(); i++) {
                    nodes.add(map(yells.getJSONObject(i)));
                }
                application.initModel(nodes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private YellNode map(JSONObject jsonYell) throws JSONException {
        YellNode yell = YellNode.newInstance();

        yell.setUuid(jsonYell.getString("uuid"));
        yell.setUrl(jsonYell.getString("url"));
        yell.setLabel(jsonYell.getString("label"));

        String type = jsonYell.getString("type");
        if (type != null) {
            yell.setType(YellNodeType.valueOf(type));
        }

        return yell;
    }
}
