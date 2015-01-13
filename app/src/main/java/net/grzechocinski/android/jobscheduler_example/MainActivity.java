package net.grzechocinski.android.jobscheduler_example;

import android.os.Build;
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
        findViewById(R.id.btn_trigger).setOnClickListener(this);
        jobSchedulerManager = new JobSchedulerManager(getApplicationContext());
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_trigger:
                Timber.d("Button clicked:  %s", Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
                jobSchedulerManager.scheduleOneShotJob();
                break;
        }
    }
}
