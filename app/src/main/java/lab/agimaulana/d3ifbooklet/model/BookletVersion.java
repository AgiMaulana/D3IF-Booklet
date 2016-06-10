package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by root on 5/29/16.
 */

@Root(name = "booklet")
public class BookletVersion implements Serializable{
    @Element(name = "type")
    private String type;
    @Element(name = "version-number")
    private String version;

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }
}
