package yellcast.com.yell.model;

import java.util.List;

public interface YellModelListener {
    void addYellNode(YellNode node);
    void removeYellNode(YellNode node);
    void init(List<YellNode> nodes);
}
