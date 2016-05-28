package lab.agimaulana.d3ifbooklet.model.viewholder;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lab.agimaulana.d3ifbooklet.R;

/**
 * Created by root on 5/28/16.
 */
public class ItemCheckBox extends RecyclerView.ViewHolder {
    public AppCompatTextView text;
    public AppCompatCheckBox checkBox;
    public ItemCheckBox(View itemView) {
        super(itemView);
        text = (AppCompatTextView) itemView.findViewById(R.id.text);
        checkBox = (AppCompatCheckBox) itemView.findViewById(R.id.checkbox);
    }
}
