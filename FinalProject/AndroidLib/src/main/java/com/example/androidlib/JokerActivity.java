package com.example.androidlib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class JokerActivity extends AppCompatActivity {

    private static final String JOKES_EXTRA = "JOKES_EXTRA";

    /**
     * @param str the jokes
     * @return new intent capable of launching the activity
     */
    public static Intent newIntent(Context context, String str) {
        Intent intent = new Intent(context, JokerActivity.class);
        intent.putExtra(JOKES_EXTRA, str);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joker);

        // adds back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.joker_app_name);
        }

        TextView txvDisplayJokes = findViewById(R.id.txv_display_joke);

        //retrieve data from bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String jokes = bundle.getString(JOKES_EXTRA);
            if (jokes != null && !jokes.isEmpty()) {
                txvDisplayJokes.setText(jokes);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
