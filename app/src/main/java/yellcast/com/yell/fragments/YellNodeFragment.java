package yellcast.com.yell.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yellcast.com.yell.R;
import yellcast.com.yell.model.YellNode;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link yellcast.com.yell.fragments.YellNodeFragment.OnYellNodeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YellNodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class YellNodeFragment extends Fragment {

    private static final String ARG_YELL_NODE = "yellNode";

    private YellNode node;

    private OnYellNodeFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param yellNode the node
     * @return A new instance of fragment YellNodeFragment.
     */
    public static YellNodeFragment newInstance(YellNode yellNode) {
        YellNodeFragment fragment = new YellNodeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_YELL_NODE, yellNode);
        fragment.setArguments(args);
        return fragment;
    }
    public YellNodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            node = getArguments().getParcelable(ARG_YELL_NODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yell_node, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnYellNodeFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnYellNodeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnYellNodeFragmentInteractionListener {
        public void onYellNodeInteraction(Uri uri);
    }

}
