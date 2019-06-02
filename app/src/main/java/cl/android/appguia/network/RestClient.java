package cl.android.appguia.network;

import com.orhanobut.hawk.Hawk;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    //https://jsonplaceholder.typicode.com/
    //Se designa direccion donde se consumiran los servicios
    public static final String BaseUrl="http://170.239.85.247:3111";
//Solo GET
    public static API setup(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(API.class);
    }

    // GET y POST
    public static API postSetup() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Token")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        //.header("access_token", Token)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(API.class);
    }

    public  static Retrofit retrofit= null;
    public static Retrofit getClient(String url){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

