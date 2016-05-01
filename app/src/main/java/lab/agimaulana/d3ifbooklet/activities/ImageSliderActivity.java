package lab.agimaulana.d3ifbooklet.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.animation.FlipPageTransformer;
import lab.agimaulana.d3ifbooklet.fragments.ImageSliderContentFragment;

/**
 * Created by Agi Maulana on 4/15/2016.
 */
public class ImageSliderActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        ArrayList<String> URLs = getIntent().getStringArrayListExtra("images");
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String url : URLs){
            Bundle bundle = new Bundle();
            bundle.putString("image", url);
            ImageSliderContentFragment fragment = new ImageSliderContentFragment();
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        SliderAdapter sliderAdapter = new SliderAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(sliderAdapter);

        int positionRequest = getIntent().getIntExtra("position", 0);
        viewPager.setCurrentItem(positionRequest);
    }

    private void toggleToolbar(){
        if(getSupportActionBar().isShowing())
            getSupportActionBar().hide();
        else
            getSupportActionBar().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_slider, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_close)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private class SliderAdapter extends FragmentPagerAdapter{

        ArrayList<Fragment> fragments = new ArrayList<>();
        public void addFragment(Fragment fragment){
            fragments.add(fragment);
        }

        public SliderAdapter(FragmentManager fm) {
            super(fm);
        }

        public SliderAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
