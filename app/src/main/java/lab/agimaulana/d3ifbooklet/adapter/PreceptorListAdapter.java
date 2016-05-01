package lab.agimaulana.d3ifbooklet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.model.Lecturer;
import lab.agimaulana.d3ifbooklet.model.viewholder.TwoLineListViewHolder;

/**
 * Created by Agi Maulana on 4/15/2016.
 */
public class PreceptorListAdapter extends BaseAdapter {
    private ArrayList<Lecturer> preceptors = new ArrayList<>();
    public PreceptorListAdapter(ArrayList<Lecturer> preceptors){
        this.preceptors = preceptors;
    }

    @Override
    public int getCount() {
        return preceptors.size();
    }

    @Override
    public Object getItem(int position) {
        return preceptors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TwoLineListViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            holder = new TwoLineListViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (TwoLineListViewHolder) convertView.getTag();
        }

        Lecturer lecturer = preceptors.get(position);
        holder.text1.setText(lecturer.getName());
        holder.text2.setText(lecturer.getNIP());
        return convertView;
    }
}
