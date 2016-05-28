package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
@Element(name = "pembimbing")
public class Preceptors implements Serializable {

    @ElementList(inline = true)
    private List<Lecturer> preceptors = new ArrayList<>();

    public List<Lecturer> getPreceptors() {
        return preceptors;
    }
}
