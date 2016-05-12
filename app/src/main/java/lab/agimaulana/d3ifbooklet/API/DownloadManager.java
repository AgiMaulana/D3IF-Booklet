package lab.agimaulana.d3ifbooklet.API;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by root on 5/12/16.
 */
public class DownloadManager {
    private String url;
    private DownloadListener listener;

    public DownloadManager(String url) {
        this.url = url;
    }

    public void execute(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(listener != null)
                    listener.onDownloadFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(listener != null && response.isSuccessful())
                    listener.onDownloaded(response.body().byteStream());
            }
        });
    }

    public void setDownloadListener(DownloadListener downloadListener){
        this.listener = downloadListener;
    }

    public interface DownloadListener{
        void onDownloaded(InputStream inputStream);
        void onDownloadFailure(IOException e);
    }
}