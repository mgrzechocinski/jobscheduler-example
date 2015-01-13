package net.grzechocinski.android.jobscheduler_example;

import android.content.ComponentName;
import android.content.Context;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import timber.log.Timber;

public class JobSchedulerManager {

    private static final int ONE_SHOT_JOB_ID = 132231;

    private static final int PERIODIC_JOB_ID = 221312;

    private static final long CACHE_UPDATE_PERIOD_IN_MILLIS = TimeUnit.SECONDS.toMillis(10);

    private JobScheduler jobScheduler;

    private Context context;

    public JobSchedulerManager(Context context) {
        this.context = context;
        this.jobScheduler = JobScheduler.getInstance(context);
    }

    public void scheduleOneShotJob() {
        JobInfo job = baseJobInfoBuilder(ONE_SHOT_JOB_ID).build();
        jobScheduler.schedule(job);
    }

    public void schedulePeriodicJob() {
        long period = CACHE_UPDATE_PERIOD_IN_MILLIS;
        JobInfo job = baseJobInfoBuilder(PERIODIC_JOB_ID)
                .setPeriodic(period)
                .setPersisted(true)
                .build();

        jobScheduler.schedule(job);
        Timber.d("Scheduling cache update job (id: %s) for every %s h. (%s min., %s s.).", job.getId(),
                TimeUnit.MILLISECONDS.toHours(period),
                TimeUnit.MILLISECONDS.toMinutes(period),
                TimeUnit.MILLISECONDS.toSeconds(period));
    }

    public void cancelPendingJobs() {
        List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
        for (JobInfo pendingJob : allPendingJobs) {
            int id = pendingJob.getId();
            jobScheduler.cancel(id);
            Timber.i("Pending job with id %s cancelled", id);
        }
    }

    private JobInfo.Builder baseJobInfoBuilder(int jobId) {
        return
                new JobInfo.Builder(jobId, new ComponentName(context, JobSchedulerService.class))
                        .setRequiresCharging(true);
    }
}
