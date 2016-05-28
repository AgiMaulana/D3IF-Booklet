package lab.agimaulana.d3ifbooklet.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import lab.agimaulana.d3ifbooklet.API.APIClient;
import lab.agimaulana.d3ifbooklet.API.ServiceAdapter;
import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.dialogs.SearchDialogFragment;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private APIClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiClient = ServiceAdapter.createService(APIClient.class);
    }

    private void tesApi(){
        Call<Booklet> booklet = apiClient.getProjects();
        booklet.enqueue(new Callback<Booklet>() {
            @Override
            public void onResponse(Call<Booklet> call, Response<Booklet> response) {
                Log.d("Ini responsenya", response.raw().message());
                if(response.body().getProjects().size() > 0) {
                    Toast.makeText(MainActivity.this, "Data BERHASIL dimuat", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data GAGAL dimuat", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<Booklet> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            new SearchDialogFragment().show(getSupportFragmentManager(), getString(R.string.cari_project));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
