package lab.agimaulana.d3ifbooklet.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import lab.agimaulana.d3ifbooklet.API.APIClient;
import lab.agimaulana.d3ifbooklet.API.ServiceAdapter;
import lab.agimaulana.d3ifbooklet.helper.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class GetBooklet {
    private APIClient apiClient;
    private FetchListener listener;

    public  GetBooklet(FetchListener fetchListener){
        this.apiClient = ServiceAdapter.createService(APIClient.class);
        this.listener = fetchListener;
    }

    public void execute(){
        Call<Booklet> booklet = apiClient.getProjects();
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
        });
    }

    public interface FetchListener{
        void onResponse(Response<Booklet> response);
        void onFailure(Throwable t);
    }
}
