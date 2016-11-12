package com.example.chunhliu9.scoreboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chunhliu9 on 11/11/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Items> item;


    public CustomListAdapter(Context _context, List<Items> _item) {
        this.context = _context;
        this.item = _item;

    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View scoreView, ViewGroup parent) {
        ViewHolder holder;
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (scoreView == null) {

            scoreView = inflater.inflate(R.layout.score_row, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) scoreView.findViewById(R.id.name);
            holder.score = (TextView) scoreView.findViewById(R.id.score);

            scoreView.setTag(holder);

        } else {
            holder = (ViewHolder) scoreView.getTag();
        }

        final Items m = item.get(position);
        holder.name.setText(m.getName());
        holder.score.setText(m.getScore());

        return scoreView;
    }

    static class ViewHolder {

        TextView name;
        TextView score;

    }
}
