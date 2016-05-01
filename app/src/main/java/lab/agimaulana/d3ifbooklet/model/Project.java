package lab.agimaulana.d3ifbooklet.model;

import org.simpleframework.xml.Element;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
@Element(name = "project")
public class Project {
    @Element(name = "judul")
    private String title;

    @Element(name = "deskripsi")
    private String description;

    @Element(name = "poster")
    private String poster;

    @Element(name = "video")
    private String video;

    @Element(name = "screenshots")
    private Screenshots screenshots = new Screenshots();

    @Element(name = "developer")
    private Developer developers = new Developer();

    @Element(name = "pembimbing")
    private Preceptors preceptors = new Preceptors();

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }

    public String getVideo() {
        return video;
    }

    public Screenshots getScreenshots() {
        return screenshots;
    }

    public Developer getDevelopers() {
        return developers;
    }

    public Preceptors getPreceptors() {
        return preceptors;
    }
}
