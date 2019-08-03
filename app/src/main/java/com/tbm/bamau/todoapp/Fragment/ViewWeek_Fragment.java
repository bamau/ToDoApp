package com.tbm.bamau.todoapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tbm.bamau.todoapp.Adapter.GridAdapter;
import com.tbm.bamau.todoapp.Adapter.TaskAdapter;
import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.motion.MotionScene.TAG;

public class ViewWeek_Fragment extends Fragment {

    ImageButton nextButton, previousButton;
    TextView currentDate, weekDate, sun, mon, tue, wed, thu, fri, sat;
    GridView gridView;
    DbHelper database;
    GridAdapter gridAdapter;

    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM",Locale.ENGLISH);
    SimpleDateFormat dayFormat = new SimpleDateFormat("EEE dd",Locale.ENGLISH);

    List<Date> dates = new ArrayList<>();
    List<Task> taskList= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.viewweek_fragment, container, false);

        Initialization(view);
        setUpDayOfWeek(view);
        setUpCalendar();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(calendar.WEEK_OF_MONTH,1);

                setUpDayOfWeek(view);
                setUpCalendar();


            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(calendar.WEEK_OF_MONTH,-1);

                setUpDayOfWeek(view);
                setUpCalendar();

            }
        });

        return view;
    }

    private  void Initialization(View view){
        nextButton = view.findViewById(R.id.nextBtn);
        previousButton = view.findViewById(R.id.previousBtn);
        currentDate = view.findViewById(R.id.currentDate);
        gridView = view.findViewById(R.id.gridview);
        database = new DbHelper(getActivity());
        weekDate = view.findViewById(R.id.weekDate);
    }
    private void setUpCalendar() {
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        String cDate = dateFormat.format(calendar.getTime());
        currentDate.setText(cDate);
        calendar.add(Calendar.DATE, +6);
        String wDate = dateFormat.format(calendar.getTime());
        weekDate.setText(wDate);

    }

    private void setUpDayOfWeek(View view){
        int findview[]={R.id.sun, R.id.mon, R.id.tue, R.id.wed, R.id.thu, R.id.fri, R.id.sat};
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        TextView view1;
        for(int i = 0;i<=6;++i, calendar.add(Calendar.DATE, 1)){
            view1 = view.findViewById(findview[i]);
            view1.setText(dayFormat.format(calendar.getTime()));
        }
        calendar.add(Calendar.DATE,-6);
    }

}
