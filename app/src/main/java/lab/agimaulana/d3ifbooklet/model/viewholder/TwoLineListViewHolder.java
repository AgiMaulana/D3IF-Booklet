package lab.agimaulana.d3ifbooklet.model.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Agi Maulana on 4/15/2016.
 */
public class TwoLineListViewHolder extends RecyclerView.ViewHolder{
    public TextView text1;
    public TextView text2;
    public TwoLineListViewHolder(View itemView) {
        super(itemView);
        text1 = (TextView) itemView.findViewById(android.R.id.text1);
        text2 = (TextView) itemView.findViewById(android.R.id.text2);
    }
}
