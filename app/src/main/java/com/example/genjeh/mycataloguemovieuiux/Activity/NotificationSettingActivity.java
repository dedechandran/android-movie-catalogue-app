package com.example.genjeh.mycataloguemovieuiux.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.genjeh.mycataloguemovieuiux.AlarmReceiver;
import com.example.genjeh.mycataloguemovieuiux.R;
import com.example.genjeh.mycataloguemovieuiux.StatePreference;


import java.util.Calendar;
import java.util.List;


import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class NotificationSettingActivity extends AppCompatActivity {
    @BindView(R.id.setting_notifikasi_toolbar)
    Toolbar toolbar;

    @BindViews({R.id.switch_daily_remainder, R.id.switch_release_today_remainder})
    List<Switch> listSwitch;

    private AlarmManager alarmManager;
    private StatePreference statePreference;

    public static final int REQUEST_CODE_DAILY_ON = 1;
    public static final int REQUEST_CODE_MOVIE_RELEASED_ON = 2;
    public static final String ACTION_DAILY_ON = "ACTION_DAILY_ON";
    public static final String ACTION_MOVIE_RELEASED_ON = "ACTION_MOVIE_RELEASED_ON";

    public static final int TIME_DAILY = 7;
    public static final int TIME_MOVIE_RELEASED = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        statePreference = new StatePreference(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (statePreference.getDailyOn()) {
            listSwitch.get(0).setChecked(true);
        } else {
            listSwitch.get(0).setChecked(false);
        }

        if (statePreference.getReleasedNow()) {
            listSwitch.get(1).setChecked(true);
        } else {
            listSwitch.get(1).setChecked(false);
        }

        final Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);

        listSwitch.get(0).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmIntent.setAction(ACTION_DAILY_ON);
                PendingIntent alarmDailyIntent = PendingIntent.getBroadcast(getApplicationContext(), REQUEST_CODE_DAILY_ON, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                if (isChecked) {
                    statePreference.setDailyOn(true);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, TIME_DAILY);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY,
                            alarmDailyIntent);
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.toast_set_notif)
                            , Toast.LENGTH_SHORT).show();
                } else {
                    statePreference.setDailyOn(false);
                    if (alarmManager != null) {
                        alarmManager.cancel(alarmDailyIntent);
                    }
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.toast_cancel_notif),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        listSwitch.get(1).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmIntent.setAction(ACTION_MOVIE_RELEASED_ON);
                PendingIntent alarmMovieReleasedIntent = PendingIntent.getBroadcast(getApplicationContext(),
                        REQUEST_CODE_MOVIE_RELEASED_ON, alarmIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                if (isChecked) {
                    statePreference.setReleasedNow(true);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, TIME_MOVIE_RELEASED);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY,
                            alarmMovieReleasedIntent);
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.toast_set_notif)
                            , Toast.LENGTH_SHORT).show();
                } else {
                    statePreference.setReleasedNow(false);
                    if (alarmManager != null) {
                        alarmManager.cancel(alarmMovieReleasedIntent);
                    }
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.toast_cancel_notif),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
