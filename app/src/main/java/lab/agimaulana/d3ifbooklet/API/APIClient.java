package lab.agimaulana.d3ifbooklet.API;

import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.checkversion.Version;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public interface APIClient {
    @GET("booklet.xml")
    Call<Booklet> getProjects();
    @GET("version.xml")
    Call<Version> getVersion();
}
