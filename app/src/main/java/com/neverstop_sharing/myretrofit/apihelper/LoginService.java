package com.neverstop_sharing.myretrofit.apihelper;

import com.neverstop_sharing.myretrofit.response.GetLogin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    //fungsi API untuk login
    @FormUrlEncoded
    @POST("user/login")
    Call<GetLogin> loginRequest(@Field("username") String username,
                                @Field("password") String password);

    //Fungsi api untuk regist

}
