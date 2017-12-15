package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;


/**
 * Created by eric on 13/12/2017.
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class AsyncTaskResponseTest {
    private Context mContext;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setContext() {
        mContext = rule.getActivity().getApplicationContext();
    }

    @Test
    public void verifyAsyncTaskResponse() throws ExecutionException, InterruptedException {
        EndpointsAsyncTask task = new EndpointsAsyncTask(mContext);
        task.execute();
        String response = null;
        try {
            response = task.get(5000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        //checks for non-empty string response ...Test fail if it's empty or null
        assertThat(response, not(isEmptyOrNullString()));
        Log.d("AsyncTaskResponse", String.valueOf(response));
    }
}