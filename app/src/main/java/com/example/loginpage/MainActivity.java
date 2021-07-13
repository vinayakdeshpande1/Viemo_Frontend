package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button btnLogin,btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.edit_text_email);
        password=findViewById(R.id.edit_text_password);
        btnLogin=findViewById(R.id.login_btn);
        btnSignup = findViewById(R.id.signup_btn);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                   Toast.makeText(MainActivity.this,"username/password Required",Toast.LENGTH_LONG).show();
                }else if(! validateEmailAddress(email)){
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                }
                else if(validateEmailAddress(email)){
                    //procced to login
                    login();

                }

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }
    private boolean validateEmailAddress(EditText email){
        String emailInput = email.getText().toString();
        if( ! Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(MainActivity.this,"Invalid email id",Toast.LENGTH_LONG).show();
            return false;
        }
        else
            return true;
    }

    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<String> loginResponceCall = ApiClient.getUserService().userLogin(loginRequest.getUsername(),loginRequest.getPassword());
        loginResponceCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                Log.d("Login",result);
                String abc;
                if(result.equals("0")) {
                    abc = "invalid username/password";
                }
                else {
                    abc = "login Succesful";
                    openHomepage();
                }

                Toast.makeText(getApplicationContext(),abc,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Login",t.toString());
            }
        });

    }

    public void openNewActivity(){
        Intent intent = new Intent(this,Regestration.class);
        startActivity(intent);
    }

    public void openHomepage(){
        Intent intent = new Intent(this,Home_Page.class);
        startActivity(intent);

    }

}