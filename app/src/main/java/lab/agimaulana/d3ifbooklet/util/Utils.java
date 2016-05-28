package lab.agimaulana.d3ifbooklet.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.Project;

/**
 * Created by root on 5/13/16.
 */
public class Utils {
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void saveToInternalImageDir(final Context context, final String filename, final Drawable drawable){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = drawableToBitmap(drawable);
                File dir = new File(context.getFilesDir()+"/images");
                File file = new File(dir.getAbsolutePath(), filename);
                try {
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

    public static void saveToExternalImageDir(final Context context, final String filename, final Drawable drawable){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = drawableToBitmap(drawable);
                File dir = new File(context.getFilesDir()+"/images");
                File file = new File(dir.getAbsolutePath(), filename);
                try {
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

    public static File getImageFileFromInternal(Context context, String filename){
        File dir = new File(context.getFilesDir()+"/images");
        File file = new File(dir.getAbsolutePath(), filename);
        return file;
    }

    public static void saveBookletXml(final Context context, final InputStream inputStream){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = context.getFilesDir();
                File file = new File(dir.getAbsolutePath(), "Booklet.xml");
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    final byte[] buffer = new byte[1024];
                    int read;
                    while ((read = inputStream.read(buffer)) != -1)
                        fos.write(buffer, 0, read);
                    fos.flush();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static Booklet Booklet(Context context) throws Exception {
        File file = new File(context.getFilesDir().getAbsolutePath() , "Booklet.xml");
        if(!file.exists())
            throw new Exception("Booklet.xml not found");
        Serializer serializer = new Persister();
        Booklet booklet = serializer.read(Booklet.class, file);
        return booklet;
    }

    public static class SearchProject extends AsyncTask{
        String keyword;
        final Booklet booklet;
        final ArrayList<Project> result;
        private OnGetResult onGetResult;

        public SearchProject(Context context, String keyword, OnGetResult onGetResult) throws Exception {
            this.keyword = keyword.toLowerCase();
            booklet = Booklet(context);
            result = new ArrayList<>();
            this.onGetResult = onGetResult;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            for (int i = 0; i < booklet.getProjects().size(); i++) {
                Project project = booklet.getProjects().get(i);
                if(project.getTitle().toLowerCase().contains(keyword))
                    result.add(project);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(onGetResult != null)
                onGetResult.onGetResult(result);
        }

        public interface OnGetResult{
            void onGetResult(ArrayList<Project> projects);
        }
    }
}