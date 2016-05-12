package lab.agimaulana.d3ifbooklet.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Debug;
import android.os.ResultReceiver;
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
public class ImageDownloaderIntentService extends IntentService{
    private ResultReceiver receiver;
    private ArrayList<ImageProperty> images;
    private ArrayList<Project> projects;
    private int downloaded = 0;

    public ImageDownloaderIntentService() {
        super("ImageDownloaderIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, START_STICKY, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        receiver = intent.getParcelableExtra("receiver");
        projects = (ArrayList<Project>) new Helper(this).getBooklet().getProjects();
        images = new ArrayList<>();
        for (Project project : projects) {
         //   initDownloadList(p);
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
            Log.d("ImageDownloaded", "Item size : " + images.size());
        }

//        if(receiver != null)
//            deliverResult(1, downloaded, images.size());

        for (final ImageProperty image : images){
            DownloadManager downloadManager = new DownloadManager(image.url);
            downloadManager.setDownloadListener(new DownloadManager.DownloadListener() {
                @Override
                public synchronized void onDownloaded(InputStream inputStream) {
                    saveToStorage(image.name, inputStream);
                }

                @Override
                public synchronized void onDownloadFailure(IOException e) {
                    Log.i("ImageDownloader", "Download failed. Error : " + e.getMessage());
                }
            });
            downloadManager.execute();
        }
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

    private synchronized void saveToStorage(String filename, InputStream inputStream){
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        try {
            File dir = new File(getFilesDir()+"/images");
            if(!dir.exists())
                dir.mkdir();
            File file = new File(dir.getAbsolutePath(), filename);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            Log.i("ImageDownloader",filename);
            if(receiver != null)
                deliverResult(1, downloaded++, images.size());

        } catch (IOException e) {
            Log.i("ImageDownloader", "Download failed : "+filename + ". Cause : " + e.getMessage());
        }
    }

    private void deliverResult(int resultCode, int downloaded, int max){
        Bundle bundle = new Bundle();
        bundle.putInt("downloaded", downloaded);
        bundle.putInt("max", max);
        receiver.send(resultCode, bundle);
    }

    private class ImageProperty{
        String name;
        String url;

        public ImageProperty(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }
}
