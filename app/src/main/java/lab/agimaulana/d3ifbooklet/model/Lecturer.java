package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
@Root(name = "dosen")
public class Lecturer {
    @Element(name = "nip")
    private String NIP;

    @Element(name = "nama")
    private String name;

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
