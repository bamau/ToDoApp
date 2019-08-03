package com.tbm.bamau.todoapp.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tbm.bamau.todoapp.Adapter.TaskAdapter;
import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;
import com.tbm.bamau.todoapp.UpdateTaskActivity;

import java.util.List;

public class ViewDoneTask_Fragment extends Fragment {

    TaskAdapter taskAdapter;
    DbHelper database;
    SwipeMenuListView listTask;
    List<Task> arrayList;
    int status=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewdonetask_fragment, container, false);

        listTask = view.findViewById(R.id.swipelistview);
        setupAdapter(view);
        updateListTaskWithChangeStatus(status);
        return view;
    }

    private void setupAdapter(View view) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
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
        listTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task task = arrayList.get(position);
                int idTask = task.getIdTask();
                Intent intent = new Intent(getContext(), UpdateTaskActivity.class);
                intent.putExtra("ID",idTask);
                startActivity(intent);

            }
        });
        listTask.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                int id = arrayList.get(position).getIdTask();
                switch (index) {
                    case 0:
                        // delete
                        DialogXoaDoneTask(id);
                        break;
                }
                return false;
            }
        });
    }

    public void updateListTaskWithChangeStatus(int status){
        database = new DbHelper(getActivity());
        if (arrayList != null)
            arrayList.clear();
        arrayList=database.getListTaskWithStatus(status);
        taskAdapter = new TaskAdapter(getActivity(),R.layout.item_task, arrayList);
        listTask.setAdapter(taskAdapter);
        if(taskAdapter!= null){
            taskAdapter.notifyDataSetChanged();
        }
    }

    private void DialogXoaDoneTask(final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getContext());
        dialogXoa.setMessage("Do you want to delete this task?");
        dialogXoa.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.deleteTask(database.getTaskById(id));
                Toast.makeText(getContext(), "Delete success!", Toast.LENGTH_SHORT).show();
                updateListTaskWithChangeStatus(status);
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
