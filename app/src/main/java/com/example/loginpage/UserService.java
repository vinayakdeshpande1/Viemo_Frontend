package com.example.loginpage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @FormUrlEncoded
    @POST("demo/signin")
    Call<String> userLogin(@Field("email") String email,@Field("password") String password);
    @POST("demo/signup")
    Call<String> userRegestration(@Body RegestrationRequest u);
    @GET("demo/checkEmail")
    Call<String> checkEmail(@Query("email") String email);
    @GET("demo/checkPhoneNumber")
    Call<String> checkPhoneNumber(@Query("contactNo") String phno);
}
