package com.tbm.bamau.todoapp.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class BootReceiver extends BroadcastReceiver {

    private int id;
    private int status;
    private String nameTask;
    private String timeTask;
    private String dayTask;
    private String monthTask;
    private String yearTask;
    private String repeatTask;
    private String timeReminder;
    private String noteTask;
    private String linkImage;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private String[] mTimeSplit;

    private Calendar mCalendar;
    private AlarmReceiver mAlarmReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            DbHelper db = new DbHelper(context);
            mCalendar = Calendar.getInstance();
            mAlarmReceiver = new AlarmReceiver();

            List<Task> taskList = db.getListTaskWithStatusOrderByYear(0);

            for (Task tb : taskList) {
                id = tb.getIdTask();
                status = tb.getStatusTask();
                nameTask = tb.getNameTask();
                timeTask = tb.getTimeTask();
                dayTask = tb.getDayTask();
                monthTask = tb.getMonthTask();
                yearTask = tb.getYearTask();
                repeatTask = tb.getRepeat();
                timeReminder = tb.getTimeReminder();
                noteTask = tb.getNote();
                linkImage = tb.getLinkImage();

                mTimeSplit = timeTask.split(":");

                mDay = Integer.parseInt(dayTask);
                mMonth = Integer.parseInt(monthTask);
                mYear = Integer.parseInt(yearTask);
                mHour = Integer.parseInt(mTimeSplit[0]);
                mMinute = Integer.parseInt(mTimeSplit[1]);

                mCalendar.set(Calendar.MONTH, --mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);



//                Intent newIntent = new Intent(get, AlarmReceiver.class);
//                newIntent.putExtra(getString(R.string.alert_title), task.getIdTask());
//                pendingIntent = PendingIntent.getBroadcast(this,0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        }
    }
}
