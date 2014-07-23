package yellcast.com.yell.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class YellModel {
    private List<YellNode> nodes;
    private Set<YellModelListener> listeners;

    public YellModel() {
        listeners = new HashSet<YellModelListener>();
        nodes = new ArrayList<YellNode>();
    }

    public void initModel(List<YellNode> initialNodes) {
        nodes = initialNodes;
        for (YellModelListener listener: listeners) {
            listener.init(nodes);
        }
    }

    public boolean add(YellNode node) {
        if (nodes.add(node)) {
            for (YellModelListener listener : listeners) {
                listener.addYellNode(node);
            }
            return true;
        }
        return false;
    }

    public boolean remove(YellNode node) {
        if (nodes.remove(node)) {
            for (YellModelListener listener : listeners) {
                listener.removeYellNode(node);
            }
            return true;
        }
        return false;
    }

    public List<YellNode> getNodes() {
        List<YellNode> dest = new ArrayList<YellNode>();
        for (YellNode node : nodes) {
            dest.add(node);
        }
        return dest;
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
}
