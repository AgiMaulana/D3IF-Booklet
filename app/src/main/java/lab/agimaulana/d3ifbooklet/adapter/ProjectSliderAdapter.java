package lab.agimaulana.d3ifbooklet.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.fragments.ProjectSliderContentFragment;

/**
 * Created by Agi Maulana on 4/19/2016.
 */
public class ProjectSliderAdapter extends FragmentPagerAdapter {
    public ProjectSliderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        ProjectSliderContentFragment fragment = new ProjectSliderContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
