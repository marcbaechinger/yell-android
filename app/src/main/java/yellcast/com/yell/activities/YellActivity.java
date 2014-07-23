package yellcast.com.yell.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.cast.CastDevice;

import java.util.List;

import yellcast.com.yell.R;
import yellcast.com.yell.YellApplication;
import yellcast.com.yell.YellModelListener;
import yellcast.com.yell.cast.ChromeCastManager;
import yellcast.com.yell.fragments.YellNodeListFragment;
import yellcast.com.yell.fragments.YellNodeSelectionListener;
import yellcast.com.yell.model.YellNode;


public class YellActivity extends ActionBarActivity implements YellNodeSelectionListener {

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.yell, menu);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        chromeCastManager.scann((MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onDeviceUnselected(CastDevice selectedDevice) {

    }

    public void onDeviceSelected(CastDevice unselectedDevice) {

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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onYellNodeSelected(YellNode yellNode) {

    }
}
