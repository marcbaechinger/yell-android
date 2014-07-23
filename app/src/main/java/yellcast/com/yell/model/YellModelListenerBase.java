package yellcast.com.yell.model;

import java.util.List;

public abstract class YellModelListenerBase implements YellModelListener {
    @Override
    public void addYellNode(YellNode node) {}

    @Override
    public void removeYellNode(YellNode node) {}

    @Override
    public void init(List<YellNode> nodes) {}
}
