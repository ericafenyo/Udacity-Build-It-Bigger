package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ProgressBar;

import com.example.androidlib.JokerActivity;
import com.example.javalib.JokesProvider;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokerApi.JokerApi;

import java.io.IOException;

import javax.annotation.Nullable;


/**
 * Created by eric on 13/12/2017.
 * Built on code sample from
 * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
 */

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static JokerApi mJokerApi = null;
    private Context mContext;
    private ProgressBar mProgressBar;
    private String mResult;

    public EndpointsAsyncTask(Context mContext) {
        this.mContext = mContext;
    }

    public EndpointsAsyncTask(Context mContext,  @Nullable ProgressBar mProgressBar) {
        this.mContext = mContext;
        this.mProgressBar = mProgressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Pair<Context, String>[] pairs) {
        if (mJokerApi == null) {  // Only do this once
            JokerApi.Builder builder = new JokerApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            mJokerApi = builder.build();
        }

        try {
            return mJokerApi.getJokes(new JokesProvider().provide()).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mResult = s;
        mProgressBar.setVisibility(View.INVISIBLE);
        if (s != null) {
            Intent launchJoker = JokerActivity.newIntent(mContext, mResult);
            launchJoker.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(launchJoker);
        }
    }

}