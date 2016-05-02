package lab.agimaulana.d3ifbooklet.API;

/**
 * Created by Agi Maulana on 5/3/2016.
 */
public class ImageDownloadAPI {
    /*
    For downloading, you can use ResponseBody as your return type --

    @GET("documents/checkout")
    @Streaming
    public Call<ResponseBody> checkout(@Query("documentUrl") String documentUrl, @Query("accessToken") String accessToken, @Query("readOnly") boolean readOnly);
    and you can get the ResponseBody input stream in your call back --

    Call<ResponseBody> call = RetrofitSingleton.getInstance(serverAddress)
                .checkout(document.getContentUrl(), apiToken, readOnly[i]);

    call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response,
                    Retrofit retrofit) {
                String fileName = document.getFileName();
                try {
                    InputStream input = response.body().byteStream();
                    //  rest of your code
    Your upload looks okay at first glance if you server handles multipart messages correctly. Is it working? If not, can you explain the failure mode? You also might be able to simplify by not making it multipart. Remove the @Multipart annotation and convert @Path to @Body --

    @POST("documents/checkin")
    public Call<String> checkin(@Query("documentId") String documentId, @Query("name") String fileName, @Query("accessToken") String accessToken, @Body RequestBody file);
     */

    /*

    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("http://wwwns.akamai.com/media_resources/globe_emea.png")
            .build();

    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
            System.out.println("request failed: " + e.getMessage());
        }

        @Override
        public void onResponse(Response response) throws IOException {
            response.body().byteStream(); // Read the data from the stream
        }
    });

    public interface Api {
        @GET("/media_resources/{imageName}")
        void getImage(@Path("imageName") String imageName, Callback<Response> callback);
    }

     */

}
