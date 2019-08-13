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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tbm.bamau.todoapp.Adapter.DoneTaskAdapter;
import com.tbm.bamau.todoapp.Adapter.TaskAdapter;
import com.tbm.bamau.todoapp.DbHelper;
import com.tbm.bamau.todoapp.Models.Task;
import com.tbm.bamau.todoapp.R;
import com.tbm.bamau.todoapp.UpdateTaskActivity;
import com.tbm.bamau.todoapp.ViewDetailTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.tbm.bamau.todoapp.MainActivity.hideSoftKeyboard;

public class ViewDoneTask_Fragment extends Fragment {

    DoneTaskAdapter taskAdapterDone;
    DbHelper database;
    SwipeMenuListView listTaskDone;
    List<Task> arrayListDone;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
    String currentDate = dateFormat.format(calendar.getTime());
    OnMessageReadListenerDone onMessageReadListenerDone;

    public interface OnMessageReadListenerDone{
        public void onMessageReadDone(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewdonetask_fragment, container, false);

        setupUI(view);
        listTaskDone = view.findViewById(R.id.swipelistview);
        setupAdapter(view);
        String[] cutMonth = currentDate.split(" ");
        getListDoneTaskOneMonth(cutMonth[0],cutMonth[1],1);
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
        listTaskDone.setMenuCreator(creator);
        listTaskDone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Task task = arrayListDone.get(position);
                int idTask = task.getIdTask();
                Intent intent = new Intent(getContext(), ViewDetailTask.class);
                intent.putExtra("ID",idTask);
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

    public  void getListDoneTaskOneMonth(String month, String year, int status){
        database = new DbHelper(getActivity());
        if (arrayListDone != null)
            arrayListDone.clear();
        arrayListDone=database.getListDoneTaskOneMonth(month,year,status);
        taskAdapterDone = new DoneTaskAdapter(getActivity(),R.layout.item_task_done, arrayListDone);
        listTaskDone.setAdapter(taskAdapterDone);
        if(taskAdapterDone!= null){
            taskAdapterDone.notifyDataSetChanged();
        }
    }

    public void updateListTaskWithChangeStatus(int status){
        database = new DbHelper(getActivity());
        if (arrayListDone != null)
            arrayListDone.clear();
        arrayListDone=database.getListTaskWithStatusOrderByYear(status);
        arrayListDone=database.getListTaskWithStatusOrderByMonth(status);
        arrayListDone=database.getListTaskWithStatusOrderByDay(status);
        arrayListDone=database.getListTaskWithStatusOrderByTime(status);
        taskAdapterDone = new DoneTaskAdapter(getActivity(),R.layout.item_task_done, arrayListDone);
        listTaskDone.setAdapter(taskAdapterDone);
        if(taskAdapterDone!= null){
            taskAdapterDone.notifyDataSetChanged();
        }
    }

    private void DialogXoaDoneTask(final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(getContext());
        dialogXoa.setMessage(R.string.do_you_want_delete_this_task);
        dialogXoa.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.deleteTask(database.getTaskById(id));
                Toast.makeText(getContext(), R.string.delete_success, Toast.LENGTH_SHORT).show();
                updateListTaskWithChangeStatus(1);
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
