package lab.agimaulana.d3ifbooklet.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import lab.agimaulana.d3ifbooklet.R;

/**
 * Created by Agi Maulana on 4/15/2016.
 */
public class ImageSliderContentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_image_slider, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = getArguments().getString("image");
        ImageView image = (ImageView) getView().findViewById(R.id.imageview);
        Picasso.with(getActivity()).load(Uri.parse(url)).into(image);
    }
}
