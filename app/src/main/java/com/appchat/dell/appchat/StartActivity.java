package com.appchat.dell.appchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnRegister;

    FirebaseUser user;
    @Override
    protected void onStart() {
        super.onStart();

        user = FirebaseAuth.getInstance().getCurrentUser();
        //check internet
        boolean internet = new BroadcastCheckInternet().isConnected(StartActivity.this);
        if(internet)
            //check user != null
            if(user != null){
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        else
            Toast.makeText(StartActivity.this, "Lost connect.", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //axa
        btnLogin = findViewById(R.id.btnLoginStart);
        btnRegister = findViewById(R.id.btnRegisterStart);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                boolean internet = new BroadcastCheckInternet().isConnected(StartActivity.this);
                if(internet)
                    startActivity(intent);
                else
                    Toast.makeText(StartActivity.this, "Lost connect.", Toast.LENGTH_LONG).show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                boolean internet = new BroadcastCheckInternet().isConnected(StartActivity.this);
                if(internet)
                    startActivity(intent);
                else
                    Toast.makeText(StartActivity.this, "Lost connect.", Toast.LENGTH_LONG).show();
            }
        });

    }
}
