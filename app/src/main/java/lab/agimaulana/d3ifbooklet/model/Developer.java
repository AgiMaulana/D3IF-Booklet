package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
@Root(name = "developer")
public class Developer {

    @ElementList(inline = true)
    private List<Student> developers = new ArrayList<>();

    public List<Student> getDevelopers() {
        return developers;
    }
}
