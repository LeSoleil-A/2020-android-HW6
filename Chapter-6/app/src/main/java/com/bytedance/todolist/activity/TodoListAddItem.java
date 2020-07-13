package com.bytedance.todolist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class TodoListAddItem extends AppCompatActivity {

    private EditText edit_act;
    private Button btn_com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_add_item);
        edit_act = findViewById(R.id.edit_item);
        btn_com = findViewById(R.id.btn_commit);
        btn_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.putExtra("activity", edit_act.getText().toString());
                        setResult(RESULT_OK, intent);
                        TodoListDao dao = TodoListDatabase.inst(TodoListAddItem.this).todoListDao();
                        dao.addTodo(new TodoListEntity(edit_act.getText().toString(), new Date(System.currentTimeMillis())));
                        finish();
                    }
                }.start();
            }
        });
    }
}
