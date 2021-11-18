package com.example.program;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class LoginActivity extends AppCompatActivity {

    EditText inputemail, inputpassword;
    String email, password;
    Button login;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    TextView register, forgotpassword;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        mAuth = FirebaseAuth.getInstance();




        inputemail = (EditText) findViewById(R.id.email);
        inputpassword = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);
        register =  findViewById(R.id.register);
        forgotpassword = findViewById(R.id.forgot_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SetValidation();
            }
        });

    }


    public void SetValidation() {

try {
    // Check for a valid email address.
    email = inputemail.getText().toString();
    password = inputpassword.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(LoginActivity.this, "Masukkan Email Yang Benar", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(LoginActivity.this, "Masukkan Password Yang Benar", Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<6){
            Toast.makeText(LoginActivity.this, "Password Salah (Minimum 6)", Toast.LENGTH_SHORT).show();
        }

    assert mAuth != null;
    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task)
                {

                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Sukses", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();


//                                if (isEmailValid == isPasswordValid) {
//                                    if (inputemail.getText().toString().equals("admin@gmail.com") == inputpassword.getText().toString().equals("admin1234")) {
//                                        Toast.makeText(getApplicationContext(), "Selamat Datang Admin", Toast.LENGTH_SHORT).show();
//                                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
//                                        startActivity(intent1);
//                                    } else {
//                                        passError.setError("Username and Password Not True");
//                                    }
//                                }

                    }
                }
            });
} catch (Exception e){}

    }


    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void reset_password(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(intent);
    }
}