package com.adzumi.zumievents.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adzumi.zumievents.R;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mLoginuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        mLoginuser = (TextView) findViewById(R.id.loginTextView);
        mLoginuser.setOnClickListener(this);
    }

    public void onClick(View view){
        if(view == mLoginuser){
            Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
