package com.recyclingsg.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class FacebookLogin extends AppCompatActivity {
    private static final String TAG = "FacebookLogin";
    LoginButton loginButton;
    private static boolean notLoggedIn = true; // true means didn't login
    CallbackManager callbackManager;
    public static boolean getLoginStatus(){
        return notLoggedIn;
    }
    private void updateLoginStatus(){
        notLoggedIn=(AccessToken.getCurrentAccessToken()==null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.getApplicationContext();
        setContentView(R.layout.activity_facebook_login);
        // Get the Intent that started this activity and extract the string
        final Intent intent = getIntent();
        String message=" ";
        message = (String)intent.getStringExtra("message");
        if(notLoggedIn){
            LoginManager.getInstance().logOut();
        }
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        loginButton=(LoginButton)findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile"));
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    Log.d(TAG,"onLogout Caught");
                    updateLoginStatus();
                    UserManager.setUserName(null);
                    UserManager.setUserID(null);

                }

            }
        };
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateLoginStatus();
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

                                    Log.d(TAG, "got user name "+name);

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
                //go back to main if successful logged in
                    Intent backToMain=new Intent(FacebookLogin.this, MainActivity.class);
                    startActivity(backToMain);



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
        accessTokenTracker.startTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
