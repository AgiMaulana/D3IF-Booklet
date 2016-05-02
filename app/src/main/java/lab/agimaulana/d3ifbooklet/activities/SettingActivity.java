package lab.agimaulana.d3ifbooklet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.adapter.SettingRecyclerAdapter;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class SettingActivity extends AppCompatActivity implements SettingRecyclerAdapter.OnOptionsSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SettingRecyclerAdapter adapter = new SettingRecyclerAdapter(this);
        adapter.setOnOptionsSelectedListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onOptionsSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_update_booklet:
                startActivity(new Intent(this, UpdateBookletActivity.class));
                break;
            default:break;
        }
    }
}
