package lab.agimaulana.d3ifbooklet.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.activities.ViewProjectActivity2;
import lab.agimaulana.d3ifbooklet.adapter.RecyclerProjectList;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.util.Cache;
import lab.agimaulana.d3ifbooklet.util.Utils;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class ProjectListFragment extends Fragment implements RecyclerProjectList.OnItemClickListener, Utils.BookletSerializer.Callback, Cache.LoadAsyncCallback, View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerProjectList projectAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton fab;

    private String bookletType;

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
        if(bookletType != null) {
            //setupList(bookletType);
            this.bookletType = bookletType;
            if(Cache.isCached(getActivity(), bookletType)){
                Cache.readBookletAsync(getActivity(), bookletType, this);
            }else {
                Utils.BookletSerializer bookletSerializer = new Utils.BookletSerializer(getActivity(), bookletType);
                bookletSerializer.setCallback(this);
                bookletSerializer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    private void setupWidget(){
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setOnScrollListener(new HideShowFAB());
        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void setupList(Booklet booklet){
        if(booklet == null)
            return;
        projectAdapter = new RecyclerProjectList(booklet);
        projectAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v, int position, Project project) {
        Intent intent = new Intent(getActivity(), ViewProjectActivity2.class);
        intent.putExtra("project", project);
        startActivity(intent);
    }

    @Override
    public void onSerialized(Booklet booklet) {
        setupList(booklet);
        Cache.write(getActivity(), bookletType, booklet);
        Log.d("booklet cache","loaded from memory " + bookletType);
    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void onLoaded(Booklet booklet) {
        setupList(booklet);
        Log.d("booklet cache","loaded from cache " + bookletType);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab)
            recyclerView.smoothScrollToPosition(0);
    }

    private class HideShowFAB extends RecyclerView.OnScrollListener{

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if(dy > 0){
                fab.hide();
            }

            if(dy < 0){
                fab.show();
            }

            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if(layoutManager.findFirstCompletelyVisibleItemPosition() == 0)
                fab.hide();

        }
    }

    public void hideFab(){
        if(fab != null)
            fab.hide();
    }
}
