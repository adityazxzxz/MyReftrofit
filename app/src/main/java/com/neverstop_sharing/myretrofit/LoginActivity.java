package com.neverstop_sharing.myretrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neverstop_sharing.myretrofit.apihelper.AuthService;
import com.neverstop_sharing.myretrofit.apihelper.UtilsApi;
import com.neverstop_sharing.myretrofit.response.GetLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.neverstop_sharing.myretrofit.apihelper.UtilsApi.BASE_URL_API;

public class LoginActivity extends AppCompatActivity{
    EditText edtUsername,edtPassword;
    Button btnLogin,btnRegist;
    ProgressDialog loading;
    Context mContext;
    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        authService = UtilsApi.getAPIService();
        initComponents();
    }



    private void initComponents() {
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnRegist = (Button)findViewById(R.id.btn_regist);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext,null,"Harap menunggu..",true,false);
                requestLogin();
            }
        });

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }



    private void requestLogin() {
        String uname = edtUsername.getText().toString();
        String paswd = edtPassword.getText().toString();
        authService.loginRequest(uname,paswd)
                .enqueue(new Callback<GetLogin>() {
                    @Override
                    public void onResponse(Call<GetLogin> call, Response<GetLogin> response) {
                        GetLogin getLogin = response.body();
                        if(response.isSuccessful()){
                            Log.d("debuging","connected");
                            if(!getLogin.getError()){
                                SharedPreferences sharedPreferences = getSharedPreferences("loginInfo",mContext.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userid",getLogin.getData().getId());
                                editor.putString("usertoken",getLogin.getData().getAuthToken());
                                editor.putString("username",getLogin.getData().getUsername());
                                editor.putString("name",getLogin.getData().getName());
                                loading.dismiss();
                                Log.d("debuging",getLogin.getMessage());
                                Log.d("debuging",getLogin.getData().getAuthToken());
                                Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                loading.dismiss();
                                Toast.makeText(mContext,getLogin.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetLogin> call, Throwable t) {
                        Log.e("debug","onFailure:ERROR > "+t.getMessage());
                        Toast.makeText(mContext,"Koneksi Internet Bermasalah",Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });
    }
}
