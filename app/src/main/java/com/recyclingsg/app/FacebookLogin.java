package com.recyclingsg.app;

import android.app.Activity;
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
import com.facebook.login.LoginBehavior;
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
    private void startTargetActivity(String activity){
        Log.e(TAG,"STARTTARGETACTIVITY") ;
        try {
            Intent goToTargetActivity;
            Intent goToMain = new Intent(this,MainActivity.class);
            if (activity.equals("Statistics")) {
                goToTargetActivity = new Intent(this, StatisticsActivity.class);
            }
            else if (activity.equals("Deposit")) {
                goToTargetActivity = new Intent(this, DepositActivity.class);
            }
            else if (activity.equals("TrashPool")) {
                goToTargetActivity = new Intent(this, PostPrivateCollectionPointActivity.class);
            }
            else{
                goToTargetActivity = new Intent(this,MainActivity.class);
            }
            startActivity(goToMain);
            startActivity(goToTargetActivity);
        }
        catch(Exception e){
            Log.e(TAG,"Fail to get intent");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.getApplicationContext();
        setContentView(R.layout.activity_facebook_login);
        final Intent intent = getIntent();

        Log.e(TAG,"ONCREATE") ;

        // Get the Intent that started this activity and extract the string
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
        loginButton.setLoginBehavior(LoginBehavior.WEB_ONLY);
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    Log.e(TAG,"CURRENTACCESSTOKENNULL") ;
                    Log.d(TAG,"onLogout Caught");
                    updateLoginStatus();
                    UserManager.getInstance().setUserName(null);
                    UserManager.getInstance().setUserID(null);

                }

            }
        };
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateLoginStatus();
                final String userId = loginResult.getAccessToken().getUserId();
                UserManager.getInstance().setUserID(userId);
                StatisticsManager.getInstance().refreshData();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                try {
                                    String name = jsonObject.getString("name");

                                    UserManager.getInstance().setUserName(name);

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
                //go back to target activity if successful logged in
                String activity = " ";
                activity = intent.getStringExtra("activity");
                startTargetActivity(activity);

            }

            @Override
            public void onCancel() {
                // App code
                Log.e(TAG,"LOGIN ACTIVITY CANCEL") ;
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG,"ERRROR") ;
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
