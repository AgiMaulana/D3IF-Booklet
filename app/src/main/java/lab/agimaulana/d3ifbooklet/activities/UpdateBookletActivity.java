package lab.agimaulana.d3ifbooklet.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class UpdateBookletActivity extends AppCompatActivity implements View.OnClickListener {

    private Helper helper;
    private TextView tvLastChecked;
    private TextView tvCurrentVersion;
    private TextView tvAvailableVersionTitle;
    private TextView tvAvailableVersion;
    private Button btnCheck;
    private ImageButton btnUpdate;
    private ProgressBar progressBar;

    private String checkedVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        helper = new Helper(this);
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        APIClient apiClient = ServiceAdapter.createService(APIClient.class);
        Call<Booklet> call = apiClient.getProjects();
        call.enqueue(new Callback<Booklet>() {
            @Override
            public void onResponse(Call<Booklet> call, Response<Booklet> response) {
                progressDialog.cancel();
                if(response.isSuccessful())
                    helper.setBooklet(response.body(), checkedVersion);
                else
                    Snackbar.make(btnCheck, R.string.error, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Booklet> call, Throwable t) {
                progressDialog.cancel();
                Snackbar.make(btnCheck, R.string.error, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
