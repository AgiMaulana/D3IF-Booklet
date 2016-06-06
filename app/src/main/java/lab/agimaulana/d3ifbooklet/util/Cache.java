package lab.agimaulana.d3ifbooklet.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import lab.agimaulana.d3ifbooklet.fragments.ProjectListFragment;
import lab.agimaulana.d3ifbooklet.model.Booklet;

/**
 * Created by root on 6/1/16.
 */
public class Cache {

    public static boolean isCached(Context context, String name){
        File dir = new File(context.getCacheDir()+"/BookletCache");
        File file = new File(dir.getAbsolutePath(), name);
        return file.exists();
    }

    public static void write(final Context context, final String name, final Serializable object) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = new File(context.getCacheDir()+"/BookletCache");
                if(!dir.exists())
                    dir.mkdir();
                File file = new File(dir.getAbsolutePath(), name);
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(object);
                    oos.flush();
                    oos.close();
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static <T> T read(Context context, String name) {
        File dir = new File(context.getCacheDir()+"/BookletCache");
        if(!dir.exists())
            dir.mkdir();
        File file = new File(dir.getAbsolutePath(), name);
        if(!file.exists())
            return null;

        Serializable object = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            object = (Serializable) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (T) object;
    }

    public static void readBookletAsync(Context context, String name, final LoadAsyncCallback callback) {
        File dir = new File(context.getCacheDir()+"/BookletCache");
        if(!dir.exists())
            dir.mkdir();
        File file = new File(dir.getAbsolutePath(), name);
        new LoadAsync(file, new LoadAsyncCallback() {
            @Override
            public void onLoaded(Booklet booklet) {
                callback.onLoaded(booklet);
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static void clearCache(Context context){
        File dir = new File(context.getCacheDir()+"/BookletCache");
        deleteDir(dir);
    }

    private static void deleteDir(File dir){
        if(dir.isDirectory()){
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                File child = new File(dir, children[i]);
                if(child.isDirectory())
                    deleteDir(child);
                else
                    child.delete();
            }
        }
    }

    private static class LoadAsync extends AsyncTask{

        private Serializable serializable;
        private File file;
        private LoadAsyncCallback callback;

        public LoadAsync(File file, LoadAsyncCallback callback) {
            this.file = file;
            this.callback = callback;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            if(!file.exists())
                return null;

            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                serializable = (Serializable) ois.readObject();
                ois.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(callback != null)
                callback.onLoaded((Booklet) serializable);
        }
    }

    public interface LoadAsyncCallback{
        void onLoaded(Booklet booklet);
    }
}
