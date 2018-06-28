package com.adzumi.zumievents.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.adzumi.zumievents.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    private TextView mRegisterTextView;
    @BindView(R.id.registerTextView) TextView mRegisterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mRegisterTextView = (TextView) findViewById(R.id.registerTextView);

        mRegisterTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if (view == mRegisterTextView) {
            Intent intent = new Intent(MainActivity.this, UserRegistrationActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
