package lab.agimaulana.d3ifbooklet.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import lab.agimaulana.d3ifbooklet.R;
import lab.agimaulana.d3ifbooklet.config.BookletConfig;
import lab.agimaulana.d3ifbooklet.config.BookletType;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.util.Utils;

/**
 * Created by root on 6/1/16.
 */
public class SchemaValidationActivity extends AppCompatActivity {

    private TextView textView1, textView2, textView3;
    private ProgressBar progressBar1, progressBar2, progressBar3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schema_validator);
        initWidget();
        validate();
    }

    private void initWidget(){
        textView1 = (TextView) findViewById(R.id.textview_validation_result_1);
        textView2 = (TextView) findViewById(R.id.textview_validation_result_2);
        textView3 = (TextView) findViewById(R.id.textview_validation_result_3);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
    }

    private void validate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.Booklet(getApplicationContext(), BookletConfig.FILE_BOOKLET_PT1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView1.setTextColor(getResources().getColor(R.color.blue_900));
                            textView1.setText(R.string.skema_valid);
                            progressBar1.setVisibility(View.GONE);
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView1.setTextColor(getResources().getColor(R.color.red_500));
                            textView1.setText(getString(R.string.skema_error, e.getMessage()));
                            progressBar1.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.Booklet(getApplicationContext(), BookletConfig.FILE_BOOKLET_PT2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView2.setTextColor(getResources().getColor(R.color.blue_900));
                            textView2.setText(R.string.skema_valid);
                            progressBar2.setVisibility(View.GONE);
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView2.setTextColor(getResources().getColor(R.color.red_500));
                            textView2.setText(getString(R.string.skema_error, e.getMessage()));
                            progressBar2.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Utils.Booklet(getApplicationContext(), BookletConfig.FILE_BOOKLET_PA);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView3.setTextColor(getResources().getColor(R.color.blue_900));
                            textView3.setText(R.string.skema_valid);
                            progressBar3.setVisibility(View.GONE);
                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView3.setTextColor(getResources().getColor(R.color.red_500));
                            textView3.setText(getString(R.string.skema_error, e.getMessage()));
                            progressBar3.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }
}
