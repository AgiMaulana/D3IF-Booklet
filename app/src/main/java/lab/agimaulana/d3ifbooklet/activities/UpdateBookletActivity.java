package lab.agimaulana.d3ifbooklet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import lab.agimaulana.d3ifbooklet.API.APIClient;
import lab.agimaulana.d3ifbooklet.API.ServiceAdapter;
import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.helper.Helper;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.checkversion.Version;
import lab.agimaulana.d3ifbooklet.service.ImageDownloaderIntentService;
import lab.agimaulana.d3ifbooklet.service.ImageDownloaderTask;
import lab.agimaulana.d3ifbooklet.service.ServiceResultReceiver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class UpdateBookletActivity extends AppCompatActivity implements View.OnClickListener, ImageDownloaderTask.ImageDownloadListener {

    private Helper helper;
    private TextView tvLastChecked;
    private TextView tvCurrentVersion;
    private TextView tvAvailableVersionTitle;
    private TextView tvAvailableVersion;
    private Button btnCheck;
    private ImageButton btnUpdate;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    private String checkedVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        helper = new Helper(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        setupWidget();
    }

    private void setupWidget(){
        tvLastChecked = (TextView) findViewById(R.id.textview_last_checked);
        String lastChecked = helper.getLastCheckedDate();
        if(lastChecked == null)
            tvLastChecked.setText(R.string.not_checked);
        else
            tvLastChecked.setText(getString(R.string.last_checked, lastChecked));

        tvCurrentVersion = (TextView) findViewById(R.id.textview_current_version);
        String currentVersion = helper.getCurrentVersion();
        String downloadDate = helper.getLastDownloadDate();
        if(downloadDate == null)
            tvCurrentVersion.setText(R.string.not_checked);
        else
            tvCurrentVersion.setText(getString(R.string.current_version_value, currentVersion, downloadDate));

        tvAvailableVersionTitle = (TextView) findViewById(R.id.textview_available_version_title);
        tvAvailableVersionTitle.setVisibility(View.GONE);
        tvAvailableVersion = (TextView) findViewById(R.id.textview_available_version);
        tvAvailableVersion.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        btnCheck = (Button) findViewById(R.id.button_check);
        btnCheck.setOnClickListener(this);
        btnUpdate = (ImageButton) findViewById(R.id.button_download);
        btnUpdate.setOnClickListener(this);
        btnUpdate.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_check)
            checkVersion();
        if(v.getId() == R.id.button_download)
            updateBooklet();
    }

    private void checkVersion(){
        tvAvailableVersion.setVisibility(View.GONE);
        tvAvailableVersionTitle.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        APIClient apiClient = ServiceAdapter.createService(APIClient.class);
        Call<Version> call = apiClient.getVersion();
        call.enqueue(new Callback<Version>() {
            @Override
            public void onResponse(Call<Version> call, Response<Version> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    showNewVersion(response.body());
                    checkedVersion = response.body().getVersion();
                }
            }

            @Override
            public void onFailure(Call<Version> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(btnCheck, R.string.error, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void showNewVersion(Version version){
        if(helper.getCurrentVersion() != null && helper.getCurrentVersion().equalsIgnoreCase(version.getVersion())){
            Snackbar.make(btnCheck, R.string.error, Snackbar.LENGTH_SHORT).show();
            return;
        }

        helper.setLastCheckedDate();
        tvLastChecked.setText(getString(R.string.last_checked, helper.getLastCheckedDate()));
        tvAvailableVersion.setText(String.valueOf(version.getVersion()));
        tvAvailableVersion.setVisibility(View.VISIBLE);
        tvAvailableVersionTitle.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
    }

    private void updateBooklet(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        final ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask(this);
        imageDownloaderTask.setDownloadedListener(this);

        APIClient apiClient = ServiceAdapter.createService(APIClient.class);
        Call<Booklet> call = apiClient.getProjects();
        call.enqueue(new Callback<Booklet>() {
            @Override
            public void onResponse(Call<Booklet> call, Response<Booklet> response) {
                dialog.cancel();
                if(response.isSuccessful()) {
                    helper.setBooklet(response.body(), checkedVersion);
                    /*Intent service = new Intent(getApplicationContext(), ImageDownloaderIntentService.class);
                    service.putExtra("receiver", receiver);
                    startService(service);*/
                    imageDownloaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    progressDialog.show();
                }else
                    Snackbar.make(btnCheck, R.string.error, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Booklet> call, Throwable t) {
                dialog.cancel();
                Snackbar.make(btnCheck, R.string.error, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    /*@Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == 1 && resultData != null){
            int downloaded = resultData.getInt("downloaded");
            int max = resultData.getInt("max");
            progressDialog.setMax(max);
            progressDialog.setProgress(downloaded);
            progressDialog.setMessage(getString(R.string.menunduh_gambar_progress, downloaded, max));

            if(progressDialog.getProgress() == progressDialog.getMax() && progressDialog.isShowing())
                progressDialog.cancel();
        }else{
            if(progressDialog.isShowing())
                progressDialog.cancel();
        }
    }*/

    @Override
    public void onDownloaded(final int current, final int max) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage(getString(R.string.menunduh_gambar_progress, current, max));
            }
        });
    }

    @Override
    public void onFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
            }
        });
    }
}
