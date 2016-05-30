package lab.agimaulana.d3ifbooklet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.model.viewholder.ItemCheckBox;

/**
 * Created by root on 5/28/16.
 */
public class RecyclerCheckbox extends RecyclerView.Adapter<ItemCheckBox> {
    private ArrayList<Item> items;
    private OnCheckedChangeListener onCheckedChangeListener;

    public RecyclerCheckbox(){
        items = new ArrayList<>();
    }

    public RecyclerCheckbox(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(String text, boolean checked){
        items.add(new Item(text, checked));
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    @Override
    public ItemCheckBox onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkbox, parent, false);
        return new ItemCheckBox(view);
    }

    @Override
    public void onBindViewHolder(ItemCheckBox holder, final int position) {
        holder.text.setText(items.get(position).text);
        holder.checkBox.setChecked(items.get(position).checked);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCheckedChangeListener != null)
                    onCheckedChangeListener.onCheckedChanged(position, isChecked);
                items.get(position).checked = isChecked;
            }
        });
        OnClick onClick = new OnClick(holder.checkBox);
        holder.itemView.setOnClickListener(onClick);
        holder.text.setOnClickListener(onClick);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class OnClick implements View.OnClickListener{
        CheckBox checkBox;

        public OnClick(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        @Override
        public void onClick(View view) {
            checkBox.performClick();
        }
    }

    public class Item{
        private String text;
        private boolean checked;

        public Item(String text, boolean checked) {
            this.text = text;
            this.checked = checked;
        }

        public String getText() {
            return text;
        }

        public boolean isChecked() {
            return checked;
        }
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged(int position, boolean isChecked);
    }
}
