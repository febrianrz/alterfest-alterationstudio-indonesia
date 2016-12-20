package id.web.alterationstudio.sewainaja.alterfest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.web.alterationstudio.sewainaja.alterfest.Config.Config;
import id.web.alterationstudio.sewainaja.alterfest.Helpers.Helpers;
import id.web.alterationstudio.sewainaja.alterfest.Response.Register;
import id.web.alterationstudio.sewainaja.alterfest.Service.LoginService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Callback<Register> {

    EditText etUsername, etPassword, etEmail, etRePassword;
    String usernameStr, emailStr, passwordStr, repasswordStr;
    Button btnRegis;
    TextView tvLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etRePassword = (EditText) findViewById(R.id.etRepassword);
        btnRegis = (Button) findViewById(R.id.btDaftar);
        tvLogin = (TextView) findViewById(R.id.tvLogin);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Loading...");

        btnRegis.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onResponse(Call<Register> call, Response<Register> response) {
        Register register = response.body();
        Toast.makeText(this,register.getMsg(),Toast.LENGTH_LONG).show();
        progressDialog.hide();
        if(register.getStatus()){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onFailure(Call<Register> call, Throwable t) {

    }

    @Override
    public void onClick(View view) {
        if(view == btnRegis){
            usernameStr = etUsername.getText().toString();
            emailStr = etEmail.getText().toString();
            passwordStr = etPassword.getText().toString();
            repasswordStr = etRePassword.getText().toString();
            /**
             * Cek seluruh field tidak kosong
             */
            if(usernameStr.matches("") || emailStr.matches("") || passwordStr.matches("") || repasswordStr.matches("")){
                Toast.makeText(this,"Field Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            } else {
                /**
                 * Cek Valid Email Format
                 */
                if(!Helpers.isValidEmail(emailStr)){
                    Toast.makeText(this,"Email Tidak Sesuai",Toast.LENGTH_LONG).show();
                } else {
                    if(passwordStr.length() < 6){
                        Toast.makeText(this,"Kata Sandi Minimal 6 digit",Toast.LENGTH_LONG).show();
                    } else {
                        /**
                         * Cek Password dan Repassword Sesuai
                         */
                        if(!passwordStr.equals(repasswordStr)){
                            Toast.makeText(this,"Kata Sandi Tidak Sesuai",Toast.LENGTH_LONG).show();
                        } else {
                            /**
                             * Cek From API
                             */
                            progressDialog.show();

                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(Config.getServerUrl())
                                    .client(client)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            LoginService loginService = retrofit.create(LoginService.class);
                            Call<Register> registerCall = loginService.postRegister(emailStr,usernameStr,passwordStr, Config.getApiKey());
                            registerCall.enqueue(this);
                        }
                    }

                }
            }
        } else if(view == tvLogin){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
