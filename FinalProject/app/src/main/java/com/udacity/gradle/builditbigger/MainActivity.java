package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //    public void tellJoke(View view) {
//        Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
//    }


//    /**
//     * displays toast message using jokes from Java Library
//     */
//    public void tellJoke(View view) {
//        Toast.makeText(this, new JokesProvider().provide(), Toast.LENGTH_SHORT).show();
//    }
//

//    /**
//     * launches an activity from Android Library
//     * sends data from the Java Library to the activity through intent
//     */
//    public void tellJoke(View view) {
//        Intent launchJoker = JokerActivity.newIntent(this, new JokesProvider().provide());
//        startActivity(launchJoker);
//    }


    /**
     * executes AsyncTask {@link EndpointsAsyncTask}
     */
    public void tellJoke(View view) {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            new EndpointsAsyncTask(this, mProgressBar).execute();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
