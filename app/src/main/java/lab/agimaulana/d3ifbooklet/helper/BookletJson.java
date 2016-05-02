package lab.agimaulana.d3ifbooklet.helper;

import com.google.gson.Gson;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lab.agimaulana.d3ifbooklet.model.Booklet;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class BookletJson extends RealmObject {
    @PrimaryKey
    private int id = 1;

    private String json;

    public BookletJson() {
    }

    public BookletJson(Booklet booklet) {
        this.json = new Gson().toJson(booklet);
    }

    @Override
    public String toString() {
        return json;
    }
}
