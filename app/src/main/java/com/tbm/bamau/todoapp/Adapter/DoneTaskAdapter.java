package com.tbm.bamau.todoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;

import java.util.List;

public class DoneTaskAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Task> taskListDone;

    public DoneTaskAdapter(Context context, int layout, List<Task> taskListDone) {
        this.context = context;
        this.layout = layout;
        this.taskListDone = taskListDone;
    }


    @Override
    public int getCount() {
        return taskListDone.size();
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
        TextView txt_name ,txt_time, txt_note;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DoneTaskAdapter.ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.txt_time  = (TextView) convertView.findViewById(R.id.id_time);
            holder.txt_name  = (TextView) convertView.findViewById(R.id.id_name);
            holder.txt_note  = (TextView) convertView.findViewById(R.id.id_note);
            holder.imageView  = (ImageView) convertView.findViewById(R.id.imageTask);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = taskListDone.get(position);
        holder.txt_name.setText(task.getNameTask());
        holder.txt_time.setText(task.getTimeTask());
        holder.txt_note.setText(task.getNote());


        return convertView;
    }
}
