package com.adzumi.zumievents.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.adzumi.zumievents.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.loginTextView) TextView mLoginuser;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.emailEditText) EditText mEmailEditText;;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.createUserButton) EditText mCreateUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        ButterKnife.bind(this);

        mLoginuser.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
    }

    public void onClick(View view){
        if(view == mLoginuser){
            Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (view == mCreateUserButton) {
            createNewUser();
        }
    }
}
