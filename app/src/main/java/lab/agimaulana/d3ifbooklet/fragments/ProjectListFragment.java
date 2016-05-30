package lab.agimaulana.d3ifbooklet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.activities.ViewProjectActivity2;
import lab.agimaulana.d3ifbooklet.adapter.RecyclerProjectList;
import lab.agimaulana.d3ifbooklet.config.BookletConfig;
import lab.agimaulana.d3ifbooklet.model.GetBooklet;
import lab.agimaulana.d3ifbooklet.model.Project;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class ProjectListFragment extends Fragment implements RecyclerProjectList.OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerProjectList projectAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_project, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupWidget();
        String bookletType = getArguments().getString("bookletType");
        if(bookletType != null)
            setupList(bookletType);
    }

    private void setupWidget(){
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupList(String bookletType){
        try {
            projectAdapter = new RecyclerProjectList(getActivity(), bookletType);
            projectAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(projectAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v, int position, Project project) {
        Intent intent = new Intent(getActivity(), ViewProjectActivity2.class);
        intent.putExtra("project", project);
        startActivity(intent);
    }
}
