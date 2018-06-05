package com.neverstop_sharing.myretrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.neverstop_sharing.myretrofit.apihelper.AuthService;
import com.neverstop_sharing.myretrofit.apihelper.UtilsApi;
import com.neverstop_sharing.myretrofit.response.GetRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Button btnSubmitRegist;
    EditText edtEmail,edtPassword,edtBirthdate,edtName,edtPhone,edtStatus,edtJob;
    RadioGroup rg;
    RadioButton rbMale,rbFemale;
    AuthService authService;
    ProgressDialog progressDialog;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        authService = UtilsApi.getAPIService();
        edtEmail = (EditText)findViewById(R.id.edtRegistPassword);
        edtPassword = (EditText)findViewById(R.id.edtRegistPassword);
        edtBirthdate = (EditText)findViewById(R.id.edtRegistBirthdate);
        edtName = (EditText)findViewById(R.id.edtRegistName);
        edtPhone = (EditText)findViewById(R.id.edtRegistPhone);
        edtStatus = (EditText)findViewById(R.id.edtRegistStatus);
        edtJob = (EditText)findViewById(R.id.edtRegistJob);


        rg = (RadioGroup)findViewById(R.id.rg_gender_register);
        btnSubmitRegist = (Button)findViewById(R.id.btnRegisterSubmit);
        btnSubmitRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = "";
                int selectedId = rg.getCheckedRadioButtonId();
                if (selectedId == R.id.rb_regis_male){
                    gender = "m";
                }else{
                    gender = "f";
                }
                RadioButton rb = (RadioButton)findViewById(selectedId);
                progressDialog = ProgressDialog.show(mContext,null,"Harap menunggu...",true,false);
                authService.registerRequest(edtEmail.getText().toString(),
                        edtPassword.getText().toString(),
                        edtBirthdate.getText().toString(),
                        edtName.getText().toString(),
                        edtPhone.getText().toString(),
                        edtStatus.getText().toString(),
                        edtJob.getText().toString(),
                        gender).enqueue(new Callback<GetRegister>() {
                            @Override
                            public void onResponse(Call<GetRegister> call, Response<GetRegister> response) {
                                GetRegister getRegister = response.body();
                                if (response.isSuccessful()){
                                    Log.d("debuging","connected");
                                    if (!getRegister.getError()){
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this,getRegister.getMessage(),Toast.LENGTH_SHORT).show();
                                    }else{progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this,getRegister.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<GetRegister> call, Throwable t) {
                                progressDialog.dismiss();
                            }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
