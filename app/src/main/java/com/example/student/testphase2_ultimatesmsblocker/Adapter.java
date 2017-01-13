package com.example.student.testphase2_ultimatesmsblocker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> items;

    private final int User = 0;
    Context context;

    Adapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof User) {
            return User;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case User:
                View v1 = inflater.inflate(R.layout.adapter_view_items, viewGroup, false);
                viewHolder = new ViewHolderUser(v1);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case User:
                ViewHolderUser view1 = (ViewHolderUser) viewHolder;
                configureViewHolderExpense(view1, position);
                break;
        }
    }

    private void configureViewHolderExpense(ViewHolderUser viewHolder, int position) {
        User user = (User) items.get(position);
        if (user != null) {
            viewHolder.getId().setText("Id: "+user.getId());
            viewHolder.getName().setText("Name: "+user.getTitle());
            viewHolder.getPassword().setText("Password: "+user.getPassword());
            viewHolder.getEmail().setText("Email: "+user.getEmail());
            viewHolder.getContact().setText("Contact: "+user.getContact());
            viewHolder.getUsertype().setText("Type: "+user.getType());
            viewHolder.getRegDate().setText("Registration Date: "+user.getRegDate());
            viewHolder.getModfDate().setText("Modify Date: "+user.getModfDate());
        }
    }
}
