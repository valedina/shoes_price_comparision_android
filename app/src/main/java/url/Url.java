package url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {

    public static final String BASE_URL = "http://10.0.2.2:8005/";
    public static String Cookie="";

    public static int shoesId ;
    public static int storeId ;

    public static int userId;
    public static String user="ashish";


    public static Retrofit getInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}

