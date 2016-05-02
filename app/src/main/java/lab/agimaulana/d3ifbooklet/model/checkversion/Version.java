package lab.agimaulana.d3ifbooklet.model.checkversion;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;

/**
 * Created by Agi Maulana on 5/2/2016.
 */

@Root(name = "booklet")
public class Version{
    @Element(name = "version")
    private String version;

    public String getVersion() {
        return version;
    }
}
