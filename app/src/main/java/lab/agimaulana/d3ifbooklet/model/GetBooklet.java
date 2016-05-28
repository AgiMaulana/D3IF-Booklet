package lab.agimaulana.d3ifbooklet.model;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import lab.agimaulana.d3ifbooklet.API.APIClient;
import lab.agimaulana.d3ifbooklet.API.ServiceAdapter;
import lab.agimaulana.d3ifbooklet.util.DownloadManager;
import lab.agimaulana.d3ifbooklet.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class GetBooklet {
    //private APIClient apiClient;
    private DownloadManager downloadManager;
    private FetchListener listener;
    private Context context;
    private AlertDialog messageDialog;

    public  GetBooklet(Context context, FetchListener fetchListener){
        //this.apiClient = ServiceAdapter.createService(APIClient.class);
        downloadManager = new DownloadManager(ServiceAdapter.BOOKLET_XML_URL);
        this.listener = fetchListener;
        this.context = context;

        messageDialog = new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("Tidak dapat mengunduh booklet")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
    }

    public void execute(){
        /*Call<Booklet> booklet = apiClient.getProjects();
        booklet.enqueue(new Callback<Booklet>() {
            @Override
            public void onResponse(Call<Booklet> call, Response<Booklet> response) {
                if(response.isSuccessful())
                    ProjectList.projects = response.body().getProjects();
                if (listener != null)
                    listener.onResponse(response);
            }


            @Override
            public void onFailure(Call<Booklet> call, Throwable t) {
                if(listener != null)
                    listener.onFailure(t);
            }
        });*/
        downloadManager.execute(new DownloadManager.DownloadCallback() {
            @Override
            public void onFailuer(IOException e) {
                showDialog();
                Log.e("GetBooklet", e.getMessage());

                if(listener != null)
                    listener.onError(e);
            }

            @Override
            public void onResponse(okhttp3.Response response) {
                if(response.isSuccessful()) {
                    try {
                        Utils.saveBookletXml(context, response.body().byteStream());
                        if (listener != null)
                            listener.onSuccess();
                        Log.d("ByteStream Size", String.valueOf(response.body().byteStream().available()));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else {
                    showDialog();
                }
            }
        });
    }

    private void showDialog(){
        new Handler(context.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        messageDialog.show();
                    }
                });
    }

    public interface FetchListener{
        void onSuccess();
        void onError(Throwable t);
    }
}
