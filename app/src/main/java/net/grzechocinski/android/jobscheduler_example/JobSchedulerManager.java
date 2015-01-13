package net.grzechocinski.android.jobscheduler_example;

import android.content.ComponentName;
import android.content.Context;
import android.widget.Toast;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import timber.log.Timber;

public class JobSchedulerManager {

    public static final int ONE_SHOT_JOB_ID = 2222;

    public static final int PERIODIC_JOB_ID = 3333;

    private static final long UPDATE_PERIOD_IN_MILLIS = TimeUnit.SECONDS.toMillis(5);

    private JobScheduler jobScheduler;

    private Context context;

    public JobSchedulerManager(Context context) {
        this.context = context;
        this.jobScheduler = JobScheduler.getInstance(context);
        //this.jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    public void schedulePeriodic() {
        JobInfo job = baseJobInfoBuilder(PERIODIC_JOB_ID).setPeriodic(UPDATE_PERIOD_IN_MILLIS).build();
        int resultOrJobId = jobScheduler.schedule(job);
        switch (resultOrJobId) {
            case JobScheduler.RESULT_SUCCESS:
                String result_success = "schedulePeriodic: RESULT_SUCCESS";
                Timber.d(result_success);
                break;
            case JobScheduler.RESULT_FAILURE:
                String result_failure = "schedulePeriodic: RESULT_FAILURE";
                Timber.d(result_failure);
                break;
            default:
                String result = "schedulePeriodic: job is scheduled: " + resultOrJobId;
                Timber.d(result);
        }
    }

    public void scheduleOneShotJob() {
        JobInfo job = baseJobInfoBuilder(ONE_SHOT_JOB_ID).setOverrideDeadline(TimeUnit.SECONDS.toMillis(3)).build();
        int resultOrJobId = jobScheduler.schedule(job);
        switch (resultOrJobId) {
            case JobScheduler.RESULT_SUCCESS:
                String result_success = "scheduleOneShotJob: RESULT_SUCCESS";
                Toast.makeText(context, result_success, Toast.LENGTH_SHORT).show();
                Timber.d(result_success);
                break;
            case JobScheduler.RESULT_FAILURE:
                String result_failure = "scheduleOneShotJob: RESULT_FAILURE";
                Toast.makeText(context, result_failure, Toast.LENGTH_SHORT).show();
                Timber.d(result_failure);
                break;
            default:
                String result = "scheduleOneShotJob: job is scheduled: " + resultOrJobId;
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                Timber.d(result);
        }
    }

    public void cancelPendingJobs() {
        List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
        for (JobInfo pendingJob : allPendingJobs) {
            int id = pendingJob.getId();
            jobScheduler.cancel(id);
            Timber.i("Pending job with id %s cancelled", id);
        }
        jobScheduler.cancelAll();

    }

    private JobInfo.Builder baseJobInfoBuilder(int jobId) {
        return new JobInfo.Builder(jobId, new ComponentName(context, JobSchedulerService.class)).setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
    }
}
