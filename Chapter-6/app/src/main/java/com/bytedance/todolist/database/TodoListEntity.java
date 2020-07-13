package com.bytedance.todolist.database;

import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Entity(tableName = "todo")
public class TodoListEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long mId;

    @ColumnInfo(name = "content")
    private String mContent;

    @ColumnInfo(name = "time")
    private Date mTime;

    @ColumnInfo(name = "done")
    public int mDone;

    public TodoListEntity(String mContent, Date mTime) {
        this.mContent = mContent;
        this.mTime = mTime;
        this.mDone = 0;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date mTime) {
        this.mTime = mTime;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public void setmDone_true() {
        this.mDone = 1;
    }

    public void setmDone_false() {
        this.mDone = 0;
    }

    public int getDone() {
        return mDone;
    }
}
