package com.hk.single_selection_recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hk.R;

import java.util.ArrayList;

/**
 * Created by Hovhannisyan.Karo on 03.01.2018.
 */

public class SingleSelectionAdapter extends RecyclerView.Adapter<SingleSelectionAdapter.MyViewHolder> {

    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private LayoutInflater inflater;
    private Context context;

    private ArrayList<MFont> fonts = new ArrayList<>();

    public SingleSelectionAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 10; i++) {
            fonts.add(new MFont("Text " + i, false));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(inflater.inflate(R.layout.layout_single_selection, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(fonts.get(position).getName());
        holder.checkBox.setChecked(fonts.get(position).isSelected());
        holder.checkBox.setTag(new Integer(position));

        if (position == 0 && fonts.get(0).isSelected() && holder.checkBox.isChecked()) {
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                int clickedPos = ((Integer) cb.getTag()).intValue();

                if (cb.isChecked()) {
                    if (lastChecked != null) {
                        lastChecked.setChecked(false);
                        fonts.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                } else
                    lastChecked = null;

                fonts.get(clickedPos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return fonts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}
