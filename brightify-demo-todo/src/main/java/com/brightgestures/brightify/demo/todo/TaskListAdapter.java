package com.brightgestures.brightify.demo.todo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.brightgestures.brightify.demo.todo.model.Task;
import com.brightgestures.brightify.demo.todo.view.TaskItemView;
import com.brightgestures.brightify.util.Callback;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

import java.util.List;

import static com.brightgestures.brightify.BrightifyService.bfy;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
@EBean
public class TaskListAdapter extends BaseAdapter {

    List<Task> tasks;

    @RootContext
    Context context;

    @Bean
    AuthManager authManager;

    @AfterInject
    void initAdapter() {
        bfy().load().type(Task.class).filter("ownerId = ?", authManager.getLoggedUser().getId()).async(
                new Callback<List<Task>>() {
                    @Override
                    public void onSuccess(List<Task> data) {
                        tasks = data;
                    }

                    @Override
                    public void onFailure(Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public int getCount() {
        return tasks != null ? tasks.size() : 0;
    }

    @Override
    public Task getItem(int position) {
        return tasks != null ? tasks.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskItemView itemView;
        if(convertView == null) {
            itemView = TaskItemView_.build(context);
        } else {
            itemView = (TaskItemView) convertView;
        }

        itemView.bind(getItem(position));

        return itemView;
    }
}
