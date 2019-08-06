package com.tbm.bamau.todoapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class GridAdapterMonth extends ArrayAdapter {

    List<Date> dates;
    Calendar currentDate;
    List<Task> taskList;
    LayoutInflater inflater;

    public GridAdapterMonth(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Task> taskList) {
        super(context, R.layout.singe_cell_layout);

        this.dates=dates;
        this.currentDate=currentDate;
        this.taskList=taskList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if(view==null)
            view=inflater.inflate(R.layout.singe_cell_layout, parent,false);

        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }else{
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }

        TextView Day_Number = view.findViewById(R.id.calendar_day);
        TextView Task_Number = view.findViewById(R.id.task_id);
        Day_Number.setText(String.valueOf(DayNo));
        Calendar taskCalendar = Calendar.getInstance();
        DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i=0; i < taskList.size(); i++){
            String date = taskList.get(i).getDayTask()+" "+taskList.get(i).getMonthTask()+" "+taskList.get(i).getYearTask();
            try {
                taskCalendar.setTime(formatDate(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (DayNo == taskCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == taskCalendar.get(Calendar.MONTH)+1
            && displayYear == taskCalendar.get(Calendar.YEAR) ){
                if(position == 0 || position == 7 || position == 14 || position == 21 ||position == 28 || position == 35 ){
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.violet));
                }
                if(position == 1 || position == 8 || position == 15 || position == 22 ||position == 29 || position == 36 ){
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.denim));
                }
                if(position == 2 || position == 9 || position == 16 || position == 23 ||position == 30 || position == 37 ){
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
                }
                if(position == 3 || position == 10 || position == 17 || position == 24 ||position == 31 || position == 38 ){
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.dark_green));
                }
                if(position == 4 || position == 11 || position == 18 || position == 25 ||position == 32 || position == 39 ){
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.orange));
                }
                if(position == 5 || position == 12 || position == 19 || position == 26 ||position == 33 || position == 40 ){
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.red));
                }
                if(position == 6 || position == 13 || position == 20 || position == 27 ||position == 34 || position == 41 ){
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.light_green));
                }
                arrayList.add(taskList.get(i).getNameTask());
                Task_Number.setText(arrayList.size()+ " Tasks");
            }
        }
        return view;
    }

    public Date formatDate (String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        Date fDate = dateFormat.parse(date);
        return fDate;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
