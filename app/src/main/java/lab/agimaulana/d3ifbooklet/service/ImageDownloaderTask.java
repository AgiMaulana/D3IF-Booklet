package lab.agimaulana.d3ifbooklet.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import lab.agimaulana.d3ifbooklet.API.DownloadManager;
import lab.agimaulana.d3ifbooklet.API.ServiceAdapter;
import lab.agimaulana.d3ifbooklet.helper.Helper;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.Screenshot;
import lab.agimaulana.d3ifbooklet.util.ProjectUtils;

/**
 * Created by root on 5/12/16.
 */
public class ImageDownloaderTask extends AsyncTask {
    private ArrayList<ImageProperty> images;
    private ArrayList<Project> projects;
    private int downloaded = 0;
    private Context mCtx;
    private ImageDownloadListener listener;

    public ImageDownloaderTask(Context mCtx) {
        this.mCtx = mCtx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        projects = (ArrayList<Project>) new Helper(mCtx).getBooklet().getProjects();
        images = new ArrayList<>();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        for (Project p : projects)
            initDownloadList(p);
        if(listener != null)
            listener.onDownloaded(downloaded, images.size());

        for (final ImageProperty image : images){
            DownloadManager downloadManager = new DownloadManager(image.url);
            downloadManager.setDownloadListener(new DownloadManager.DownloadListener() {
                @Override
                public void onDownloaded(InputStream inputStream) {
                    saveToStorage(image.name, inputStream);
                }

                @Override
                public void onDownloadFailure(IOException e) {
                    Log.i("ImageDownloader", "Download failed. Error : " + e.getMessage());
                    e.printStackTrace();
                }
            });
            downloadManager.execute();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        if(listener != null)
            listener.onFinish();
    }

    private void initDownloadList(Project project){
        if(images == null) images = new ArrayList<>();
        /*String youtubeThumbnailName = project.getTitle()+"-Video-"+project.getVideo();
        String youtubeThumbnailUrl = "http://img.youtube.com/vi/"+ project.getVideo() +"/hqdefault.jpg";
        images.add(new ImageProperty(ProjectUtils.getYoutubeThumbnailName(project), ProjectUtils.getYoutubeThumbnailUrl(project)));
*/
        String posterName = project.getTitle()+"-Poster-"+project.getPoster();
        String posterUrl = ServiceAdapter.API_BASE_URL + project.getPoster();
        images.add(new ImageProperty(ProjectUtils.getPosterName(project), ProjectUtils.getPosterUrl(project)));
        int i = 1;
        for(Screenshot s : project.getScreenshots().getScreenshots()){
            images.add(new ImageProperty(ProjectUtils.getScreenshotName(project, i), ProjectUtils.getScreenshotUrl(s)));
        }
    }

    private void saveToStorage(String filename, InputStream inputStream){
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        try {
            File dir = new File(mCtx.getFilesDir()+"/images");
            File video = new File(mCtx.getFilesDir()+"/video thumbnail");
            File poster = new File(mCtx.getFilesDir()+"/poster");
            File ss = new File(mCtx.getFilesDir()+"/screenshot");
            if(!dir.exists()) {
                dir.mkdir();
                video.mkdir();
                poster.mkdir();
                ss.mkdir();
            }
            File file = new File(dir.getAbsolutePath(), filename);
            if(!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            if(listener != null)
                listener.onDownloaded(downloaded++, images.size());
            Log.i("ImageDownloader",filename);
        } catch (IOException e) {
            Log.i("ImageDownloader", "Download failed : "+filename + ". Cause : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private class ImageProperty{
        String name;
        String url;

        public ImageProperty(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public void setDownloadedListener(ImageDownloadListener imageDownloadListener) {
        this.listener = imageDownloadListener;
    }

    public interface ImageDownloadListener{
        void onDownloaded(int current, int max);
        void onFinish();
    }
}
