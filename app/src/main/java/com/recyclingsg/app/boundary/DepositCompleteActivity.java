package com.recyclingsg.app.boundary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.recyclingsg.app.R;

/**
 * This class is the Deposit Complete Activity. It starts upon the completion of adding deposit records and displays points and information of the deposit to the user.
 * @author Honey Stars
 * @version 1.0
 */

public class DepositCompleteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_complete);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Extra points the user will receive
        Intent intentFromDepositActivity = getIntent();
        String DepositActivityStringTransfer = intentFromDepositActivity.getStringExtra(DepositActivity.pointTransfer);
        ImageButton depositCompleteContinue = findViewById(R.id.depositComplete_continue);
        depositCompleteContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick_confirm_button(view);
            }
        });
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
