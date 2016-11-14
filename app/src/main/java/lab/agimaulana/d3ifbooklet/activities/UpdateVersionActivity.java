package lab.agimaulana.d3ifbooklet.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.API.APIClient;
import lab.agimaulana.d3ifbooklet.API.ServiceAdapter;
import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.config.BookletConfig;
import lab.agimaulana.d3ifbooklet.model.BookletVersion;
import lab.agimaulana.d3ifbooklet.util.Cache;
import lab.agimaulana.d3ifbooklet.util.GetBooklet;
import lab.agimaulana.d3ifbooklet.model.Version;
import lab.agimaulana.d3ifbooklet.util.Utils;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 5/30/16.
 */
public class UpdateVersionActivity extends AppCompatActivity implements View.OnClickListener, GetBooklet.FetchListener, Callback<Version> {

    private TextView tvNewVersion;
    private RelativeLayout btnCheck;
    private LinearLayout btnDownload;
    private ProgressBar progressCheckVersion, progressDownload;

    private ArrayList<GetBooklet> downloader;
    private GetBooklet downloadVersionFile;
    private int position;
    private BookletVersion bookletVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_update);
        initWidget();
        downloader = new ArrayList<>();
        downloader.add(new GetBooklet(this, BookletConfig.URL_BOOKLET_PT1, BookletConfig.FILE_BOOKLET_PT1));
        downloader.add(new GetBooklet(this, BookletConfig.URL_BOOKLET_PT2, BookletConfig.FILE_BOOKLET_PT2));
        downloader.add(new GetBooklet(this, BookletConfig.URL_BOOKLET_PA, BookletConfig.FILE_BOOKLET_PA));

        for (GetBooklet g : downloader)
            g.setListener(this);

        downloadVersionFile = new GetBooklet(this, BookletConfig.URL_VERSION, BookletConfig.FILE_VERSION);
        position = getIntent().getIntExtra("position", -1);
        if(position == -1){
            finish();
            return;
        }

        Version version = null;
        try {
            version = Utils.BookletVersion(this);
            bookletVersion = version.getBookletVersions().get(position);
            getSupportActionBar().setTitle(bookletVersion.getType());
        } catch (Exception e) {
            finish();
            return;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupContent(position);
    }

    private void initWidget(){
//        tvBookletType = (TextView) findViewById(R.id.textview_booklet_type);
//        tvCurrentVersion = (TextView) findViewById(R.id.textview_booklet_version);
        tvNewVersion = (TextView) findViewById(R.id.textview_versi_baru);
        btnCheck = (RelativeLayout) findViewById(R.id.button_check_version);
        btnDownload = (LinearLayout) findViewById(R.id.button_download);
        progressCheckVersion = (ProgressBar) findViewById(R.id.progressBar);
        progressDownload = (ProgressBar) findViewById(R.id.progressBar2);
    }

    private void setupContent(int position){
//        tvBookletType.setText(bookletVersion.getType());
//        tvCurrentVersion.setText(getString(R.string.versi_saat_ini, bookletVersion.getVersion()));

        btnCheck.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_check_version:
                checkVersion();
                break;
            case R.id.button_download:
                progressDownload.setVisibility(View.VISIBLE);
                downloader.get(position).execute();
                break;
            default:break;
        }
    }

    @Override
    public void onSuccess() {
        downloadVersionFile.setListener(new GetBooklet.FetchListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Cache.clearCache(getApplicationContext());
                        Toast.makeText(UpdateVersionActivity.this, "Berhasil diunduh", Toast.LENGTH_SHORT).show();
                        tvNewVersion.setVisibility(View.INVISIBLE);
                        progressDownload.setVisibility(View.GONE);
                        btnDownload.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(Throwable t) {

            }
        });
        downloadVersionFile.execute();
    }

    @Override
    public void onError(Throwable t) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDownload.setVisibility(View.GONE);
                Toast.makeText(UpdateVersionActivity.this, "Tidak dapat mengunduh. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkVersion(){
        progressCheckVersion.setVisibility(View.VISIBLE);
        APIClient apiClient = ServiceAdapter.createService(APIClient.class);
        retrofit2.Call<Version> call = apiClient.getVersion();
        call.enqueue(this);
    }

    @Override
    public void onResponse(retrofit2.Call<Version> call, Response<Version> response) {
        if(response.isSuccessful()){
            progressCheckVersion.setVisibility(View.GONE);
            //BookletVersion b = response.body().getBookletVersions().get(position);

            for(BookletVersion b : response.body().getBookletVersions())
                if(b.getType().equalsIgnoreCase(bookletVersion.getType())) {
                    tvNewVersion.setText(getString(R.string.tersedia_versi_baru, b.getVersion()));
                    tvNewVersion.setVisibility(View.VISIBLE);
                    btnDownload.setVisibility(View.VISIBLE);
                }
        }
    }

    @Override
    public void onFailure(retrofit2.Call<Version> call, Throwable t) {
        progressCheckVersion.setVisibility(View.GONE);
        Toast.makeText(UpdateVersionActivity.this, "Tidak dapat memerika. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }
}
