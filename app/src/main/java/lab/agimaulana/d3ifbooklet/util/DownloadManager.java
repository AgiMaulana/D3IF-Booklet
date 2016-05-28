package lab.agimaulana.d3ifbooklet.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by root on 5/14/16.
 */
public class DownloadManager {
    private String url;

    public DownloadManager(String url) {
        this.url = url;
    }

    public void execute(final DownloadCallback downloadCallback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(downloadCallback != null)
                    downloadCallback.onFailuer(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(downloadCallback != null)
                    downloadCallback.onResponse(response);
            }
        });
    }

    public interface DownloadCallback{
        void onFailuer(IOException e);
        void onResponse(Response response);
    }
}
