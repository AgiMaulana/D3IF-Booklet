package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by Agi Maulana on 4/13/2016.
 */

@Root(name = "screenshot")
public class Screenshot {

    @Text
    private String url;

    public String getURL() {
        return url;
    }
}
