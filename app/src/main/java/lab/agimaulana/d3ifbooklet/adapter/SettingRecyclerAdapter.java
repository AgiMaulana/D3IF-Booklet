package lab.agimaulana.d3ifbooklet.adapter;

import android.content.Context;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import lab.agimaulana.d3ifbooklet.R;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class SettingRecyclerAdapter extends RecyclerView.Adapter<SettingRecyclerAdapter.ItemHolder>{

    private Menu menu;
    private OnOptionsSelectedListener selectedListener;

    public SettingRecyclerAdapter(Context context) {
        if(menu == null) {
            menu = new MenuBuilder(context);
            MenuInflater menuInflater = new MenuInflater(context);
            menuInflater.inflate(R.menu.menu_setting, menu);
        }
    }

    public void setOnOptionsSelectedListener(OnOptionsSelectedListener onOptionsSelectedListener){
        this.selectedListener = onOptionsSelectedListener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_text, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final MenuItem item = menu.getItem(position);
        holder.image.setImageDrawable(item.getIcon());
        holder.text.setText(item.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedListener != null)
                    selectedListener.onOptionsSelected(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;
        public ItemHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageview);
            text = (TextView) itemView.findViewById(R.id.text1);
        }
    }

    public interface OnOptionsSelectedListener{
        void onOptionsSelected(MenuItem item);
    }
}
