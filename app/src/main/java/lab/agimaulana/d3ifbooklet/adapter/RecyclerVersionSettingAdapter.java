package lab.agimaulana.d3ifbooklet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.BookletVersion;
import lab.agimaulana.d3ifbooklet.model.viewholder.VersionSettingViewHolder;

/**
 * Created by root on 5/30/16.
 */
public class RecyclerVersionSettingAdapter extends RecyclerView.Adapter<VersionSettingViewHolder> {
    private ArrayList<BookletVersion> bookletVersions;
    private OnOptionsSelectedListener listener;

    public RecyclerVersionSettingAdapter(ArrayList<BookletVersion> bookletVersions) {
        this.bookletVersions = bookletVersions;
    }

    public void setOnOptionsSelectedListener(OnOptionsSelectedListener listener) {
        this.listener = listener;
    }

    public void replaceItem(ArrayList<BookletVersion> bookletVersions){
        this.bookletVersions = bookletVersions;
    }

    @Override
    public VersionSettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VersionSettingViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(VersionSettingViewHolder holder, final int position) {
        BookletVersion b = bookletVersions.get(position);
        holder.text1.setText(b.getType());
        holder.text2.setText(holder.itemView.getContext().getString(R.string.versi_saat_ini, b.getVersion()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null)
                    listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookletVersions.size();
    }

    public interface OnOptionsSelectedListener{
        void onClick(int position);
    }
}
