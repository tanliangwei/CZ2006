package com.recyclingsg.app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DepositCompleteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle b = getIntent().getExtras();
        //Extra points the user will receive
        String points = "0";
        TextView txt_points = (TextView) findViewById(R.id.txt_points);
        if(b != null) {
            points = b.getString("points");
            txt_points.setText("Points: +" + points);
        }else{
            txt_points.setText("Points: +0");
        }

        setContentView(R.layout.activity_deposit_complete);

    }
}
