package lab.agimaulana.d3ifbooklet.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.ProjectList;
import lab.agimaulana.d3ifbooklet.model.viewholder.ProjectItemViewHolder;
import lab.agimaulana.d3ifbooklet.util.PicassoUtils;
import lab.agimaulana.d3ifbooklet.util.Utils;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class RecyclerProjectList extends RecyclerView.Adapter<ProjectItemViewHolder>{
    private OnItemClickListener mOnItemClickListener;
    private int position;
    private List<Project> projects;

    public RecyclerProjectList(Context context, String bookletName) throws Exception {
        Booklet booklet = Utils.Booklet(context, bookletName);
        projects = new ArrayList<>();
        projects = booklet.getProjects();
        //projects.addAll(booklet.getProjects());
    }

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
//        Project project = ProjectList.projects.get(position);
        final Project project = projects.get(position);
        String url = project.getPoster();
        PicassoUtils.load(holder.itemView.getContext(), url, holder.getImageField(), new PicassoUtils.LoadCallback() {
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
        holder.getLevelField().setText(project.getLevel());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null)
                    mOnItemClickListener.onClick(v, holder.getLayoutPosition(), project);
            }
        });

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;

        return 1;
    }

    public interface OnItemClickListener{
        void onClick(View v, int position, Project project);
    }
}
