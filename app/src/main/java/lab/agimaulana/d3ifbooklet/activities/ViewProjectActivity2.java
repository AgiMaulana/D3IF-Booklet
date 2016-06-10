package lab.agimaulana.d3ifbooklet.activities;

        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.design.widget.AppBarLayout;
        import android.support.design.widget.CollapsingToolbarLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.AppCompatTextView;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.webkit.WebView;
        import android.widget.ArrayAdapter;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.youtube.player.YouTubeInitializationResult;
        import com.google.android.youtube.player.YouTubePlayer;
        import com.google.android.youtube.player.YouTubePlayerSupportFragment;
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
        import lab.agimaulana.d3ifbooklet.dialogs.YoutubeEmbedDialogFragment;
        import lab.agimaulana.d3ifbooklet.model.Lecturer;
        import lab.agimaulana.d3ifbooklet.model.Project;
        import lab.agimaulana.d3ifbooklet.model.ProjectList;
        import lab.agimaulana.d3ifbooklet.model.Screenshot;
        import lab.agimaulana.d3ifbooklet.model.Student;
        import lab.agimaulana.d3ifbooklet.util.PicassoUtils;
        import lab.agimaulana.d3ifbooklet.util.Utils;

/**
 * Created by Agi Maulana on 4/14/2016.
 */
public class ViewProjectActivity2 extends AppCompatActivity implements View.OnClickListener, RVScreenshotAdapter.OnClickListener, YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener, AppBarLayout.OnOffsetChangedListener {
    private AppBarLayout appBarLayout;

    private ImageView imgPoster;
    private RelativeLayout layoutVideoButton;
    private ProgressBar progressBarPoster;

    private Toolbar toolbar;
    private WebView mWebView;

    private AppCompatTextView tvTitle;
    private AppCompatTextView tvLevel;
    private RecyclerView recyclerScreenshot;
    private ProgressBar progressBarScreenshot;
    private RecyclerView recyclerDevelopers;
    private RecyclerView recyclerPreceptors;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private Project project;

    private YouTubePlayer youTubePlayer;
    private RelativeLayout youtubeLayout;
    private RelativeLayout youtubeToolbar;
    private RelativeLayout youtubeCloseButton;
    private RelativeLayout youtubeLaunchApp;

    private boolean youtubeIsPlaying = false;
    private int youtubeCurrentMilis = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_deep_orange_500_24dp);
        getSupportActionBar().setTitle("");
        setupWidget();

        project = (Project) getIntent().getSerializableExtra("project");
        if(project != null)
            displayData(project);
    }

    @Override
    protected void onStart() {
        super.onStart();
        appBarLayout.addOnOffsetChangedListener(this);
        imgPoster.setOnClickListener(this);
        youtubeCloseButton.setOnClickListener(this);
        youtubeLaunchApp.setOnClickListener(this);
        loadVideoThumbnail();
    }

    @Override
    public void onBackPressed() {
        if(youtubeLayout.getVisibility() == View.VISIBLE){
            if(youTubePlayer != null && youTubePlayer.isPlaying())
                youTubePlayer.pause();
            youtubeLayout.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    private void setupWidget(){
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);

        imgPoster = (ImageView) findViewById(R.id.imageview_poster);
        layoutVideoButton = (RelativeLayout) findViewById(R.id.relativeLayout);
        progressBarPoster = (ProgressBar) findViewById(R.id.progressbar);

/*
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
        appBarLayout.addOnOffsetChangedListener(this);*/

  /*      YouTubePlayerSupportFragment youtubePlayer = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        youtubePlayer.initialize(getString(R.string.YOUTUBE_KEY), this);
*/
        mWebView = (WebView) findViewById(R.id.webview);
        tvTitle = (AppCompatTextView) findViewById(R.id.textview_project_title);
        tvLevel = (AppCompatTextView) findViewById(R.id.textview_project_level);
        recyclerScreenshot = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerScreenshot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        progressBarScreenshot = (ProgressBar) findViewById(R.id.progressbar_2);
        recyclerDevelopers = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerDevelopers.setLayoutManager(new LinearLayoutManager(this));
        recyclerPreceptors = (RecyclerView) findViewById(R.id.recyclerview3);
        recyclerPreceptors.setLayoutManager(new LinearLayoutManager(this));

        youtubeLayout = (RelativeLayout) findViewById(R.id.youtube_layout);
        youtubeToolbar = (RelativeLayout) findViewById(R.id.toolbar_youtube);
        youtubeCloseButton = (RelativeLayout) findViewById(R.id.button_back);
        youtubeLaunchApp = (RelativeLayout) findViewById(R.id.button_launch);
    }

    private void displayData(Project project){
        getImagesUrl(project);
        tvLevel.setText(project.getLevel());
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
        imageUrls.add(project.getPoster());
        for(Screenshot sc : project.getScreenshots().getScreenshots())
            imageUrls.add(sc.getURL());
    }

    private void loadVideoThumbnail(){
        progressBarPoster.setVisibility(View.VISIBLE);
        layoutVideoButton.setVisibility(View.GONE);

        String videoThumbnail = "http://img.youtube.com/vi/" + project.getVideo() + "/hqdefault.jpg";
        Log.d("Picasso - poster", videoThumbnail);
        PicassoUtils.loadNoResize(this, videoThumbnail, imgPoster, new PicassoUtils.LoadCallback() {
            @Override
            public void onSuccess() {
                progressBarPoster.setVisibility(View.GONE);
                layoutVideoButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                imgPoster.setImageDrawable(getResources().getDrawable(R.drawable.default_youtube_thumbnail));
                progressBarPoster.setVisibility(View.GONE);
                layoutVideoButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        //openImageSlider(0);
        switch (v.getId()){
            case R.id.imageview_poster:
                try {
                    youtubeLayout.setVisibility(View.VISIBLE);
                    YouTubePlayerSupportFragment youtubePlayerFragment = new YouTubePlayerSupportFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, youtubePlayerFragment).commit();
                    youtubePlayerFragment.initialize(getString(R.string.YOUTUBE_KEY), this);

                    //openYoutubeVideo(project.getVideo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_back:
                youtubeLayout.setVisibility(View.GONE);
                break;
            case R.id.button_launch:
                openYoutubeVideo(project.getVideo());
                break;
            default:break;
        }
    }

    @Override
    public void onClick(View v, int position) {
        openImageSlider(position);
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
            //intent.putExtra("force_fullscreen",true);
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        youTubePlayer = player;
        if(!wasRestored)
            player.cueVideo(project.getVideo());
        player.setPlaybackEventListener(this);
        player.setPlayerStateChangeListener(new YoutubeState());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        provider.initialize(getString(R.string.YOUTUBE_KEY), this);
    }

    @Override
    public void onPlaying() {
        youtubeToolbar.setVisibility(View.GONE);
    }

    @Override
    public void onPaused() {
        youtubeToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopped() {
        youtubeToolbar.setVisibility(View.VISIBLE);
        youtubeCurrentMilis = 0;
    }

    @Override
    public void onBuffering(boolean b) {
        youtubeToolbar.setVisibility(View.GONE);
    }

    @Override
    public void onSeekTo(int i) {
        youtubeCurrentMilis = i;
    }

    private class YoutubeState implements YouTubePlayer.PlayerStateChangeListener{


        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {
            youTubePlayer.play();
        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            youtubeToolbar.setVisibility(View.VISIBLE);
        }
    }
}
