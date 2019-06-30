package com.tbm.bamau.todoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbm.bamau.todoapp.Models.Settings;
import com.tbm.bamau.todoapp.R;
import com.tbm.bamau.todoapp.SettingActivity;

import java.util.List;

public class SettingAdapter extends BaseAdapter {

    private SettingActivity context;
    private int layout;
    private List<Settings> settingsList;

    public SettingAdapter(SettingActivity context, int layout, List<Settings> settingsList) {
        this.context = context;
        this.layout = layout;
        this.settingsList = settingsList;
    }

    @Override
    public int getCount() {
        return 0;
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

        TextView view_name;
        ImageView view_image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout, null);

            holder.view_image=(ImageView) convertView.findViewById(R.id.view_image);
            holder.view_name = (TextView) convertView.findViewById(R.id.view_name);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Settings settings = settingsList.get(position);
        holder.view_name.setText(settings.getName());

        return convertView;
    }
}
