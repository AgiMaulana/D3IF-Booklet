package lab.agimaulana.d3ifbooklet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.config.BookletConfig;
import lab.agimaulana.d3ifbooklet.config.BookletType;
import lab.agimaulana.d3ifbooklet.config.Preference;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.GetBooklet;

/**
 * Created by root on 5/29/16.
 */
public class FirstRunActivity extends AppCompatActivity implements GetBooklet.FetchListener, View.OnClickListener {
    private ProgressBar progressBar;
    private AppCompatTextView tvError;
    private GetBooklet getPA, getPT1, getPT2, getVersion;
    private int downloadCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Preference.isFirstRun(this)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_first_run);
        initWidget();
        setupDefaultAppSetting();
        setupBookletData();
        startDownload();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvError.setOnClickListener(this);
    }

    private void initWidget(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvError = (AppCompatTextView) findViewById(R.id.error);
    }

    private void setupDefaultAppSetting(){
        Preference.setSearchSetting(this, BookletType.PT1, true);
        Preference.setSearchSetting(this, BookletType.PT2, true);
        Preference.setSearchSetting(this, BookletType.PA, true);
    }

    private void setupBookletData(){
        getPT1 = new GetBooklet(this, BookletConfig.URL_BOOKLET_PT1, BookletConfig.FILE_BOOKLET_PT1);
        getPT1.setListener(this);
        getPT2 = new GetBooklet(this, BookletConfig.URL_BOOKLET_PT2, BookletConfig.FILE_BOOKLET_PT2);
        getPT2.setListener(this);
        getPA = new GetBooklet(this, BookletConfig.URL_BOOKLET_PA, BookletConfig.FILE_BOOKLET_PA);
        getPA.setListener(this);
        getVersion = new GetBooklet(this, BookletConfig.URL_VERSION, BookletConfig.FILE_VERSION);
        getVersion.setListener(this);
    }

    /*private boolean isAllFileDownloaded(){
        File filePT1 = new File(getFilesDir().getAbsolutePath(), BookletConfig.FILE_BOOKLET_PT1);
        File filePT2 = new File(getFilesDir().getAbsolutePath(), BookletConfig.FILE_BOOKLET_PT2);
        File filePA = new File(getFilesDir().getAbsolutePath(), BookletConfig.FILE_BOOKLET_PA);
        File fileVersion = new File(getFilesDir().getAbsolutePath(), BookletConfig.FILE_VERSION);

        if(!filePT1.exists() || !filePT2.exists() || !filePA.exists() || !fileVersion.exists())
            return false;
        return true;
    }*/

    private void startDownload(){
        downloadCount = 0;
        getPT1.execute();
        getPT2.execute();
        getPA.execute();
        getVersion.execute();
    }

    @Override
    public void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                downloadCount++;
                if(downloadCount == 4){
                    Preference.markHasFirstRun(getApplicationContext());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onError(Throwable t) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.error) {
            tvError.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            startDownload();
        }
    }
}
