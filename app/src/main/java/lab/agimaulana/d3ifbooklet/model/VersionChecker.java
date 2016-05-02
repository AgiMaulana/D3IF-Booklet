package lab.agimaulana.d3ifbooklet.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import lab.agimaulana.d3ifbooklet.API.APIClient;
import lab.agimaulana.d3ifbooklet.API.ServiceAdapter;
import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.helper.Helper;
import lab.agimaulana.d3ifbooklet.model.checkversion.Version;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class VersionChecker {
    private Context context;
    public VersionChecker(Context context){
        this.context = context;
    }

    private Context getContext(){
        return context;
    }

    public void execute(){
        APIClient apiClient = ServiceAdapter.createService(APIClient.class);
        Call<Version> call = apiClient.getVersion();
        call.enqueue(new Callback<Version>() {
            @Override
            public void onResponse(Call<Version> call, Response<Version> response) {
//                if(response.isSuccessful())
//                    showUpdateAlert(response.body().getVersion());
            }

            @Override
            public void onFailure(Call<Version> call, Throwable t) {

            }
        });
    }

    private void showUpdateAlert(double newVersion){
        String message = getContext().getString(R.string.data_update_version, (int) newVersion);
        AlertDialog alert = new AlertDialog.Builder(getContext())
                .setTitle(R.string.update_data)
                .setMessage(message)
                .setNegativeButton(R.string.nanti, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "downloading", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();
        alert.show();
    }
}
