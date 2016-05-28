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

    public static Target Target(final Context context, final String url){
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File dir = new File(context.getFilesDir()+"/images");
                            File file = new File(dir.getAbsolutePath(), url);
                            if(!dir.exists())
                                dir.mkdir();
                            if(!file.exists())
                                file.createNewFile();

                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        return target;
    }

    public static Target Target(final Context context, final String url, final PicassoTargetCallback listener){
        PicassoTargetCallback targetListener = listener;
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File dir = new File(context.getFilesDir()+"/images");
                            File file = new File(dir.getAbsolutePath(), url);
                            if(!dir.exists())
                                dir.mkdir();
                            if(!file.exists())
                                file.createNewFile();

                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                            fos.close();

                            listener.onSuccess();
                        } catch (IOException e) {
                            listener.onError();
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        return target;
    }

    public static void load(Context context, String path, ImageView imageView){
        File file = Utils.getImageFileFromInternal(context, path);
        if(file != null){
            Picasso.with(context).load(file).into(imageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }else{
            Picasso.with(context).load(Uri.parse(path)).into(imageView);
        }
    }

    public static void load(final Context context, final String path, final ImageView imageView, final LoadCallback loadCallback){
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
            Picasso.with(context).load(Uri.parse(path)).into(imageView, new Callback() {
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
