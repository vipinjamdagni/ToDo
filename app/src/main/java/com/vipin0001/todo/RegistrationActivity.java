package com.vipin0001.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity {

    private EditText RegisterEmail,Registerpwd;
    private Button RegisterBtn;
    private TextView RegisterQstn;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        
        toolbar=findViewById(R.id.loginToolbar);
        getSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");

        mAuth=FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);
        
        RegisterEmail=findViewById(R.id.RegistrationEmail);
        RegisterBtn=findViewById(R.id.RegistrationButton);
        Registerpwd=findViewById(R.id.RegistrationPassword);
        RegisterQstn=findViewById(R.id.RegistrationPageQuestion);
        RegisterQstn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=RegisterEmail.getText().toString().trim();
                String password=Registerpwd.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    RegisterEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Registerpwd.setError("Password is required");
                    return;
                }else{
                    loader.setMessage("Registration in Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                String error=task.getException().toString();
                                Toast.makeText(RegistrationActivity.this,"Registration Failed"+error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            loader.dismiss();
                        }else{
                            String error=task.getException().toString();
                            Toast.makeText(RegistrationActivity.this,"Registration Failed"+error,Toast.LENGTH_SHORT).show();
                            loader.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void getSupportActionBar(Toolbar toolbar) {
    }

}