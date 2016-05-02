package lab.agimaulana.d3ifbooklet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.RealmList;
import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.model.Lecturer;
import lab.agimaulana.d3ifbooklet.model.viewholder.TwoLineListViewHolder;

/**
 * Created by Agi Maulana on 4/15/2016.
 */
public class RVPreceptorsAdapter extends RecyclerView.Adapter<TwoLineListViewHolder> {
    private ArrayList<Lecturer> preceptors;
    public RVPreceptorsAdapter(ArrayList<Lecturer> preceptors){
        this.preceptors = preceptors;
    }

    @Override
    public TwoLineListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_two_line_list, parent, false);
        return new TwoLineListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TwoLineListViewHolder holder, int position) {
        Lecturer lecturer = preceptors.get(position);
        holder.text1.setText(lecturer.getName());
        holder.text2.setText(lecturer.getNIP());
    }

    @Override
    public int getItemCount() {
        return preceptors.size();
    }
}
