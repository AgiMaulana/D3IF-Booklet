package lab.agimaulana.d3ifbooklet.util;

import lab.agimaulana.d3ifbooklet.API.ServiceAdapter;
import lab.agimaulana.d3ifbooklet.model.Project;
import lab.agimaulana.d3ifbooklet.model.Screenshot;

/**
 * Created by root on 5/12/16.
 */
public class ProjectUtils {
    public static String getYoutubeThumbnailName(Project project){
        return Utils.md5("video thumbnail/"+project.getTitle())+"-Video.png";
    }

    public static String getYoutubeThumbnailUrl(Project project){
        return "http://img.youtube.com/vi/"+ project.getVideo() +"/hqdefault.jpg";
    }

    public static String getPosterName(Project project){
        return Utils.md5("poster/"+project.getTitle())+"-Poster.png";
    }

    public static String getPosterUrl(Project project){
        return ServiceAdapter.API_BASE_URL + project.getPoster();
    }

    public static String getScreenshotName(Project project, int position){
        return Utils.md5("screenshot/"+project.getTitle())+"-Screenshot" + position + ".png";
    }

    public static String getScreenshotUrl(Screenshot screenshot){
        return ServiceAdapter.API_BASE_URL + screenshot.getURL();
    }


}