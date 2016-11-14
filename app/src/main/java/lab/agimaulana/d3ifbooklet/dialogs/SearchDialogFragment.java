package lab.agimaulana.d3ifbooklet.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.adapter.RecyclerProjectList;
import lab.agimaulana.d3ifbooklet.adapter.RecyclerSearchProjectAdapter;
import lab.agimaulana.d3ifbooklet.config.BookletConfig;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.util.Utils;

/**
 * Created by root on 5/27/16.
 */
public class SearchDialogFragment extends AppCompatDialogFragment implements View.OnClickListener, TextView.OnEditorActionListener, View.OnTouchListener, SearchSettingDialog.OnClickListener {

    private EditText etSearch;
    private RelativeLayout btnBack, btnClear, btnFilter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private AppCompatTextView tvNotFound;

    private SearchSettingDialog searchSettingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_NoActionBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null){
            getDialog().getWindow().setWindowAnimations(R.style.dialog_fade);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        searchSettingDialog = new SearchSettingDialog(getContext());
        searchSettingDialog.setOnClickListener(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            showInputMethod(etSearch);
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        super.onCancel(dialog);
    }

    private void initWidget(){
        etSearch = (EditText) getView().findViewById(R.id.edittext_search);
        etSearch.requestFocus();
        etSearch.setOnTouchListener(this);
        etSearch.setOnEditorActionListener(this);
        //showInputMethod(etSearch);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        btnBack = (RelativeLayout) getView().findViewById(R.id.button_back);
        btnBack.setOnClickListener(this);

        btnClear = (RelativeLayout) getView().findViewById(R.id.button_clear);
        btnClear.setOnClickListener(this);
        btnFilter = (RelativeLayout) getView().findViewById(R.id.button_filter);
        btnFilter.setOnClickListener(this);

        progressBar = (ProgressBar) getView().findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvNotFound = (AppCompatTextView) getView().findViewById(R.id.textview_not_found);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                    btnClear.setVisibility(View.VISIBLE);
                else
                    btnClear.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showInputMethod(final View view){
        view.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager =  (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),     InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_back:
                //showInputMethod(etSearch);
                getDialog().cancel();
                break;
            case R.id.button_clear:
                etSearch.setText("");
                etSearch.requestFocus();
                recyclerView.setVisibility(View.GONE);
                tvNotFound.setVisibility(View.GONE);

                if(btnFilter.getVisibility() == View.VISIBLE)
                    showInputMethod(etSearch);

                btnFilter.setVisibility(View.GONE);
                break;
            case R.id.button_filter:
                searchSettingDialog.show();
                break;
            default:break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(v.getId() == R.id.edittext_search && actionId == EditorInfo.IME_ACTION_SEARCH) {
            showInputMethod(v);
            performSearch(v.getText().toString());
        }
        return false;
    }

    private void performSearch(String keyword) {
        if(keyword.length() == 0)
            return;
        etSearch.clearFocus();
        btnFilter.setVisibility(View.VISIBLE);

        tvNotFound.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        try {
            new Utils.SearchProject(getActivity(), keyword, new Utils.SearchProject.OnGetResult() {
                @Override
                public void onGetResult(ArrayList<Project> projects) {
                    progressBar.setVisibility(View.GONE);
                    if(projects.size() == 0) {
                        tvNotFound.setVisibility(View.VISIBLE);
                        if(recyclerView.getAdapter() != null){
                            RecyclerSearchProjectAdapter adapter = (RecyclerSearchProjectAdapter) recyclerView.getAdapter();
                            adapter.clear();
                        }
                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        showResult(projects);
                    }
                }
            }).execute(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResult(ArrayList<Project> projects){
        RecyclerSearchProjectAdapter adapter = new RecyclerSearchProjectAdapter(projects);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.edittext_search){
            btnFilter.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.GONE);
        }
        return false;
    }

    @Override
    public void onPositiveClicked() {
        performSearch(etSearch.getText().toString());
    }

    @Override
    public void onNegativeClicked() {

    }
}