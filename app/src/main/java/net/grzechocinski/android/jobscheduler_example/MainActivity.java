package net.grzechocinski.android.jobscheduler_example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import timber.log.Timber;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private JobSchedulerManager jobSchedulerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_trigger_one).setOnClickListener(this);
        findViewById(R.id.btn_cancel_all_pending).setOnClickListener(this);
        findViewById(R.id.btn_trigger_periodic).setOnClickListener(this);
        jobSchedulerManager = new JobSchedulerManager(getApplicationContext());
        Timber.plant(new JobSchedulerTimberTree());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_trigger_one:
                jobSchedulerManager.scheduleOneShotJob();
                break;
            case R.id.btn_trigger_periodic:
                jobSchedulerManager.schedulePeriodic();
                break;
            case R.id.btn_cancel_all_pending:
                jobSchedulerManager.cancelPendingJobs();
                break;
        }
    }
}
