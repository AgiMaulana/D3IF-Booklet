package lab.agimaulana.d3ifbooklet.helper;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class BookletVersion extends RealmObject{
    @PrimaryKey
    private int id = 1;

    private String version = "0";
    private String downloadDate;

    public BookletVersion() {
    }

    public String getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(String downloadDate) {
        this.downloadDate = downloadDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
