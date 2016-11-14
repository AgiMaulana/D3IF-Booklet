package lab.agimaulana.d3ifbooklet.API;

import java.util.concurrent.TimeUnit;

import lab.agimaulana.d3ifbooklet.config.BookletConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Agi Maulana on 4/13/2016.
 */
public class ServiceAdapter {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BookletConfig.BASE_URL+"/")
                    .addConverterFactory(SimpleXmlConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
