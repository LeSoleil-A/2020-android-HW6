package com.bytedance.todolist.activity;

import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListItemHolder extends RecyclerView.ViewHolder {

    View actView;
    public CheckBox mCheck;
    public TextView mContent;
    public TextView mTimestamp;
    public ImageButton mImgBtn;
    private int mDone;

    public TodoListItemHolder(@NonNull View itemView) {
        super(itemView);
        actView = itemView;
        mContent = itemView.findViewById(R.id.tv_content);
        mTimestamp = itemView.findViewById(R.id.tv_timestamp);
        mCheck = itemView.findViewById(R.id.check_done);
        mImgBtn = itemView.findViewById(R.id.del_btn);
    }

    public void bind(final TodoListEntity entity) {
        mContent.setText(entity.getContent());
        mTimestamp.setText(formatDate(entity.getTime()));
        mDone = entity.getDone();
        // Log.i("Entity", "done" + mDone);
        if(mDone==1){
            mContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            mContent.setPaintFlags( mContent.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    private String formatDate(Date date) {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }
}
