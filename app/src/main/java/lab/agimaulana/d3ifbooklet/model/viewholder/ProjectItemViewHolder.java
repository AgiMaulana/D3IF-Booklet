package lab.agimaulana.d3ifbooklet.model.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import lab.agimaulana.d3ifbooklet.R;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class ProjectItemViewHolder extends ProjectListViewHolder {
    private View view;
    private ImageView imgProject;
    private ProgressBar progressBar;
    private TextView tvTitle;
    private TextView tvLevel;

    public ProjectItemViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        imgProject = (ImageView) itemView.findViewById(R.id.imageview_poster);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
        tvTitle = (TextView) itemView.findViewById(R.id.textview_project_title);
        tvLevel = (TextView) itemView.findViewById(R.id.textview_project_level);
    }

    public View getView() {
        return view;
    }

    public ImageView getImageField(){
        return imgProject;
    }

    public ProgressBar getProgressBar(){
        return progressBar;
    }

    public TextView getTitleField(){
        return tvTitle;
    }

    public TextView getLevelField(){
        return tvLevel;
    }
}
