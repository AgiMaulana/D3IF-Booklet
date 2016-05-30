package lab.agimaulana.d3ifbooklet.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.adapter.ExpandableRecyclerAdapter;

/**
 * Created by Agi Maulana on 5/3/2016.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_about);
        setupThirdPartyLibrariesList();
    }

    private void setupThirdPartyLibrariesList(){
        ArrayList<ExpandableRecyclerAdapter.ChildItem> childItems = new ArrayList<>();
        childItems.add(new ExpandableRecyclerAdapter
                .ChildItem("Android Material Design Icon Generator", "Konifar - https://github.com/konifat/android-material-design-icon-generator-plugin"));
        childItems.add(new ExpandableRecyclerAdapter
                .ChildItem("Retrofit", "Square - https://github.com/square/retrofit"));
        childItems.add(new ExpandableRecyclerAdapter
                .ChildItem("OkHTTP", "Square - https://github.com/square/okhttp"));
        childItems.add(new ExpandableRecyclerAdapter
                .ChildItem("Picasso", "Square - https://github.com/square/picasso"));
        childItems.add(new ExpandableRecyclerAdapter
                .ChildItem("Simple XML", "Niall Gallagher - http://simple.sourceforge.net/"));
        childItems.add(new ExpandableRecyclerAdapter
                .ChildItem("YouTube Android Player API", "Google - https://developers.google.com/youtube/android/player//"));

        ArrayList<ExpandableRecyclerAdapter.ParentItem> parentItems = new ArrayList<>();
        parentItems.add(new ExpandableRecyclerAdapter.ParentItem("Third Party Libraries", childItems));
        ExpandableRecyclerAdapter adapter = new ExpandableRecyclerAdapter(parentItems);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
