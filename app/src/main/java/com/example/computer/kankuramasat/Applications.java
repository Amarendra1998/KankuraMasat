package com.example.computer.kankuramasat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class Applications extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private EditText mailedit,nameedit,messagedit;
    private TextView mlogin;
    private Button mregbtn;
    private FirebaseAuth mauth;
    private String CurrentUserId;
    private DatabaseReference mRoot;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);
        mauth = FirebaseAuth.getInstance();
        initializefields();
        CurrentUserId = mauth.getCurrentUser().getUid();
        mRoot = FirebaseDatabase.getInstance().getReference();

        mregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createaccount();
            }
        });
    }
    private void createaccount() {
        String email = mailedit.getText().toString();
        String name = nameedit.getText().toString();
        String mess = messagedit.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please provide email...",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please provide name...",Toast.LENGTH_LONG).show();
        }if (TextUtils.isEmpty(mess)){
            Toast.makeText(this,"Please provide message...",Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog.setTitle("Addinf new information");
            progressDialog.setMessage("Please wait, process is under construction...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            Map<String,Object> profilemap =new HashMap<>();
            profilemap.put("uid",CurrentUserId);
            profilemap.put("email",email);
            profilemap.put("name",name);
            profilemap.put("message",mess);
            mRoot.child("Info").child(CurrentUserId).setValue(profilemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        SendUserToMainActivity();
                        Toast.makeText(Applications.this, "Profile Updated Successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(Applications.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void SendUserToMainActivity() {
        Intent loginInt = new Intent(Applications.this,Home.class);
        startActivity(loginInt);
        finish();
    }

    private void initializefields() {
        mailedit = (EditText)findViewById(R.id.mailedit);
        nameedit = (EditText)findViewById(R.id.passedit);
        messagedit = (EditText)findViewById(R.id.message);
        mregbtn = (Button)findViewById(R.id.regbtn);
        progressDialog = new ProgressDialog(this);
    }
    private void SendUserToLoginActivity() {
        Intent loginInt = new Intent(Applications.this,LoginActivity.class);
        startActivity(loginInt);
    }
}
