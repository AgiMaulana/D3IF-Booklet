package lab.agimaulana.d3ifbooklet.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.ProjectList;
import lab.agimaulana.d3ifbooklet.model.viewholder.ProjectItemViewHolder;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class RecyclerProjectList extends RecyclerView.Adapter<ProjectItemViewHolder> implements View.OnClickListener {

    private String[] level = new String[]{"Proyek Tingkat I", "Proyek Tingkat II", "Proyek Akhir"};
    private int[] color = new int[]{R.color.amber_500, R.color.green_500, R.color.red_500};
    private OnItemClickListener mOnItemClickListener;
    private int position;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ProjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == 0)
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_headline, parent, false);
        else
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_project_list, parent, false);
        ProjectItemViewHolder viewHolder = new ProjectItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ProjectItemViewHolder holder, int position) {
        this.position = position;
        Project project = ProjectList.projects.get(position);
        String url = "http://192.168.43.185/" + project.getPoster();
        Picasso.with(holder.itemView.getContext()).load(Uri.parse(url)).into(holder.getImageField(), new Callback() {
            @Override
            public void onSuccess() {
                holder.getProgressBar().setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.getProgressBar().setVisibility(View.GONE);
            }
        });

        String title = project.getTitle();
        title = title.length() > 100 ? title.substring(0, 100) : title;
        holder.getTitleField().setText(title);

        if(position > 0) {
            int i = 2;
            holder.getLevelField().setText(level[i]);
            holder.getLevelField().setTextColor(holder.getLevelField().getContext().getResources()
            .getColor(color[i]));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null)
                    mOnItemClickListener.onClick(v, holder.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ProjectList.projects.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;

        return 1;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null)
            mOnItemClickListener.onClick(v, position);
    }

    public interface OnItemClickListener{
        void onClick(View v, int position);
    }
}
