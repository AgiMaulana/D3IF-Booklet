package lab.agimaulana.d3ifbooklet.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;


/**
 * Created by Agi Maulana on 5/3/2016.
 */
public class ExpandableRecyclerAdapter extends RecyclerView.Adapter<ExpandableRecyclerAdapter.Holder>{
    private ArrayList<ParentItem> items = new ArrayList<>();

    public ExpandableRecyclerAdapter(ArrayList<ParentItem> items) {
        this.items = items;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expandable_recyclerview_parent, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        ParentItem item = items.get(position);
        holder.text.setText(item.text);
        holder.recyclerView.setAdapter(new ChildAdapter(item.childItems));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.recyclerView.getVisibility() == View.GONE) {
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.arrow.setImageResource(R.drawable.ic_arrow_drop_up_deep_orange_500_24dp);
                }else {
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.arrow.setImageResource(R.drawable.ic_arrow_drop_down_deep_orange_500_24dp);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView arrow;
        RecyclerView recyclerView;

        public Holder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.textview1);
            arrow = (ImageView) itemView.findViewById(R.id.imageView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview);
        }
    }

    static class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildHolder>{
        ArrayList<ChildItem> items = new ArrayList<>();

        public ChildAdapter(ArrayList<ChildItem> items) {
            this.items = items;
        }

        @Override
        public ChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_expandable_recyclerview_child, parent, false);
            return new ChildHolder(v);
        }

        @Override
        public void onBindViewHolder(ChildHolder holder, int position) {
            ChildItem item = items.get(position);
            holder.text1.setText(item.text1);
            holder.text2.setText(item.text2);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ChildHolder extends RecyclerView.ViewHolder{
            TextView text1;
            TextView text2;
            public ChildHolder(View itemView) {
                super(itemView);
                text1 = (TextView) itemView.findViewById(R.id.textview1);
                text2 = (TextView) itemView.findViewById(R.id.textview2);
            }
        }
    }

    public static class ParentItem{
        private String text;
        private ArrayList<ChildItem> childItems;

        public ParentItem(String text, ArrayList<ChildItem> childItems) {
            this.text = text;
            this.childItems = childItems;
        }
    }

    public static class ChildItem{
        private String text1;
        private String text2;

        public ChildItem(String text1, String text2) {
            this.text1 = text1;
            this.text2 = text2;
        }
    }

}
