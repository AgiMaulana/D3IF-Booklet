package lab.agimaulana.d3ifbooklet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.ProjectList;
import lab.agimaulana.d3ifbooklet.model.viewholder.ProjectItemViewHolder;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class ListViewProjectList extends BaseAdapter {

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        else
            return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return ProjectList.projects.size();
    }

    @Override
    public Object getItem(int position) {
        return ProjectList.projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectItemViewHolder viewHolder;
        if(convertView == null) {
            if (getItemViewType(position) == 0)
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_headline, parent, false);
            else
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_project_list, parent, false);
            viewHolder = new ProjectItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ProjectItemViewHolder) convertView.getTag();
        }

        Project project = ProjectList.projects.get(position);
        viewHolder.getTitleField().setText(project.getTitle());

        return convertView;
    }
}
