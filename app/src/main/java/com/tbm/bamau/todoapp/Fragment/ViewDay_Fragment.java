package com.tbm.bamau.todoapp.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tbm.bamau.todoapp.Adapter.TaskAdapter;
import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;

import java.util.List;

public class ViewDay_Fragment extends Fragment {

    Context context;
    TaskAdapter taskAdapter;
    DbHelper database;
    SwipeMenuListView listTask;
    List<Task> arrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewday_fragment, container, false);


        Task task;
        setupAdapter(view);
        return view;
    }

    private void setupAdapter(View view) {
        database = new DbHelper(getActivity());
        listTask = view.findViewById(R.id.listView);
        arrayList=database.getAllTask();
        taskAdapter = new TaskAdapter(getActivity(),R.layout.item_task,arrayList);
        listTask.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();


        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem doneItem = new SwipeMenuItem
                        (getContext().getApplicationContext());
                // set item background
                doneItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xe6,
                        0x00)));
                // set item width
                doneItem.setWidth(150);
                // set a icon
                doneItem.setIcon(R.drawable.ic_done_black_24dp);
                // add to menu
                menu.addMenuItem(doneItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem
                        (getContext().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(150);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
//        // set creator
        listTask.setMenuCreator(creator);
        listTask.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // complete

                        break;
                    case 1:
                        // delete
                        int id = arrayList.get(position).getIdTask();
                        DialogXoaTask(id);
                        break;
                }
                return false;
            }
        });
    }

    public void updateListTask(){
        arrayList.clear();
        arrayList.addAll(database.getAllTask());
        if(taskAdapter!= null){
            taskAdapter.notifyDataSetChanged();
        }
    }
        private void DialogXoaTask(final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getContext());
        dialogXoa.setMessage("Do you want to delete this task?");
        dialogXoa.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.deleteTask(database.getTaskById(id));
                Toast.makeText(getContext(), "Delete success!", Toast.LENGTH_SHORT).show();
                updateListTask();
            }
        });
        dialogXoa.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogXoa.show();
    }
}
