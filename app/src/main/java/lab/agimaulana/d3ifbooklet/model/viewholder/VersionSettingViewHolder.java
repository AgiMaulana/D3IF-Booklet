package lab.agimaulana.d3ifbooklet.model.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lab.agimaulana.d3ifbooklet.R;

/**
 * Created by root on 5/30/16.
 */
public class VersionSettingViewHolder extends RecyclerView.ViewHolder{
    public TextView text1;
    public TextView text2;
    public VersionSettingViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setting_version, parent, false));
        text1 = (TextView) itemView.findViewById(android.R.id.text1);
        text2 = (TextView) itemView.findViewById(android.R.id.text2);
    }
}
