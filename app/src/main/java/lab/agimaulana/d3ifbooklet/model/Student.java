package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
@Root(name = "mahasiswa")
public class Student implements Serializable {

    @Element(name = "nama")
    private String name;

    @Element(name = "nim")
    private String NIM;

    public String getName() {
        return name;
    }

    public String getNIM() {
        return NIM;
    }
}
