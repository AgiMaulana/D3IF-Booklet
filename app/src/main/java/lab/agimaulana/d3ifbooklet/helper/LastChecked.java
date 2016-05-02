package lab.agimaulana.d3ifbooklet.helper;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class LastChecked extends RealmObject{
    @PrimaryKey
    private int id = 1;

    private String lastCheckedDate;

    public LastChecked() {
    }

    public String getLastCheckedDate() {
        return lastCheckedDate;
    }

    public void setLastCheckedDate(String lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }
}
