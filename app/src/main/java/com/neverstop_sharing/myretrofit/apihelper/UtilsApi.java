package com.neverstop_sharing.myretrofit.apihelper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.1.110/bmt-mid/index.php/api/";

    //mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
