package net.grzechocinski.android.jobscheduler_example;

import java.util.concurrent.TimeUnit;
import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;
import timber.log.Timber;

public class JobSchedulerService extends JobService {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("Created %s", this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        Timber.d("Destroyed %s", this.getClass().getSimpleName());
        super.onDestroy();
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Timber.d("onStartJob with id: %s", jobParameters.getJobId());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Timber.d("Finishing job");
                jobFinished(jobParameters, false);
            }
        }).start();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Timber.d("onStopJob with id: %s", jobParameters.getJobId());
        return true;
    }
}
