package lab.agimaulana.d3ifbooklet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.adapter.RecyclerVersionSettingAdapter;
import lab.agimaulana.d3ifbooklet.model.BookletVersion;
import lab.agimaulana.d3ifbooklet.model.Version;
import lab.agimaulana.d3ifbooklet.util.Utils;

/**
 * Created by root on 5/30/16.
 */
public class SettingActivity extends AppCompatActivity implements RecyclerVersionSettingAdapter.OnOptionsSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_version);

        try {
            Version version = Utils.BookletVersion(this);
            ArrayList<BookletVersion> bookletVersions = (ArrayList<BookletVersion>) version.getBookletVersions();
            RecyclerVersionSettingAdapter adapter = new RecyclerVersionSettingAdapter(bookletVersions);
            adapter.setOnOptionsSelectedListener(this);

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            ((LinearLayout) findViewById(R.id.button_check_schema)).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, UpdateVersionActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, SchemaValidationActivity.class));
    }
}
