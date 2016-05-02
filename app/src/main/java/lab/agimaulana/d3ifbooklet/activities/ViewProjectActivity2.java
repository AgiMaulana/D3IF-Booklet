package lab.agimaulana.d3ifbooklet.activities;

        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.webkit.WebView;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import com.squareup.picasso.Callback;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.Collections;

        import lab.agimaulana.d3ifbooklet.R;
        import lab.agimaulana.d3ifbooklet.adapter.DeveloperListAdapter;
        import lab.agimaulana.d3ifbooklet.adapter.PreceptorListAdapter;
        import lab.agimaulana.d3ifbooklet.adapter.RVDeveloperAdapter;
        import lab.agimaulana.d3ifbooklet.adapter.RVPreceptorsAdapter;
        import lab.agimaulana.d3ifbooklet.adapter.RVScreenshotAdapter;
        import lab.agimaulana.d3ifbooklet.helper.Helper;
        import lab.agimaulana.d3ifbooklet.model.Lecturer;
        import lab.agimaulana.d3ifbooklet.model.Project;
        import lab.agimaulana.d3ifbooklet.model.ProjectList;
        import lab.agimaulana.d3ifbooklet.model.Screenshot;
        import lab.agimaulana.d3ifbooklet.model.Student;

/**
 * Created by Agi Maulana on 4/14/2016.
 */
public class ViewProjectActivity2 extends AppCompatActivity implements View.OnClickListener, RVScreenshotAdapter.OnClickListener {
    private ImageView imgPoster;
    private RelativeLayout layoutVideoButton;
    private ProgressBar progressBarPoster;
    private WebView mWebView;
    private TextView tvTitle;
    private RecyclerView recyclerScreenshot;
    private ProgressBar progressBarScreenshot;
    private RecyclerView recyclerDevelopers;
    private RecyclerView recyclerPreceptors;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private Helper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_deep_orange_500_36dp);
        getSupportActionBar().setTitle("");
        helper = new Helper(this);
        setupWidget();
        displayData();
    }

    private void setupWidget(){
        imgPoster = (ImageView) findViewById(R.id.imageview_poster);
        imgPoster.setOnClickListener(this);
        layoutVideoButton = (RelativeLayout) findViewById(R.id.relativeLayout);
        layoutVideoButton.setVisibility(View.GONE);
        progressBarPoster = (ProgressBar) findViewById(R.id.progressbar);
        mWebView = (WebView) findViewById(R.id.webview);
        tvTitle = (TextView) findViewById(R.id.textview_project_title);
        recyclerScreenshot = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerScreenshot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        progressBarScreenshot = (ProgressBar) findViewById(R.id.progressbar_2);
        recyclerDevelopers = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerDevelopers.setLayoutManager(new LinearLayoutManager(this));
        recyclerPreceptors = (RecyclerView) findViewById(R.id.recyclerview3);
        recyclerPreceptors.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displayData(){
        int position = getIntent().getIntExtra("position", -1);
        if(position < 0){
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        //Project project = ProjectList.projects.get(position);
        Project project = helper.getProject(position);
        getImagesUrl(project);
        String videoThumbnail = "http://img.youtube.com/vi/" + project.getVideo() + "/hqdefault.jpg";
        Log.d("Picasso - poster", videoThumbnail);
        Picasso.with(this).load(Uri.parse(videoThumbnail)).into(imgPoster, new Callback() {
            @Override
            public void onSuccess() {
                layoutVideoButton.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {
        //openImageSlider(0);
        int position = getIntent().getIntExtra("position", -1);
        if(position < 0){
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        Project project = helper.getProject(position);
        openYoutubeVideo(project.getVideo());
    }

    @Override
    public void onClick(View v, int position) {
        openImageSlider(position+1);
    }

    private void openImageSlider(int position){
        Intent intent = new Intent(this, ImageSliderActivity.class);
        intent.putStringArrayListExtra("images", imageUrls);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void openYoutubeVideo(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            intent.putExtra("force_fullscreen",true);
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }
}
