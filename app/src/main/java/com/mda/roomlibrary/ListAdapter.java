//package com.mda.roomlibrary;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//
//import Model.Person;
//
//public class ListAdapter extends ArrayAdapter<Person> {
//    public ListAdapter(@NonNull Context context, List<Person> personList) {
//        super(context, 0,personList);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        Person p = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_view, parent, false);
//        }
//        // Lookup view for data population
//        TextView txt_show = (TextView) convertView.findViewById(R.id.txt_show);
//        // Populate the data into the template view using the data object
//        txt_show.setText(p.getName() + " " + p.getAge() + " " + p.getMobile_no());
//        return convertView;
//
//    }
//}
