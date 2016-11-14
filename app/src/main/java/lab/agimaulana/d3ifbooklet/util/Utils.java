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
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.config.BookletConfig;
import lab.agimaulana.d3ifbooklet.config.BookletType;
import lab.agimaulana.d3ifbooklet.config.Preference;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.Version;

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

    public static File getImageFileFromInternal(Context context, String filename){
        File dir = new File(context.getFilesDir()+"/images");
        File file = new File(dir.getAbsolutePath(), filename);
        return file;
    }

    public static void saveToInternalStorage(final Context context, final String fileName, final InputStream inputStream){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = context.getFilesDir();
                File file = new File(dir.getAbsolutePath(), fileName);
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

    public static void objectToXML(Context context, String fileName, Object o) throws Exception {
        File file = new File(context.getFilesDir().getAbsolutePath(), fileName);
        if(!file.exists())
            file.createNewFile();

        Serializer serializer = new Persister();
        serializer.write(o, file);
    }

    public static Version BookletVersion(Context context) throws Exception{
        File file = new File(context.getFilesDir().getAbsolutePath(), BookletConfig.FILE_VERSION);
        if(!file.exists())
            throw new Exception(BookletConfig.FILE_VERSION + "not found");
        Serializer serializer = new Persister();
        return serializer.read(Version.class, file);
    }

    public static class BookletSerializer extends AsyncTask<Booklet, Booklet, Booklet>{
        private Context context;
        private String bookletName;
        private Callback callback;

        public BookletSerializer(Context context, String bookletName) {
            this.context = context;
            this.bookletName = bookletName;
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }

        @Override
        protected Booklet doInBackground(Booklet... params) {
            Booklet booklet;
            try {
                booklet = Booklet(context, bookletName);
            } catch (Exception e) {
                e.printStackTrace();
                booklet = null;
                if(callback != null)
                    callback.onFailed(e.getMessage());
            }
            return booklet;
        }

        @Override
        protected void onPostExecute(Booklet booklet) {
            if(callback != null)
                if(booklet != null)
                    callback.onSerialized(booklet);
                else
                    callback.onFailed("Booklet null");
        }

        public interface Callback{
            void onSerialized(Booklet booklet);
            void onFailed(String message);
        }
    }

    public static Booklet Booklet(Context context, String bookletName) throws Exception {
        File file = new File(context.getFilesDir().getAbsolutePath() , bookletName);
        if(!file.exists())
            throw new Exception(bookletName + " not found");
        Serializer serializer = new Persister();
        try {
            return serializer.read(Booklet.class, file);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public static class SearchProject extends AsyncTask{
        private Context context;
        String keyword;
        final ArrayList<Booklet> booklets;
        final ArrayList<Project> result;
        private OnGetResult onGetResult;

        public SearchProject(Context context, String keyword, OnGetResult onGetResult) throws Exception {
            this.context = context;
            this.keyword = keyword.toLowerCase();
            booklets = new ArrayList<>();
            result = new ArrayList<>();
            this.onGetResult = onGetResult;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                if(Preference.getSearchSetting(context, BookletType.PT1)){
                    if(Cache.isCached(context, BookletConfig.FILE_BOOKLET_PT1))
                        booklets.add((Booklet) Cache.read(context, BookletConfig.FILE_BOOKLET_PT1));
                    else
                        booklets.add(Booklet(context, BookletConfig.FILE_BOOKLET_PT1));
                }
                if(Preference.getSearchSetting(context, BookletType.PT2)){
                    if(Cache.isCached(context, BookletConfig.FILE_BOOKLET_PT2))
                        booklets.add((Booklet) Cache.read(context, BookletConfig.FILE_BOOKLET_PT2));
                    else
                        booklets.add(Booklet(context, BookletConfig.FILE_BOOKLET_PT2));
                }
                if(Preference.getSearchSetting(context, BookletType.PA)){
                    if(Cache.isCached(context, BookletConfig.FILE_BOOKLET_PA))
                        booklets.add((Booklet) Cache.read(context, BookletConfig.FILE_BOOKLET_PA));
                    else
                        booklets.add(Booklet(context, BookletConfig.FILE_BOOKLET_PA));
                }

                for (Booklet booklet : booklets){
                    for (int i = 0; i < booklet.getProjects().size(); i++) {
                        Project project = booklet.getProjects().get(i);
                        if(project.getTitle().toLowerCase().contains(keyword))
                            result.add(project);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
