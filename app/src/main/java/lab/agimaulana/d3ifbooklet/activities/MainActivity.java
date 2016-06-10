package lab.agimaulana.d3ifbooklet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.animation.ProjectListPagerAdapter;
import lab.agimaulana.d3ifbooklet.config.BookletConfig;
import lab.agimaulana.d3ifbooklet.dialogs.SearchDialogFragment;
import lab.agimaulana.d3ifbooklet.fragments.ProjectListFragment;
import lab.agimaulana.d3ifbooklet.util.Cache;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProjectListPagerAdapter pagerAdapter;
    private SearchDialogFragment searchDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        Cache.clearCache(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new ProjectListPagerAdapter(getSupportFragmentManager());
        setupViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        searchDialogFragment = new SearchDialogFragment();
    }

    private ProjectListFragment createProjectFragment(String bookletType){
        Bundle bundle = new Bundle();
        bundle.putString("bookletType", bookletType);
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void setupViewPager(){
        pagerAdapter.addFragment(getString(R.string.pt1), createProjectFragment(BookletConfig.FILE_BOOKLET_PT1));
        pagerAdapter.addFragment(getString(R.string.pt2), createProjectFragment(BookletConfig.FILE_BOOKLET_PT2));
        pagerAdapter.addFragment(getString(R.string.pa), createProjectFragment(BookletConfig.FILE_BOOKLET_PA));
        viewPager.setAdapter(pagerAdapter);

        final String[] titles = new String[]{
                getString(R.string.proyek_tingkat_1),
                getString(R.string.proyek_tingkat_2),
                getString(R.string.proyek_akhir)
        };
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(titles[0]);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ((ProjectListFragment)pagerAdapter.getItem(viewPager.getCurrentItem())).hideFab();
            }

            @Override
            public void onPageSelected(int position) {
                if(getSupportActionBar() != null)
                    getSupportActionBar().setTitle(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        switch (id){
            case R.id.action_search:
                new SearchDialogFragment().show(getSupportFragmentManager(), getString(R.string.cari_project));
                //searchDialogFragment.show(getSupportFragmentManager(), getString(R.string.cari_project));
                break;
            case R.id.action_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            default:break;
        }

        return true;
    }
}
