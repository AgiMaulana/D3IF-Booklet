package lab.agimaulana.d3ifbooklet.model.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lab.agimaulana.d3ifbooklet.R;

/**
 * Created by Agi Maulana on 4/14/2016.
 */
public class ScreenshotViewHolder extends RecyclerView.ViewHolder{
    public View viewClickLayer;
    public ImageView imgScreenshot;
    public ScreenshotViewHolder(View itemView) {
        super(itemView);
        viewClickLayer = itemView.findViewById(R.id.view);
        imgScreenshot = (ImageView) itemView.findViewById(R.id.imageview_poster);
    }
}
