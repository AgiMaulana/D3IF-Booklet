package lab.agimaulana.d3ifbooklet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.adapter.ProjectSliderAdapter;
import lab.agimaulana.d3ifbooklet.animation.FlipPageTransformer;
import lab.agimaulana.d3ifbooklet.fragments.ProjectSliderContentFragment;
import lab.agimaulana.d3ifbooklet.model.ProjectList;

/**
 * Created by Agi Maulana on 4/19/2016.
 */
public class ProjectSliderActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ProjectSliderAdapter sliderAdapter;
    private int position, total, currentPage, previousPage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        setupSlider();
    }

    private void setupSlider(){
        position = getIntent().getIntExtra("position", -1);
        total = ProjectList.projects.size();
        if(position == -1){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        sliderAdapter = new ProjectSliderAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);
        viewPager.setPageTransformer(false, new FlipPageTransformer());
        viewPager.setAdapter(sliderAdapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
