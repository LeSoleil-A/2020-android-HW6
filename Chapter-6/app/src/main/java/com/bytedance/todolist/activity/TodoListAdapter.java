package com.bytedance.todolist.activity;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListItemHolder> {

    private Context mContext;
    private List<TodoListEntity> mDatas;
    private RecyclerView mRecyclerView;
    public Map<Integer, Boolean> map = new HashMap<>();

    public TodoListAdapter(Context context) {
        mDatas = new ArrayList<>();
        mContext = context;
    }

    @NonNull
    @Override
    public TodoListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false);
        final TodoListItemHolder holder = new TodoListItemHolder(view);
        mRecyclerView = parent.findViewById(R.id.rv_list);

        map.clear();
        for( int i=0; i<mDatas.size(); i++) {
            if (mDatas.get(i).mDone == 1) {
                map.put(i, true);
            }
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TodoListItemHolder holder, final int position) {
        holder.bind(mDatas.get(position));
        holder.mImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        TodoListDao dao = TodoListDatabase.inst(mContext).todoListDao();
                        TodoListEntity t = mDatas.get(position);
                        // Log.i("Entity", "done" + t.getDone());
                        for(int i=position; i<mDatas.size(); i++) {
                            map.remove(i);
                            if(map.containsKey(i+1)) {
                                map.put(i, true);
                            }
                        }
                        mDatas.remove(position);
                        map.remove(mDatas.size());
                        dao.delTodo(t);
                    }
                }.start();
                notifyDataSetChanged();
            }
        });

        holder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true ) {
                    map.put(position, true);
                }else if(isChecked == false) {
                    map.remove(position);
                }

                if (mRecyclerView.isComputingLayout()) {
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyItemChanged(holder.getAdapterPosition());
                        }
                    });
                } else {
                    notifyItemChanged(holder.getAdapterPosition());
                }
            }
        });
        if (map != null && map.containsKey(position)) {
            holder.mCheck.setChecked(true);
            mDatas.get(position).setmDone_true();
            new Thread() {
                @Override
                public void run() {
                    TodoListDao dao = TodoListDatabase.inst(mContext).todoListDao();
                    TodoListEntity t = mDatas.get(position);
                    t.setmDone_true();
                    // Log.i("Entity", "done" + t.getDone());
                    dao.updateTodo(t);
                }
            }.start();
            holder.mContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.mCheck.setChecked(false);
            mDatas.get(position).setmDone_false();
            new Thread() {
                @Override
                public void run() {
                    TodoListDao dao = TodoListDatabase.inst(mContext).todoListDao();
                    TodoListEntity t = mDatas.get(position);
                    t.setmDone_false();
                    // Log.i("Entity", "done" + t.getDone());
                    dao.updateTodo(t);
                }
            }.start();
            holder.mContent.setPaintFlags( holder.mContent.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @MainThread
    public void setData(List<TodoListEntity> list) {
        mDatas = list;
        notifyDataSetChanged();
    }
}
