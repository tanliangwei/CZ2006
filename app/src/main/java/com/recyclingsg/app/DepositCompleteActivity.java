package com.recyclingsg.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;


public class DepositCompleteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageButton depositCompleteContinue = findViewById(R.id.depositComplete_continue);
        depositCompleteContinue.callOnClick(onClick_confirm_button(););
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_complete);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Extra points the user will receive
        Intent intentFromDepositActivity = getIntent();
        String DepositActivityStringTransfer = intentFromDepositActivity.getStringExtra(DepositActivity.pointTransfer);
        String points = "0";
        TextView txt_points = (TextView) findViewById(R.id.txt_points);
        if(this != null){
        if(DepositActivityStringTransfer != null) {
            txt_points.setText("Points: +" + DepositActivityStringTransfer);
        }else {
            txt_points.setText("Points: 0");
            }
        }
    }
    public void onClick_confirm_button(View v){
        if(v.getId() == R.id.depositComplete_continue){
            Intent intentBackToStartPage = new Intent(this.getApplicationContext(), MainActivity.class);
            this.startActivity(intentBackToStartPage);
        }
    }
}
