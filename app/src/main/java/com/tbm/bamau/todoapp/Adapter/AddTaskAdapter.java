package com.tbm.bamau.todoapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbm.bamau.todoapp.Models.AddTask;
import com.tbm.bamau.todoapp.Models.Settings;
import com.tbm.bamau.todoapp.R;

import java.util.List;

public class AddTaskAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<AddTask> addTaskList;

    public AddTaskAdapter(Context context, int layout, List<AddTask> addTaskList) {
        this.context = context;
        this.layout = layout;
        this.addTaskList = addTaskList;
    }

    @Override
    public int getCount() {
        return addTaskList.size();
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

        TextView view_name_1, view_name_2;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout, null);

            holder.img = (ImageView) convertView.findViewById(R.id.view_image);
            holder.view_name_1 = (TextView) convertView.findViewById(R.id.view_name_1);
            holder.view_name_2 = (TextView) convertView.findViewById(R.id.view_name_2);

            convertView.setTag(holder);
        }else{
            holder = (AddTaskAdapter.ViewHolder) convertView.getTag();
        }

        AddTask addTask = addTaskList.get(position);
        holder.view_name_1.setText(addTask.getName1());
        holder.view_name_2.setText(addTask.getName2());
        holder.img.setImageResource(addTask.getImg());

        return convertView;
    }
}
