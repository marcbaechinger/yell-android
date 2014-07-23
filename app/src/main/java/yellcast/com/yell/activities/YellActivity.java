package yellcast.com.yell.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import yellcast.com.yell.R;
import yellcast.com.yell.YellApplication;
import yellcast.com.cast.ChromeCastManager;
import yellcast.com.yell.fragments.YellNodeListFragment;
import yellcast.com.yell.fragments.YellNodeSelectionListener;
import yellcast.com.yell.model.YellNode;


public class YellActivity extends ActionBarActivity implements YellNodeSelectionListener {

    private static final String TAG = YellActivity.class.getCanonicalName();

    private ChromeCastManager chromeCastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yell);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new YellNodeListFragment())
                    .commit();
        }
        chromeCastManager = ((YellApplication)getApplication()).getChromeCastManager();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.yell, menu);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        chromeCastManager.registerMediaRouteSelector((MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem));

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        chromeCastManager.connect();
    }

    @Override
    protected void onPause() {
        chromeCastManager.disconnect();
        super.onPause();
    }

    @Override
    public void onYellNodeSelected(YellNode yellNode) {
        Log.d(TAG, "yell node selected " + yellNode);
    }
}