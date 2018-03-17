package com.recyclingsg.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.recyclingsg.app.R;


public class FacebookLogin extends AppCompatActivity {
    LoginButton loginButton;
    TextView textview;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textview = findViewById(R.id.text_view);
        FacebookSdk.getApplicationContext();
        setContentView(R.layout.activity_facebook_login);
        loginButton=(LoginButton)findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textview.setText("Login Success \n");
                String userId = loginResult.getAccessToken().getUserId();
                userManager.setUserId(userId);

            }

            @Override
            public void onCancel() {
                textview.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
