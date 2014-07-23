package yellcast.com.yell;

import android.app.Application;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import yellcast.com.yell.cast.ChromeCastManager;
import yellcast.com.yell.model.YellNode;
import yellcast.com.yell.model.YellNodeType;

/**
 * Created by marcbaechinger on 23.07.14.
 */
public class YellApplication extends Application {

    private static final String APPLICATION_ID = "4FF83149";

    private ChromeCastManager chromeCastManager;
    private List<YellNode> nodes;
    private Set<YellModelListener> listeners;

    public YellApplication() {
        nodes = new ArrayList<YellNode>();
        listeners = new HashSet<YellModelListener>();
    }

    public List<YellNode> getNodes() {
        ArrayList<YellNode> dest = new ArrayList<YellNode>();
        for (YellNode node : nodes) {
            dest.add(node);
        }
        return dest;
    }

    public ChromeCastManager getChromeCastManager() {
        if (chromeCastManager == null) {
            chromeCastManager = new ChromeCastManager(new YellChannel(this));
            chromeCastManager.init(this, APPLICATION_ID);
        }
        return chromeCastManager;
    }

    public void add(YellNode node) {
        if (nodes.add(node)) {
            sendAddYellNode(node);
            for (YellModelListener listener : listeners) {
                listener.addYellNode(node);
            }
        }
    }

    public void remove(YellNode node) {
        if (nodes.remove(node)) {
            sendRemoveYellNode(node);
            for (YellModelListener listener : listeners) {
                listener.removeYellNode(node);
            }
        }
    }

    public void initModel(List<YellNode> initialNodes) {
        nodes = initialNodes;
        for (YellModelListener listener: listeners) {
            listener.init(nodes);
        }
    }

    public void addYellModelListener(YellModelListener listener) {
        listeners.add(listener);
    }
    public void removeYellModelListener(YellModelListener listener) {
        listeners.remove(listener);
    }

    public boolean contains(String url) {
        for (YellNode node: nodes) {
            if (node.getUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }

    private YellNode createYellNode(String label, String url, YellNodeType type) {
        YellNode yellNode = new YellNode();
        yellNode.setLabel(label);
        yellNode.setUrl(url);
        yellNode.setType(type);
        return yellNode;
    }


    public void sendAddYellNode(YellNode node) {
        try {
            JSONObject message = new JSONObject();
            message.put("action", "add");
            message.put("node", node.toJson());
            chromeCastManager.sendMessage(message.toString(), YellChannel.NAMESPACE);
        } catch (JSONException e) {
            Log.e("error creating json message", e.getMessage());
        }
    }

    public void sendRemoveYellNode(YellNode node) {
        try {
            JSONObject message = new JSONObject();
            message.put("action", "remove");
            message.put("node", node.toJson());
            chromeCastManager.sendMessage(message.toString(), YellChannel.NAMESPACE);
        } catch (JSONException e) {
            Log.e("error creating json message", e.getMessage());
        }
    }
}
