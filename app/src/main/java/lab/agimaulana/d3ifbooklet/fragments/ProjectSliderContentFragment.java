package lab.agimaulana.d3ifbooklet.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.activities.ImageSliderActivity;
import lab.agimaulana.d3ifbooklet.activities.MainActivity;
import lab.agimaulana.d3ifbooklet.adapter.RVDeveloperAdapter;
import lab.agimaulana.d3ifbooklet.adapter.RVPreceptorsAdapter;
import lab.agimaulana.d3ifbooklet.adapter.RVScreenshotAdapter;
import lab.agimaulana.d3ifbooklet.model.Lecturer;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.ProjectList;
import lab.agimaulana.d3ifbooklet.model.Screenshot;
import lab.agimaulana.d3ifbooklet.model.Student;
import lab.agimaulana.d3ifbooklet.util.PicassoUtils;

/**
 * Created by Agi Maulana on 4/19/2016.
 */
public class ProjectSliderContentFragment extends Fragment implements View.OnClickListener, RVScreenshotAdapter.OnClickListener {
    private ImageView imgPoster;
    private ProgressBar progressBarPoster;
    private WebView mWebView;
    private TextView tvTitle;
    private RecyclerView recyclerScreenshot;
    private ProgressBar progressBarScreenshot;
    private RecyclerView recyclerDevelopers;
    private RecyclerView recyclerPreceptors;
    private ArrayList<String> imageUrls = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_view_project_2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupWidget();
    }

    private void setupWidget(){
        imgPoster = (ImageView) getView().findViewById(R.id.imageview_poster);
        imgPoster.setOnClickListener(this);
        progressBarPoster = (ProgressBar) getView().findViewById(R.id.progressbar);
        mWebView = (WebView) getView().findViewById(R.id.webview);
        tvTitle = (TextView) getView().findViewById(R.id.textview_project_title);
        recyclerScreenshot = (RecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerScreenshot.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        progressBarScreenshot = (ProgressBar) getView().findViewById(R.id.progressbar_2);
        recyclerDevelopers = (RecyclerView) getView().findViewById(R.id.recyclerview2);
        recyclerDevelopers.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerPreceptors = (RecyclerView) getView().findViewById(R.id.recyclerview3);
        recyclerPreceptors.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void displayData(){
        int position = getArguments().getInt("position", -1);
        if(position < 0){
            startActivity(new Intent(getActivity(), MainActivity.class));
            return;
        }
        Project project = ProjectList.projects.get(position);
        getImagesUrl(project);
        String posterUrl = "http://192.168.43.185:8000/" + project.getPoster();
        Log.d("Picasso - poster", posterUrl);
        PicassoUtils.load(getActivity(), posterUrl, imgPoster, new PicassoUtils.LoadCallback() {
            @Override
            public void onSuccess() {
                progressBarPoster.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

        tvTitle.setText(project.getTitle());
        String htmlFormat = "<div style='text-align: justify;'>"+ project.getDescription() +"</div>";
        mWebView.loadData(htmlFormat, "text/html", "utf-8");
        RVScreenshotAdapter screenshotAdapter = new RVScreenshotAdapter(project);
        screenshotAdapter.setOnClickListener(this);
        recyclerScreenshot.setAdapter(screenshotAdapter);
        screenshotAdapter.setLoadListener(new RVScreenshotAdapter.LoadListener() {
            @Override
            public void onSuccess(int currentPosition) {
                recyclerScreenshot.setVisibility(View.VISIBLE);
                progressBarScreenshot.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int currentPosition) {
                recyclerScreenshot.setVisibility(View.VISIBLE);
                progressBarScreenshot.setVisibility(View.GONE);
            }
        });

        ArrayList<Student> developers = (ArrayList<Student>) project.getDevelopers().getDevelopers();
        RVDeveloperAdapter developerAdapter = new RVDeveloperAdapter(developers);
        recyclerDevelopers.setAdapter(developerAdapter);
        ArrayList<Lecturer> preceptors = (ArrayList<Lecturer>) project.getPreceptors().getPreceptors();
        RVPreceptorsAdapter preceptorsAdapter = new RVPreceptorsAdapter(preceptors);
        recyclerPreceptors.setAdapter(preceptorsAdapter);
    }

    private void getImagesUrl(Project project){
        imageUrls.add("http://192.168.43.185/" + project.getPoster());
        for(Screenshot sc : project.getScreenshots().getScreenshots())
            imageUrls.add("http://192.168.43.185/" + sc.getURL());
    }

    private void openImageSlider(int position){
        Intent intent = new Intent(getActivity(), ImageSliderActivity.class);
        intent.putStringArrayListExtra("images", imageUrls);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        openImageSlider(0);
    }

    @Override
    public void onClick(View v, int position) {
        openImageSlider(position);
    }
}
