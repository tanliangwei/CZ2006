package com.recyclingsg.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DepositCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposit_category);
    }



    public  void onClick_trashForCash(View v){
        if(v.getId() == R.id.btn_cash_for_trash){
            Intent intent = new Intent(DepositCategoryActivity.this, DepositActivity.class);
            Bundle b = new Bundle();
            b.putString("trashCategory", "cash_for_trash");
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }

    public void onClick_eWaste(View v){
        if(v.getId() == R.id.btn_eWaste){
            Intent intent = new Intent(DepositCategoryActivity.this, DepositActivity.class);
            Bundle b = new Bundle();
            b.putString("trashCategory", "eWaste");
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }
}