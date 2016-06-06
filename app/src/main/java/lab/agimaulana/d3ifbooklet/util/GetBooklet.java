package lab.agimaulana.d3ifbooklet.util;

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
    private String fileName;

    public  GetBooklet(Context context, String url, String fileName){
        //this.apiClient = ServiceAdapter.createService(APIClient.class);
        downloadManager = new DownloadManager(url);
        this.context = context;
        this.fileName = fileName;

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

    public void setListener(FetchListener listener) {
        this.listener = listener;
    }

    public void execute(){
        downloadManager.execute(new DownloadManager.DownloadCallback() {
            @Override
            public void onFailuer(IOException e) {
                Log.e("GetBooklet", e.getMessage());

                if(listener != null)
                    listener.onError(e);
            }

            @Override
            public void onResponse(okhttp3.Response response) {
                if(response.isSuccessful()) {
                    try {
                        Utils.saveToInternalStorage(context, fileName, response.body().byteStream());
                        if (listener != null)
                            listener.onSuccess();
                        Log.d("ByteStream Size", String.valueOf(response.body().byteStream().available()));
                    }catch (IOException e){
                        if(listener != null)
                            listener.onError(e);
                    }
                }else {
                    if(listener != null)
                        listener.onError(new IOException());
                }
            }
        });
    }

    public interface FetchListener{
        void onSuccess();
        void onError(Throwable t);
    }
}
