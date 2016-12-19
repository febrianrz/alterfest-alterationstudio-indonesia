package id.web.alterationstudio.sewainaja.alterfest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import id.web.alterationstudio.sewainaja.alterfest.Config.Config;
import id.web.alterationstudio.sewainaja.alterfest.Helpers.Helpers;
import id.web.alterationstudio.sewainaja.alterfest.Response.Login;
import id.web.alterationstudio.sewainaja.alterfest.Service.LoginService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<Login> {

    EditText etEmail, etPassword;
    TextView tvRegister;
    ProgressDialog progressDialog;
    Button btnLogin;
    String passwordStr, emailStr;

    /**
     * Tampilan Login Awal
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(this.isLogin()){
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.button);
        tvRegister = (TextView) findViewById(R.id.tvRegis);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Loading... Please Wait");

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

    }

    public boolean isLogin(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("userLogin",false)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onResponse(Call<Login> call, Response<Login> response) {
        progressDialog.dismiss();
        Login login = response.body();
        Toast.makeText(this,login.getMsg(),Toast.LENGTH_LONG).show();
        if(login.getStatus()){

            /**
             * Jika berhasil, simpan data dalam sharePreference
             */
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("userLogin",true);
            editor.putString("userName",login.getUsername());
            editor.putString("userKey",login.getUserKey());
            editor.putString("userGambar",login.getGambar());
            editor.putInt("userSaldo",login.getSaldo());
            editor.putString("userEmail",etEmail.getText().toString());

            /**
             * Untuk object kategori
             */
            Gson gson = new Gson();
            String kategoriStr = gson.toJson(login.getKategori());
            editor.putString("kategoriTransaksi",kategoriStr);
            editor.commit();

            if(this.isLogin()){
                Intent i = new Intent(this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    public void onFailure(Call<Login> call, Throwable t) {

    }

    @Override
    public void onClick(View view) {
        if(view == tvRegister){
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
            finish();
        } else {
            passwordStr = etPassword.getText().toString();
            emailStr = etEmail.getText().toString();

            if(passwordStr.matches("") || emailStr.matches("")){
                Toast.makeText(this,"Field Tidak Boleh Kosong",Toast.LENGTH_LONG).show();
            } else {
                /**
                 * Cek Email Valid
                 */
                if(!Helpers.isValidEmail(emailStr)){
                    Toast.makeText(this,"Email Tidak Valid",Toast.LENGTH_LONG).show();
                } else {
                    /**
                     * Cek Password Minimal 6 digit
                     */
                    if(passwordStr.length() < 6){
                        Toast.makeText(this,"Kata Sandi Minimal 6 digit",Toast.LENGTH_LONG).show();
                    } else {
                        progressDialog.show();
                        /**
                         * Cek from API
                         */


                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Config.getServerUrl())
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        LoginService loginService = retrofit.create(LoginService.class);
                        Call<Login> registerCall = loginService.postLogin(emailStr,passwordStr, Config.getApiKey());
                        registerCall.enqueue(this);
                    }
                }

            }
        }
    }
}
