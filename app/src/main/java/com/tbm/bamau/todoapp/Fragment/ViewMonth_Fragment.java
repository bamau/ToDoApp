package com.tbm.bamau.todoapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Adapter.GridAdapterMonth;
import com.tbm.bamau.todoapp.DBStructure;
import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.MainActivity;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;
import com.tbm.bamau.todoapp.UpdateTaskActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewMonth_Fragment extends Fragment {

    private static final int MAX_CALENDAR_DAYS = 42;
    ImageButton nextButton, previousButton;
    TextView currentDate;
    GridView gridView;
    DbHelper database;
    GridAdapterMonth gridAdapter;
    OnMessageReadListenerMonth onMessageReadListenerMonth;

    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat dateTaskFormat = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);

    List<Date> dates = new ArrayList<>();
    List<Task> taskList= new ArrayList<>();


    public interface OnMessageReadListenerMonth{
        public void onMessageReadMonth(CharSequence input);
    }



        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewmonth_fragment, container, false);
        nextButton = view.findViewById(R.id.nextBtn);
        previousButton = view.findViewById(R.id.previousBtn);
        currentDate = view.findViewById(R.id.currentDate);
        gridView = view.findViewById(R.id.gridview);
        database = new DbHelper(getActivity());


        setUpCalendar();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(calendar.MONTH,1);
                setUpCalendar();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(calendar.MONTH,-1);
                setUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Date date = dates.get(position);
                String dateTask = dateTaskFormat.format(date.getTime());
                CharSequence input = dateTask;
                onMessageReadListenerMonth.onMessageReadMonth(input);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnMessageReadListenerMonth){
            onMessageReadListenerMonth = (OnMessageReadListenerMonth) context;
        }else{
            throw new RuntimeException(context.toString()+ "must implement OnMessageReadListenerMonth");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMessageReadListenerMonth = null;
    }

    private void setUpCalendar() {
        String cDate = dateFormat.format(calendar.getTime());
        currentDate.setText(cDate);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayOfMonth);
        CollectTaskPerMonth(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));

        while (dates.size() < MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        gridAdapter = new GridAdapterMonth(getContext(),dates,calendar,taskList);
        gridView.setAdapter(gridAdapter);
    }

    private void CollectTaskPerMonth (String month, String year){
        taskList.clear();
        database = new DbHelper(getActivity());
        SQLiteDatabase db =  database.getReadableDatabase();
        Cursor cursor = database.readTaskPerMonth(month,year, 0,db);
        while (cursor.moveToNext()){
            String nameTask = cursor.getString(cursor.getColumnIndex(DBStructure.NAME_TASK));
            int idTask = cursor.getInt(cursor.getColumnIndex(DBStructure.TASK_ID));
            int statusTask = cursor.getInt(cursor.getColumnIndex(DBStructure.TASK_STATUS));
            String timeTask = cursor.getString(cursor.getColumnIndex(DBStructure.TIME_TASK));
            String dayTask = cursor.getString(cursor.getColumnIndex(DBStructure.DAY_TASK));
            String monthTask = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH_TASK));
            String yearTask = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR_TASK));
            String repeatTask = cursor.getString(cursor.getColumnIndex(DBStructure.REPEAT_TASK));
            String timeReminder = cursor.getString(cursor.getColumnIndex(DBStructure.TIME_REMINDER));
            String nodeTask = cursor.getString(cursor.getColumnIndex(DBStructure.NOTE_TASK));
            String linkImage = cursor.getString(cursor.getColumnIndex(DBStructure.LINK_IMAGE));
            String linkAudio = cursor.getString(cursor.getColumnIndex(DBStructure.LINK_AUDIO));
            Task task = new Task(idTask,statusTask,nameTask,timeTask,dayTask,monthTask, yearTask, repeatTask, timeReminder, nodeTask,linkImage,linkAudio);
            taskList.add(task);
        }
        cursor.close();
        database.close();
    }
}
