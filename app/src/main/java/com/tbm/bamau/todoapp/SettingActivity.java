package com.tbm.bamau.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.tbm.bamau.todoapp.Adapter.SettingAdapter;
import com.tbm.bamau.todoapp.Models.Settings;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private ArrayList<Settings> settingsList;
    ListView listView;
    SettingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        listView = findViewById(R.id.view_setting);
        settingsList = new ArrayList<Settings>();
        adapter = new SettingAdapter(this, R.layout.item_setting, settingsList);
        listView.setAdapter(adapter);

        settingsList.add(new Settings(0,"Change Language",R.drawable.ic_language_black_24dp));
        settingsList.add(new Settings(1,"Change Theme",R.drawable.ic_theme_black_24dp));
        settingsList.add(new Settings(2,"Rate Application",R.drawable.ic_star_black_24dp));

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
