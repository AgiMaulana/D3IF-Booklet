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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.activities.ViewProjectActivity2;
import lab.agimaulana.d3ifbooklet.adapter.ListViewProjectList;
import lab.agimaulana.d3ifbooklet.adapter.RecyclerProjectList;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.GetBooklet;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.ProjectList;
import retrofit2.Response;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class MainActivityFragment extends Fragment implements GetBooklet.FetchListener, RecyclerProjectList.OnItemClickListener {
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerProjectList projectAdapter;
    private GetBooklet getBooklet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupWidget();
        recyclerView.setVisibility(View.GONE);
        getBooklet = new GetBooklet(this);
        getBooklet.execute();
    }

    private void setupWidget(){
        refreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBooklet.execute();
                //setupList();
                //Toast.makeText(getActivity(), "REFRESHED", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
        refreshLayout.setColorSchemeColors(R.color.deepOrange_500, R.color.green_500, R.color.red_500);
        progressBar = (ProgressBar) getView().findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupList(){
        projectAdapter = new RecyclerProjectList();
        projectAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(projectAdapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onResponse(Response<Booklet> response) {
        if(response.isSuccessful()){
            if(response.body().getProjects().size() == 0)
                Toast.makeText(getActivity(), "000000000000000000", Toast.LENGTH_SHORT).show();
            else
                setupList();
        }else{
            try {
                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(getActivity(), ViewProjectActivity2.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
