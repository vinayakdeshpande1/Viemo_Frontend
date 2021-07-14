package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Regestration extends AppCompatActivity {

    EditText email,password,address,contact,name;
    Button btnSignup,btnLogin;
    TextInputLayout txtemail,txtcontactno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_pwd);
        contact=findViewById(R.id.et_mob);
        address=findViewById(R.id.et_addr);
        name=findViewById(R.id.et_fname);
        btnSignup=findViewById(R.id.btn_SignUp);
        btnLogin=findViewById(R.id.btn_Login);
        txtemail=findViewById(R.id.textInputLayout6);
        txtcontactno=findViewById(R.id.textInputLayout5);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtemail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                txtcontactno.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(contact.getText().toString()) || TextUtils.isEmpty(address.getText().toString())||TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(Regestration.this,"above information Required",Toast.LENGTH_LONG).show();
                }
                else if( ! validateEmailAddress(email)){
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                }
                else if(! validatePhoneNumber(contact)){
                    Toast.makeText(getApplicationContext(),"Invalid contact number", Toast.LENGTH_SHORT).show();
                }
                else  {
                    checkemail();
                }

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainactivity();
            }
        });


    }

    public void checkemail() {

        RegestrationRequest regestrationRequest =new RegestrationRequest();
        regestrationRequest.setEmail(this.email.getText().toString());
        Call<String> regestrationResponceCall = ApiClient.getUserService().checkEmail(regestrationRequest.getEmail());
        regestrationResponceCall.enqueue(new Callback<String>(){

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if(result.equals("1")){
                    txtemail.setError("Email id already registered");
                }
                else if(result.equals("0")){
                  //  txtemail.setError(null);
                    checkphoneNo();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Regestration.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    public void checkphoneNo() {

        RegestrationRequest regestrationRequest =new RegestrationRequest();
        regestrationRequest.setContact(this.contact.getText().toString());
        Call<String> regestrationResponceCall = ApiClient.getUserService().checkPhoneNumber(regestrationRequest.getContact());
        regestrationResponceCall.enqueue(new Callback<String>(){

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if(result.equals("1")){
                    txtcontactno.setError(" Phone number already registered");
                }
                else if(result.equals("0")){
                    //txtcontactno.setError(null);
                    regestration();
                }

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Regestration.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean validateEmailAddress(EditText email){
        String emailInput = email.getText().toString();
        return Patterns.EMAIL_ADDRESS.matcher(emailInput).matches();
    }

   private boolean validatePhoneNumber(EditText contact){
        String contactInput = contact.getText().toString();
       return contactInput.length() == 10;
   }

   public void openMainactivity(){
       Intent intent = new Intent(this,MainActivity.class);
       startActivity(intent);
   }


    public void regestration(){
        RegestrationRequest regestrationRequest =new RegestrationRequest();
        regestrationRequest.setName(name.getText().toString());
        regestrationRequest.setEmail(email.getText().toString());
        regestrationRequest.setAddress(address.getText().toString());
        regestrationRequest.setPassword(password.getText().toString());
        regestrationRequest.setContact(contact.getText().toString());



        Call<String> regestrationResponceCall = ApiClient.getUserService().userRegestration(regestrationRequest);
       regestrationResponceCall.enqueue(new Callback<String>(){
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
              String result = response.body();
               Log.d("Regestration",result);
               String abc;
               if(result.equals("Registered Successfully"))
                   abc="Registered Successfully";
               else
                   abc="Try again";
               Toast.makeText(getApplicationContext(),abc,Toast.LENGTH_LONG).show();
           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {
               Toast.makeText(Regestration.this,t.getMessage(),Toast.LENGTH_LONG).show();
           }
       });
    }

}