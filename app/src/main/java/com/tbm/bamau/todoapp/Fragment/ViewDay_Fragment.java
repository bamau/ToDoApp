package com.tbm.bamau.todoapp.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tbm.bamau.todoapp.Adapter.DoneTaskAdapter;
import com.tbm.bamau.todoapp.Adapter.TaskAdapter;
import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.MainActivity;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;
import com.tbm.bamau.todoapp.UpdateTaskActivity;
import com.tbm.bamau.todoapp.ViewDetailTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.motion.MotionScene.TAG;
import static com.tbm.bamau.todoapp.MainActivity.hideSoftKeyboard;

public class ViewDay_Fragment extends Fragment {

    TaskAdapter taskAdapter;
    DoneTaskAdapter taskAdapterDone;
    DbHelper database;
    SwipeMenuListView listTask, listTaskDone;
    List<Task> arrayList;
    List<Task> arrayListDone;

    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
    OnMessageReadListenerDay onMessageReadListenerDay;

    String curentDate = dateFormat.format(calendar.getTime());

    public interface OnMessageReadListenerDay{
        public void onMessageReadDay(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewday_fragment, container, false);


        Initialization(view);
        setupAdapter(view);
        setupAdapterDone(view);
        setupUI(view);
        Bundle bundle = getArguments();
        if (bundle != null){
            String date =bundle.getString("CHANGE_DATE");
            getListTaskOneDate(date, 0);
            getListDoneTaskOneDate(date,1);
        }
        return view;
    }

    private void getListDoneTaskOneDate(String day, int status) {
        database = new DbHelper(getActivity());
        if (arrayListDone != null)
            arrayListDone.clear();
        String[] cutDay = day.split(" ");
        arrayListDone=database.getListTaskWithDay(cutDay[0],cutDay[1],cutDay[2],status);
        taskAdapterDone = new DoneTaskAdapter(getActivity(),R.layout.item_task_done, arrayListDone);
        listTaskDone.setAdapter(taskAdapterDone);
        try {
            Date date = dateFormat.parse(day);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(taskAdapterDone!= null){
            taskAdapterDone.notifyDataSetChanged();
        }
    }

    public Date formatDate (String date) throws ParseException {
        Date fDate = dateFormat.parse(date);
        return fDate;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ViewDay_Fragment.OnMessageReadListenerDay) {
            onMessageReadListenerDay = (ViewDay_Fragment.OnMessageReadListenerDay) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnMessageReadListenerDay");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMessageReadListenerDay = null;
    }

    private void Initialization (View view){
        listTask = view.findViewById(R.id.listView);
        listTaskDone = view.findViewById(R.id.swipelistview);
    }
    public void setupAdapter(View view) {
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
        listTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Task task = arrayList.get(position);
            int idTask = task.getIdTask();
            Intent intent = new Intent(getContext(), UpdateTaskActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("ID",idTask);
            intent.putExtras(bundle);
            startActivity(intent);

            }
        });
        listTask.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                int id = arrayList.get(position).getIdTask();
                switch (index) {
                    case 0:
                        Task task = database.getTaskById(id);
                        database.UpdateStatus(task);
                        Toast.makeText(getContext(), R.string.congratulations_you_done_task, Toast.LENGTH_LONG).show();
                        updateListTask();
                        updateListDoneTask();
                        // complete
                        break;
                    case 1:
                        // delete
                        DialogXoaTask(id);
                        break;
                }
                return false;
            }
        });
    }

    public void setupAdapterDone(View view) {
        SwipeMenuCreator creatorDone = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menuDone) {
                // create "delete" item
                SwipeMenuItem deleteItemDone = new SwipeMenuItem
                        (getContext().getApplicationContext());
                // set item background
                deleteItemDone.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItemDone.setWidth(150);
                // set a icon
                deleteItemDone.setIcon(R.drawable.ic_delete_black_24dp);
                // add to menu
                menuDone.addMenuItem(deleteItemDone);
            }
        };
//        // set creator
        listTaskDone.setMenuCreator(creatorDone);
        listTaskDone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task task = arrayListDone.get(position);
                int idTask = task.getIdTask();
                Intent intent = new Intent(getContext(), ViewDetailTask.class);
                Bundle bundle = new Bundle();
                bundle.putInt("ID",idTask);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        listTaskDone.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                int id = arrayListDone.get(position).getIdTask();
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

    public void updateListTask(){
        database = new DbHelper(getActivity());
        Calendar calendar = Calendar.getInstance();
        String currentDay = dateFormat.format(calendar.getTime());
        if (arrayList != null)
            arrayList.clear();
        String[] cutDay = currentDay.split(" ");
        arrayList=database.getListTaskWithDay(cutDay[0],cutDay[1],cutDay[2],0);
        taskAdapter = new TaskAdapter(getActivity(),R.layout.item_task, arrayList);
        listTask.setAdapter(taskAdapter);
        if(taskAdapter!= null){
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void updateListDoneTask(){
        database = new DbHelper(getActivity());
        Calendar calendar = Calendar.getInstance();
        String currentDay = dateFormat.format(calendar.getTime());
        if (arrayListDone != null)
            arrayListDone.clear();
        String[] cutDay = currentDay.split(" ");
        arrayListDone=database.getListTaskWithDay(cutDay[0],cutDay[1],cutDay[2],1);
        taskAdapterDone = new DoneTaskAdapter(getActivity(),R.layout.item_task_done, arrayListDone);
        listTaskDone.setAdapter(taskAdapterDone);
        if(taskAdapterDone!= null){
            taskAdapterDone.notifyDataSetChanged();
        }
    }

    public void updateListTaskWithChangeDate(String day, int status){
        database = new DbHelper(getActivity());
        if (arrayList != null)
            arrayList.clear();
        String[] cutDay = day.split(" ");
        arrayList=database.getListTaskWithDay(cutDay[0],cutDay[1],cutDay[2],status);
        taskAdapter = new TaskAdapter(getActivity(),R.layout.item_task, arrayList);
        listTask.setAdapter(taskAdapter);
        if(taskAdapter!= null){
            taskAdapter.notifyDataSetChanged();
        }
    }
    public void updateListTaskDoneWithChangeDate(String day, int status){
        database = new DbHelper(getActivity());
        if (arrayListDone != null)
            arrayListDone.clear();
        String[] cutDay = day.split(" ");
        arrayListDone=database.getListTaskWithDay(cutDay[0],cutDay[1],cutDay[2],status);
        taskAdapterDone = new DoneTaskAdapter(getActivity(),R.layout.item_task_done, arrayListDone);
        listTaskDone.setAdapter(taskAdapterDone);
        if(taskAdapterDone!= null){
            taskAdapterDone.notifyDataSetChanged();
        }
    }

    public void getListTaskOneDate(String day, int status){

        database = new DbHelper(getActivity());
        if (arrayList != null)
            arrayList.clear();
        String[] cutDay = day.split(" ");
        arrayList=database.getListTaskWithDay(cutDay[0],cutDay[1],cutDay[2],status);
        taskAdapter = new TaskAdapter(getActivity(),R.layout.item_task, arrayList);
        listTask.setAdapter(taskAdapter);
        try {
            Date date = dateFormat.parse(day);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(taskAdapter!= null){
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void getListTaskDoneOneDate(String day, int status){

        database = new DbHelper(getActivity());
        if (arrayListDone != null)
            arrayListDone.clear();
        String[] cutDay = day.split(" ");
        arrayListDone=database.getListTaskWithDay(cutDay[0],cutDay[1],cutDay[2],status);
        taskAdapterDone = new DoneTaskAdapter(getActivity(),R.layout.item_task_done, arrayListDone);
        listTaskDone.setAdapter(taskAdapterDone);
        try {
            Date date = dateFormat.parse(day);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(taskAdapterDone!= null){
            taskAdapterDone.notifyDataSetChanged();
        }
    }
    public void DialogXoaTask(final int id){
        final String cDate = dateFormat.format(calendar.getTime());
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getContext());
        dialogXoa.setMessage(R.string.do_you_want_delete_this_task);
        dialogXoa.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.deleteTask(database.getTaskById(id));
                Toast.makeText(getContext(), R.string.delete_success, Toast.LENGTH_SHORT).show();
                updateListTaskWithChangeDate(cDate,0);
            }
        });
        dialogXoa.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogXoa.show();
    }
    public void DialogXoaDoneTask(final int id){
        final String cDate = dateFormat.format(calendar.getTime());
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getContext());
        dialogXoa.setMessage(R.string.do_you_want_delete_this_task);
        dialogXoa.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.deleteTask(database.getTaskById(id));
                Toast.makeText(getContext(), R.string.delete_success, Toast.LENGTH_SHORT).show();
                updateListTaskDoneWithChangeDate(cDate,1);
            }
        });
        dialogXoa.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogXoa.show();
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
