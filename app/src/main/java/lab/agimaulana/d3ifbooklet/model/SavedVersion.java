package lab.agimaulana.d3ifbooklet.model;

import io.realm.RealmObject;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class SavedVersion extends RealmObject{
    private double currentVersion;
    private String downloadDate;
    private String lastCheckedDate;

    public double getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(double currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(String downloadDate) {
        this.downloadDate = downloadDate;
    }

    public String getLastCheckedDate() {
        return lastCheckedDate;
    }

    public void setLastCheckedDate(String lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
