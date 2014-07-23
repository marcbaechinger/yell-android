package yellcast.com.yell.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import yellcast.com.yell.R;
import yellcast.com.yell.YellApplication;
import yellcast.com.yell.model.YellModelListener;
import yellcast.com.yell.model.YellModelListenerBase;
import yellcast.com.yell.model.YellNode;

/**
 * A placeholder fragment containing a simple view.
 */
public class YellNodeListFragment extends Fragment  implements AbsListView.OnItemClickListener {

    private YellNodeListAdapter adapter;
    private ListView listView;
    private YellNodeSelectionListener listener;
    private YellApplication application;
    private YellModelListener modelListener;

    public YellNodeListFragment() {

    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PersonFragment.
     */
    public static YellNodeListFragment newInstance() {
        Bundle args = new Bundle();
        YellNodeListFragment fragment = new YellNodeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yell_node_list, container, false);

        application = (YellApplication) getActivity().getApplication();

        Button createYellButton = (Button) rootView.findViewById(R.id.new_yell_button);
        createYellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createYellIntent = new Intent("com.yellcast.yell.create");
                startActivity(createYellIntent);
            }
        });



        listView = (ListView) rootView.findViewById(R.id.yell_list_view);
        listView.setOnItemClickListener(this);
        SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(listView, new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                if (reverseSortedPositions.length > 0) {
                    application.getModel().remove((YellNode) adapter.getItem(reverseSortedPositions[0]));
                    adapter.remove(reverseSortedPositions[0]);
                }
            }
        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());

        List<YellNode> yells = application.getModel().getNodes();
        adapter = new YellNodeListAdapter(getActivity(), yells);
        ((AdapterView<ListAdapter>) listView).setAdapter(adapter);

        modelListener = new YellModelListenerBase() {
            @Override
            public void init(List<YellNode> nodes) {
                adapter.refresh(nodes);
            }
        };

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        listener.onYellNodeSelected((YellNode) adapter.getItem(position));
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.refresh(application.getModel().getNodes());
        application.getModel().addYellModelListener(modelListener);
    }

    @Override
    public void onPause() {
        application.getModel().removeYellModelListener(modelListener);
        super.onPause();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (YellNodeSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
