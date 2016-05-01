package lab.agimaulana.d3ifbooklet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.model.Student;
import lab.agimaulana.d3ifbooklet.model.viewholder.TwoLineListViewHolder;

/**
 * Created by Agi Maulana on 4/15/2016.
 */
public class DeveloperListAdapter extends BaseAdapter {
    private ArrayList<Student> developers = new ArrayList<>();
    public DeveloperListAdapter(ArrayList<Student> developers){
        this.developers = developers;
    }

    @Override
    public int getCount() {
        return developers.size();
    }

    @Override
    public Object getItem(int position) {
        return developers.get(position);
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

        Student student = developers.get(position);
        holder.text1.setText(student.getName());
        holder.text2.setText(student.getNIM());
        return convertView;
    }

}
