package lab.agimaulana.d3ifbooklet.helper;

import android.content.Context;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import lab.agimaulana.d3ifbooklet.model.Booklet;
import lab.agimaulana.d3ifbooklet.model.Project;

/**
 * Created by Agi Maulana on 5/2/2016.
 */
public class Helper {
    private Realm realm;
    public Helper(Context context){
        realm = Realm.getInstance(new RealmConfiguration.Builder(context).build());
    }

    public void setBooklet(Booklet booklet, String version){
        BookletJson json = new BookletJson(booklet);
        BookletVersion versionJson = new BookletVersion();
        versionJson.setVersion(version);
        versionJson.setDownloadDate(getCurrentDate());

        realm.beginTransaction();
        realm.clear(BookletJson.class);
        realm.clear(BookletVersion.class);
        realm.copyToRealmOrUpdate(json);
        realm.copyToRealmOrUpdate(versionJson);
        realm.commitTransaction();
    }

    public Booklet getBooklet(){
        BookletJson json = realm.where(BookletJson.class).findFirst();
        if(json == null)
            return null;
        Booklet booklet = new Gson().fromJson(json.toString(), Booklet.class);
        return booklet;
    }

    public ArrayList<Project> searchProject(String query){
        BookletJson json = realm.where(BookletJson.class).findFirst();
        if(json == null)
            return null;
        Booklet booklet = new Gson().fromJson(json.toString(), Booklet.class);

        ArrayList<Project> projects = new ArrayList<>();
        for (int i = 0; i < booklet.getProjects().size(); i++) {
            String title = projects.get(i).getTitle();
            if(title.contains(query))
                projects.add(projects.get(i));
        }
        return projects;
    }

    public Project getProject(int position){
        BookletJson json = realm.where(BookletJson.class).findFirst();
        if(json == null)
            return null;
        Booklet booklet = new Gson().fromJson(json.toString(), Booklet.class);
        return booklet.getProjects().get(position);
    }

    public void setLastCheckedDate(){
        LastChecked last = new LastChecked();
        last.setLastCheckedDate(getCurrentDate());


        realm.beginTransaction();
        realm.clear(LastChecked.class);
        realm.copyToRealmOrUpdate(last);
        realm.commitTransaction();
    }

    public String getCurrentVersion(){
        BookletVersion version = realm.where(BookletVersion.class).findFirst();
        if(version == null)
            return "0.0.0";
        return version.getVersion();
    }

    public String getLastDownloadDate(){
        BookletVersion version = realm.where(BookletVersion.class).findFirst();
        if(version == null)
            return null;
        return version.getDownloadDate();
    }

    public String getLastCheckedDate(){
        LastChecked last = realm.where(LastChecked.class).findFirst();
        if(last == null)
            return null;
        return last.getLastCheckedDate();
    }

    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }
}
