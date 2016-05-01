package lab.agimaulana.d3ifbooklet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.model.Student;
import lab.agimaulana.d3ifbooklet.model.viewholder.TwoLineListViewHolder;

/**
 * Created by Agi Maulana on 4/15/2016.
 */
public class RVDeveloperAdapter extends RecyclerView.Adapter<TwoLineListViewHolder> {
    private ArrayList<Student> developers;
    public RVDeveloperAdapter(ArrayList<Student> developers){
        this.developers = developers;
    }

    @Override
    public TwoLineListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_two_line_list, parent, false);
        return new TwoLineListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TwoLineListViewHolder holder, int position) {
        Student student = developers.get(position);
        holder.text1.setText(student.getName());
        holder.text2.setText(student.getNIM());
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }
}
