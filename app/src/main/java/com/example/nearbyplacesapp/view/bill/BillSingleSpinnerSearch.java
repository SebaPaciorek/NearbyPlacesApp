package com.example.nearbyplacesapp.view.bill;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nearbyplacesapp.R;
import com.example.nearbyplacesapp.SingleSpinnerListener;
import com.example.nearbyplacesapp.model.places.bill.PlaceModel;

import java.util.ArrayList;
import java.util.List;

public class BillSingleSpinnerSearch extends AppCompatSpinner implements DialogInterface.OnCancelListener {
    private static final String TAG = BillSingleSpinnerSearch.class.getSimpleName();
    private List<PlaceModel> items;
    private String defaultText = "";
    private String spinnerTitle = "";
    private SingleSpinnerListener listener;
    private int limit = -1;
    private int selected = 0;
    private LimitExceedListener limitListener;
    MyAdapter adapter;
    public static AlertDialog.Builder builder;
    public static AlertDialog alertDialog;

    public BillSingleSpinnerSearch(Context context) {
        super(context);
    }

    public BillSingleSpinnerSearch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MultiSpinnerSearch);
        for (int i = 0; i < typedArray.getIndexCount(); ++i) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.MultiSpinnerSearch_hintText) {
                spinnerTitle = typedArray.getString(attr);
                defaultText = spinnerTitle;
                break;
            }
        }
        typedArray.recycle();
    }

    public BillSingleSpinnerSearch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setLimit(int limit, LimitExceedListener listener) {
        this.limit = limit;
        this.limitListener = listener;
    }

    public List<PlaceModel> getSelectedItems() {
        List<PlaceModel> selectedItems = new ArrayList<>();
        for (PlaceModel item : items) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    public List<Long> getSelectedIds() {
        List<Long> selectedItemsIds = new ArrayList<>();
        for (PlaceModel item : items) {
            if (item.isSelected()) {
                selectedItemsIds.add(item.getId());
            }
        }
        return selectedItemsIds;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        StringBuilder spinnerBuffer = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isSelected()) {
                spinnerBuffer.append(items.get(i).getName());
                spinnerBuffer.append(", ");
            }
        }

        String spinnerText = spinnerBuffer.toString();
        if (spinnerText.length() > 2)
            spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        else
            spinnerText = defaultText;

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.textview_for_spinner, new String[]{spinnerText});
        setAdapter(adapterSpinner);

        if (adapter != null)
            adapter.notifyDataSetChanged();

        listener.onItemsSelected(items);
    }

    @Override
    public boolean performClick() {

        builder = new AlertDialog.Builder(getContext(), R.style.myDialog);
        builder.setTitle(spinnerTitle);

        final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = inflater.inflate(R.layout.alert_dialog_listview_search, null);
        builder.setView(view);

        final ListView listView = view.findViewById(R.id.alertSearchListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setFastScrollEnabled(false);
        adapter = new MyAdapter(getContext(), items);
        listView.setAdapter(adapter);

        final TextView emptyText =  view.findViewById(R.id.empty);
        listView.setEmptyView(emptyText);

        final EditText editText = view.findViewById(R.id.alertSearchEditText);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setOnCancelListener(this);
        alertDialog = builder.show();
        return true;
    }

    public void setItems(List<PlaceModel> items, int position, SingleSpinnerListener listener) {

        this.items = items;
        this.listener = listener;

        StringBuilder spinnerBuffer = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isSelected()) {
                spinnerBuffer.append(items.get(i).getName());
                spinnerBuffer.append(", ");
            }
        }
        if (spinnerBuffer.length() > 2)
            defaultText = spinnerBuffer.toString().substring(0, spinnerBuffer.toString().length() - 2);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.textview_for_spinner, new String[]{defaultText});
        setAdapter(adapterSpinner);

        if (position != -1) {
            items.get(position).setSelected(true);
           // listener.onItemsSelected(items);
            onCancel(null);
        }
    }

    public interface LimitExceedListener {
        void onLimitListener(PlaceModel data);
    }

    public class MyAdapter extends BaseAdapter implements Filterable {

        List<PlaceModel> arrayList;
        List<PlaceModel> mOriginalValues;
        LayoutInflater inflater;

        public MyAdapter(Context context, List<PlaceModel> arrayList) {
            this.arrayList = arrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView textView;
            CheckBox checkBox;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_listview_multiple, parent, false);
                holder.textView = convertView.findViewById(R.id.alertTextView);
                holder.checkBox = convertView.findViewById(R.id.alertCheckbox);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final int backgroundColor = (position % 2 == 0) ? R.color.list_even : R.color.list_odd;
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), backgroundColor));

            final PlaceModel data = arrayList.get(position);

            holder.textView.setText(data.getName());
            holder.textView.setTypeface(null, Typeface.NORMAL);
            holder.checkBox.setChecked(data.isSelected());

            convertView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (data.isSelected()) {
                        selected--;
                    } else if (selected == limit) {
                        if (limitListener != null)
                            limitListener.onLimitListener(data);
                        return;
                    } else {
                        selected++;
                    }

                    final ViewHolder temp = (ViewHolder) v.getTag();
                    temp.checkBox.setChecked(!temp.checkBox.isChecked());

                    data.setSelected(!data.isSelected());
                    notifyDataSetChanged();
                }
            });
            if (data.isSelected()) {
                holder.textView.setTypeface(null, Typeface.BOLD);
//                holder.textView.setTextColor(Color.WHITE);
                convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.list_selected));
            }
            holder.checkBox.setTag(holder);

            return convertView;
        }

        @SuppressLint("DefaultLocale")
        @Override
        public Filter getFilter() {
            return new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    arrayList = (List<PlaceModel>) results.values;
                    notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<PlaceModel> FilteredArrList = new ArrayList<>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<>(arrayList);
                    }

                    if (constraint == null || constraint.length() == 0) {

                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).getName();
                            if (data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(mOriginalValues.get(i));
                            }
                        }
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
        }
    }
}