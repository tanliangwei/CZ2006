package com.recyclingsg.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class FacebookLogin extends AppCompatActivity {
    private static final String TAG = "FacebookLogin";
    LoginButton loginButton;
    private static boolean loggedIn = true; // true means didn't login
    CallbackManager callbackManager;
    public static boolean getLoginStatus(){
        return loggedIn;
    }
    private AccessToken oldAccessToken = null;
    private AccessToken newAccessToken = null;
    private AccessToken currentAccessToken = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.getApplicationContext();
        setContentView(R.layout.activity_facebook_login);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message=" ";
        message = (String)intent.getStringExtra("message");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);


        loginButton=(LoginButton)findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loggedIn = AccessToken.getCurrentAccessToken()==null;
                currentAccessToken = oldAccessToken;
                oldAccessToken = AccessToken.getCurrentAccessToken();
                newAccessToken = AccessToken.getCurrentAccessToken();
                final String userId = loginResult.getAccessToken().getUserId();
                UserManager.setUserID(userId);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                try {
                                    String name = jsonObject.getString("name");
                                    UserManager.setUserName(name);

                                }
                                catch (JSONException e){
                                    Log.e(TAG, e.getMessage());
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name");
                request.setParameters(parameters);
                request.executeAsync();
                if(currentAccessToken!=newAccessToken){
                    Intent backToMain=new Intent(FacebookLogin.this, MainActivity.class);
                    startActivity(backToMain);
                }


            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.v("LoginActivity", exception.getCause().toString());
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
