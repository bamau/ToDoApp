package com.tbm.bamau.todoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapterWeek extends BaseAdapter {

    List<Date> dates;
    Calendar currentDate;
    List<Task> taskOneWeek;
    LayoutInflater inflater;

    public GridAdapterWeek(List<Date> dates, Calendar currentDate, List<Task> taskOneWeek, LayoutInflater inflater) {
        this.dates = dates;
        this.currentDate = currentDate;
        this.taskOneWeek = taskOneWeek;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int DayNo = dateCalendar.get(Calendar.DAY_OF_WEEK);
        int displayWeek = dateCalendar.get(Calendar.WEEK_OF_MONTH)+1;
        int displayMonth = dateCalendar.get(Calendar.MONTH);
        int displayYear = dateCalendar.get(Calendar.YEAR);

        int currentWeek = currentDate.get(Calendar.MONTH)+1;
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);

        View view = convertView;
        if (view==null){
            view = inflater.inflate(R.layout.singe_row_layout, parent,false);
        }

        return null;
    }
}
