package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/29/16.
 */
@Root(name = "version")
public class Version implements Serializable {
    @ElementList(inline = true)
    private List<BookletVersion> bookletVersions = new ArrayList<>();

    public List<BookletVersion> getBookletVersions() {
        return bookletVersions;
    }
}
