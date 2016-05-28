package lab.agimaulana.d3ifbooklet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.activities.ViewProjectActivity2;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.viewholder.ProjectItemViewHolder;
import lab.agimaulana.d3ifbooklet.util.PicassoUtils;
import lab.agimaulana.d3ifbooklet.util.Utils;

/**
 * Created by root on 5/27/16.
 */
public class RecyclerSearchProjectAdapter extends RecyclerView.Adapter<ProjectItemViewHolder>{
    private ArrayList<Project> projects;

    public RecyclerSearchProjectAdapter(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public void clear(){
        projects.clear();
        notifyDataSetChanged();
    }

    @Override
    public ProjectItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project_list, parent, false);
        v.setAnimation(AnimationUtils.makeInAnimation(v.getContext(), true));
        return new ProjectItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProjectItemViewHolder holder, int position) {
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

        final Context context = holder.itemView.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewProjectActivity2.class);
                intent.putExtra("project", project);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}
