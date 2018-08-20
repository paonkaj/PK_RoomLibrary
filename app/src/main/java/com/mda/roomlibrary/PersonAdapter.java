package com.mda.roomlibrary;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Model.Person;

public class PersonAdapter extends PagedListAdapter<Person, PersonAdapter.MyViewHolder> {


    protected PersonAdapter() {
        super(Person.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Person person = getItem(position);
        if (person != null) {
         holder.txt_show.setText(person.firstName);
            holder.txt_add.setText(person.address );
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_show,txt_add;

        public MyViewHolder(View view) {
            super(view);
            txt_show = (TextView) view.findViewById(R.id.txt_show);
            txt_add = (TextView) view.findViewById(R.id.txt_add);
        }
    }



}