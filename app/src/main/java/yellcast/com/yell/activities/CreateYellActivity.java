package yellcast.com.yell.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import yellcast.com.yell.R;
import yellcast.com.yell.fragments.YellNodeForm;

public class CreateYellActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_yell);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new YellNodeForm())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_yell, menu);
        return true;
    }
}
