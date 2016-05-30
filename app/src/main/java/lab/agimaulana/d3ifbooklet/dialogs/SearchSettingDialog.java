package lab.agimaulana.d3ifbooklet.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.adapter.RecyclerCheckbox;
import lab.agimaulana.d3ifbooklet.config.BookletType;
import lab.agimaulana.d3ifbooklet.config.Preference;

/**
 * Created by root on 5/28/16.
 */
public class SearchSettingDialog implements RecyclerCheckbox.OnCheckedChangeListener {

    private RecyclerView recyclerView;
    private RecyclerCheckbox adapter;
    private Context context;
    private AlertDialog alertDialog;
    private OnClickListener onClickListener;

    public SearchSettingDialog(Context context) {
        this.context = context;
        setupDialog();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void setupDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(createView())
                .setNegativeButton(R.string.batal, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(onClickListener != null)
                            onClickListener.onNegativeClicked();
                    }
                }).setPositiveButton(R.string.cari, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (RecyclerCheckbox.Item item : adapter.getItems())
                            Preference.setSearchSetting(getContext(), item.getText(), item.isChecked());

                        if(onClickListener != null)
                            onClickListener.onPositiveClicked();
                    }
                });
        alertDialog = builder.create();
    }

    private View createView(){
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_search_option, null);
        adapter = new RecyclerCheckbox();
        adapter.setOnCheckedChangeListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        setupContent();
        return view;
    }

    private void setupContent(){
        adapter.addItem(BookletType.PT1, Preference.getSearchSetting(getContext(), BookletType.PT1));
        adapter.addItem(BookletType.PT2, Preference.getSearchSetting(getContext(), BookletType.PT2));
        adapter.addItem(BookletType.PA, Preference.getSearchSetting(getContext(), BookletType.PA));
        adapter.notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void show(){
        alertDialog.show();
    }

    @Override
    public void onCheckedChanged(int position, boolean isChecked) {
//        String[] bookletType = new String[]{BookletType.PT1, BookletType.PT2, BookletType.PA};
//        Preference.setSearchSetting(getContext(), bookletType[position], isChecked);
    }

    public interface OnClickListener{
        void onPositiveClicked();
        void onNegativeClicked();
    }
}
