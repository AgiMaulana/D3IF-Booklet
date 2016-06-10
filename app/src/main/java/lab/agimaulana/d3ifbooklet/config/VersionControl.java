package lab.agimaulana.d3ifbooklet.config;

import android.content.Context;

import lab.agimaulana.d3ifbooklet.model.BookletVersion;
import lab.agimaulana.d3ifbooklet.model.Version;
import lab.agimaulana.d3ifbooklet.util.Utils;

/**
 * Created by root on 5/29/16.
 */
public class VersionControl {
    public static String Version(Context context, String bookletType){
        try {
            Version version = Utils.BookletVersion(context);
            for (BookletVersion v : version.getBookletVersions())
                if (v.getType().equalsIgnoreCase(bookletType))
                    return v.getVersion();
        } catch (Exception e) {
            return "0";
        }

        return "0";
    }
}
