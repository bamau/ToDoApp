package com.tbm.bamau.todoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Task> taskList;

    public TaskAdapter(Context context, int layout, List<Task> taskList) {
        this.context = context;
        this.layout = layout;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txt_name, txt_datetime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.txt_datetime  = (TextView) convertView.findViewById(R.id.id_datetime);
            holder.txt_name      = (TextView) convertView.findViewById(R.id.id_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = taskList.get(position);
        holder.txt_name.setText(task.getNameTask());
        holder.txt_datetime.setText(task.getDatetimeTask());
        return convertView;
    }
}
