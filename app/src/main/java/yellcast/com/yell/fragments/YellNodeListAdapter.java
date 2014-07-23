package yellcast.com.yell.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yellcast.com.yell.R;
import yellcast.com.yell.model.YellNode;
import yellcast.com.yell.model.YellNodeType;

/**
 * Created by marcbaechinger on 22.07.14.
 */
public class YellNodeListAdapter extends BaseAdapter {
    private Context context;
    private List<YellNode> yellNodes;

    public YellNodeListAdapter(Context context, List<YellNode> yellNodes) {
        this.context = context;
        this.yellNodes = yellNodes;
    }

    public void refresh(List<YellNode> yells) {
        this.yellNodes.clear();
        this.yellNodes.addAll(yells);
        this.notifyDataSetChanged();
    }

    public void remove(int position) {
        if (yellNodes.size() > position) {
            this.yellNodes.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return yellNodes.size();
    }

    @Override
    public Object getItem(int index) {
        return yellNodes.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.fragment_yell_node, parent, false);


        } else {
            rowView = convertView;
        }

        YellNode yell = yellNodes.get(position);

        TextView label = (TextView) rowView.findViewById(R.id.yell_node_label);
        label.setText(yell.getLabel());

        TextView url = (TextView) rowView.findViewById(R.id.yell_node_url);
        url.setText(yell.getUrl());

        TextView icon = (TextView) rowView.findViewById(R.id.yell_node_icon);
        if (yell.getType() == YellNodeType.TRAVIS) {
            icon.setText("T");
            icon.setBackgroundColor(Color.parseColor("#214D99"));
        } else if (yell.getType() == YellNodeType.JENKINS) {
            icon.setText("J");
            icon.setBackgroundColor(Color.parseColor("#C73F24"));
        } else if (yell.getType() == YellNodeType.UP4SURE) {
            icon.setText("U");
            icon.setBackgroundColor(Color.parseColor("#23782B"));
        }

        return rowView;
    }
}
