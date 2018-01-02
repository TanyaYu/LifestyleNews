package com.tanyayuferova.lifestylenews.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Tanya Yuferova on 12/23/2017.
 */

public class FirebaseJobService extends JobService {

    private AsyncTask<Void, Void, Void> mFetchArticlesTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mFetchArticlesTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                SyncTask.syncLoadNewArticles(context);
                jobFinished(jobParameters, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
            }
        };

        mFetchArticlesTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchArticlesTask != null) {
            mFetchArticlesTask.cancel(true);
        }
        return true;
    }
}
