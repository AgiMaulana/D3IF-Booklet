package lab.agimaulana.d3ifbooklet.service;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by root on 5/12/16.
 */
@SuppressLint("ParcelCreator")
public class ServiceResultReceiver extends ResultReceiver {
    private ResultListener listener;
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public ServiceResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if(listener != null)
            listener.onReceiveResult(resultCode, resultData);
    }

    public void setResultListsner(ResultListener resultListsner){
        listener = resultListsner;
    }

    public interface ResultListener{
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
