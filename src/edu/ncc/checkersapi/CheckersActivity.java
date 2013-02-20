package edu.ncc.checkersapi;

import edu.ncc.checkers.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CheckersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkers_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.checkers_activity, menu);
        return true;
    }
    
}
