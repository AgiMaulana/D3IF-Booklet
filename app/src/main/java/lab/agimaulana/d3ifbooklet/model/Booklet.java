package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
@Root(name = "booklet")
public class Booklet {
    @ElementList(inline = true)
    private List<Project> projects = new ArrayList<>();

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
