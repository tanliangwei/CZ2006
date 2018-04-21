package com.recyclingsg.app.boundary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.recyclingsg.app.R;

/**
 * This class is the Help Activity. It displays information relevant to trash deposition.
 * @author Honey Stars
 * @version 1.0
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onClickConfirm(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
