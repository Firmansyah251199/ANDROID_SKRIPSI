package com.example.program;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {


    EditText inputemail;
    String email;
    Button resetpassword;
    TextView login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        inputemail = (EditText) findViewById(R.id.email);
        login = findViewById(R.id.back_login);
        resetpassword = (Button) findViewById(R.id.forgot_password);

        mAuth = FirebaseAuth.getInstance();


        //variabel tadi untuk memanggil fungsi
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }


    public void SetValidation() {
        try {
            // Check for a valid email address.
            email = inputemail.getText().toString();
            if(email.isEmpty()){
                Toast.makeText(ForgotPassword.this, "Masukkan Email Yang Terdaftar", Toast.LENGTH_SHORT).show();
            }

            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, "Reset Password Sukses. Silahkan Lihat Email Anda", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgotPassword.this, "Reset Password Gagal", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {

        }
    }



}

