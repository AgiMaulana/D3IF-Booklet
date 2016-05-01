package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
@Element(name = "screenshots")
public class Screenshots {

    @ElementList(inline = true)
    private List<Screenshot> screenshots = new ArrayList<>();

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }
}
