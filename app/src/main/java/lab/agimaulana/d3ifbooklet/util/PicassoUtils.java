package lab.agimaulana.d3ifbooklet.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by root on 5/13/16.
 */
public class PicassoUtils {
    public static void load(Context context, String path, ImageView imageView){
        Log.d("PicassoUtils", path);
        File file = Utils.getImageFileFromInternal(context, Uri.encode(path));
        if(file.exists()){
            Picasso.with(context).load(file).resize(400, 700).onlyScaleDown().into(imageView);
        }else{
            Picasso.with(context).load(Uri.parse(path)).resize(400, 700).onlyScaleDown().into(imageView);
        }
    }

    public static void loadNoResize(final Context context, final String path, final ImageView imageView, final LoadCallback loadCallback){
        Log.d("PicassoUtils", path);
        File file = Utils.getImageFileFromInternal(context, Uri.encode(path));
        if(file.exists()){
            Picasso.with(context).load(file).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    loadCallback.onSuccess();
                    Log.i("PicassoUtils", "Load from saved image");
                }

                @Override
                public void onError() {
                    loadCallback.onError();
                }
            });
        }else{
            Picasso.with(context).load(Uri.parse(path)).resize(400, 700).onlyScaleDown().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    loadCallback.onSuccess();
                    Utils.saveToInternalImageDir(context, Uri.encode(path), imageView.getDrawable());
                    Log.i("PicassoUtils", "Download new image");
                }

                @Override
                public void onError() {
                    loadCallback.onError();
                }
            });
        }
    }

    public static void load(final Context context, final String path, final ImageView imageView, final LoadCallback loadCallback){
        Log.d("PicassoUtils", path);
        File file = Utils.getImageFileFromInternal(context, Uri.encode(path));
        if(file.exists()){
            Picasso.with(context).load(file).resize(400, 700).onlyScaleDown().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    loadCallback.onSuccess();
                    Log.i("PicassoUtils", "Load from saved image");
                }

                @Override
                public void onError() {
                    loadCallback.onError();
                }
            });
        }else{
            Picasso.with(context).load(Uri.parse(path)).resize(400, 700).onlyScaleDown().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    loadCallback.onSuccess();
                    Utils.saveToInternalImageDir(context, Uri.encode(path), imageView.getDrawable());
                    Log.i("PicassoUtils", "Download new image");
                }

                @Override
                public void onError() {
                    loadCallback.onError();
                }
            });
        }
    }

    public interface LoadCallback{
        void onSuccess();
        void onError();
    }

    public interface PicassoTargetCallback{
        void onSuccess();
        void onError();
    }
}
