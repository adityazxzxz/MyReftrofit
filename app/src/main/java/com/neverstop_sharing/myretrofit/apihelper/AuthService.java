package com.neverstop_sharing.myretrofit.apihelper;

import com.neverstop_sharing.myretrofit.response.GetLogin;
import com.neverstop_sharing.myretrofit.response.GetRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("user/login")
    Call<GetLogin> loginRequest(@Field("username") String username,
                                @Field("password") String password);

    //Fungsi api untuk regist

    //fungsi API untuk register
    @FormUrlEncoded
    @POST("user/regist")
    Call<GetRegister> registerRequest(@Field("username") String username,
                                      @Field("password") String password,
                                      @Field("birth_date") String birthdate,
                                      @Field("name") String name,
                                      @Field("phone_number") String phone,
                                      @Field("status") String status,
                                      @Field("job") String job,
                                      @Field("gender") String gender);
}

