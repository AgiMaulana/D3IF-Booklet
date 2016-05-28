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

/**
 * Created by root on 5/28/16.
 */
public class SearchSettingDialog{

    private RecyclerView recyclerView;
    private RecyclerCheckbox adapter;
    private Context context;
    private AlertDialog alertDialog;

    public SearchSettingDialog(Context context) {
        this.context = context;
        setupDialog();
    }

    private void setupDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(createView())
                .setNegativeButton(R.string.batal, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton(R.string.cari, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog = builder.create();
    }

    private View createView(){
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_search_option, null);
        adapter = new RecyclerCheckbox();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        setupContent();
        return view;
    }

    private void setupContent(){
        String[] options = new String[]{
                getContext().getString(R.string.proyek_tingkat_1),
                getContext().getString(R.string.proyek_tingkat_2),
                getContext().getString(R.string.proyek_akhir)
        };

        for (String s : options)
            adapter.addItem(s);
        adapter.notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void show(){
        alertDialog.show();
    }
}
