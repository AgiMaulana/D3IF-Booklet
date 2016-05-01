package lab.agimaulana.d3ifbooklet.adapter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.ProjectList;
import lab.agimaulana.d3ifbooklet.model.Screenshot;
import lab.agimaulana.d3ifbooklet.model.Screenshots;
import lab.agimaulana.d3ifbooklet.model.viewholder.ScreenshotViewHolder;

/**
 * Created by Agi Maulana on 4/14/2016.
 */
public class RVScreenshotAdapter extends RecyclerView.Adapter<ScreenshotViewHolder> {

    private Project project;
    private ArrayList<Screenshot> screenshots = new ArrayList<>();
    private LoadListener loadListener = null;
    private OnClickListener onClickListener = null;

    public RVScreenshotAdapter(Project project){
        this.project = project;
        screenshots = (ArrayList<Screenshot>) project.getScreenshots().getScreenshots();
    }

    public void setLoadListener(LoadListener loadListener){
        this.loadListener = loadListener;
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public ScreenshotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_screenshot, parent, false);
        return new ScreenshotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ScreenshotViewHolder holder, int position) {
        String url;
        if(position == 0){
            url = "http://192.168.43.185/" + project.getPoster();
        }else{
            Screenshot screenshot = screenshots.get((position-1));
            url = "http://192.168.43.185/" + screenshot.getURL();
        }
        Picasso.with(holder.itemView.getContext()).load(Uri.parse(url)).into(holder.imgScreenshot, new Callback() {
            @Override
            public void onSuccess() {
                if(loadListener != null)
                    loadListener.onSuccess(holder.getLayoutPosition());
            }

            @Override
            public void onError() {
                if(loadListener != null)
                    loadListener.onFailure(holder.getLayoutPosition());

            }
        });
        Log.d("Picasso - Screenshot", url);
        holder.viewClickLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null)
                    onClickListener.onClick(v, holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return screenshots.size() + 1;
    }

    public interface LoadListener{
        void onSuccess(int currentPosition);
        void onFailure(int currentPosition);
    }

    public interface OnClickListener{
        void onClick(View v, int position);
    }

}
