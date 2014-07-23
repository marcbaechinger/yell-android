package yellcast.com.yell;

import java.util.List;

import yellcast.com.yell.model.YellNode;

/**
 * Created by marcbaechinger on 23.07.14.
 */
public interface YellModelListener {
    void addYellNode(YellNode node);
    void removeYellNode(YellNode node);
    void init(List<YellNode> nodes);
}
