package id.web.alterationstudio.sewainaja.alterfest.Service;

import id.web.alterationstudio.sewainaja.alterfest.Response.Login;
import id.web.alterationstudio.sewainaja.alterfest.Response.Register;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by febrian on 12/19/16.
 */
public interface LoginService {
    @FormUrlEncoded
    @POST("v1/register")
    Call<Register> postRegister(@Field("email") String email,
                                @Field("username") String username,
                                @Field("password") String password,
                                @Field("api_key") String apiKey);

    @FormUrlEncoded
    @POST("v1/login")
    Call<Login> postLogin(@Field("email") String email,
                          @Field("password") String password,
                          @Field("api_key") String apiKey);
}
