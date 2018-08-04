package com.example.nearbyplacesapp.view.account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nearbyplacesapp.R;

public class AccountListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] options;
    private Integer[] imageID;


    public AccountListViewAdapter(@NonNull Context context, String[] options, Integer[] imageID) {
        super(context, R.layout.item_account_listview, options);
        this.context = context;
        this.options = options;
        this.imageID = imageID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.item_account_listview, parent, false);
        }

        TextView textView = row.findViewById(R.id.text_account_listView);
        ImageView imageView = row.findViewById(R.id.image_account_listView);

        textView.setText(options[position]);
        imageView.setImageResource(imageID[position]);
        return row;
    }
}
