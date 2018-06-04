package com.neverstop_sharing.myretrofit.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://bmt.qisetta.com/index.php/api/";

    //mendeklarasikan Interface BaseApiService
    public static LoginService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(LoginService.class);
    }
}
