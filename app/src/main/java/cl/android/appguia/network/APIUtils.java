package cl.android.appguia.network;

public class APIUtils {


    private APIUtils(){
    };

    public static final String API_URL = "http://170.239.85.247:3111";
    //public static final String API_URL = "http://169.254.35.189:8080/demo/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }
}
