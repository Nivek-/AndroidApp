package cl.android.appguia.network;

import java.util.List;
import cl.android.appguia.network.request.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UserService {

    @GET("/usuarios/{id}.json")
    Call<Usuarios> getUsuario(@Path("id") String id);

    //@POST("/usuarios/{id}/edit")
    //Call<Usuarios> UpdateUser(@Path("id") String id);

    @PUT("/usuarios/{id}")
    Call<Usuarios> updateUser(@Path("id") String id, @Body Usuarios User);

    @DELETE("/usuarios/{id}")
    Call<Usuarios> deleteUser(@Path("id") String id);

}


