package com.recyclingsg.app;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PostPrivateCollectionPointActivity extends AppCompatActivity {

    private static final String TAG = "PrivatePointActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_private_collection_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText addressFillField = (EditText) findViewById(R.id.address_fill_up_field);
        final EditText zipFillField = (EditText) findViewById(R.id.zip_fill_up_field);
        final EditText contactDetailsFillField = (EditText) findViewById(R.id.contact_details_fill_up_field);
        final EditText typeOfTrashFillField = (EditText) findViewById(R.id.typeOfTrash_fill_up_field);
        final EditText pricesFillField = (EditText) findViewById(R.id.prices_fill_up_field);
        final EditText openingTimeFillField = (EditText) findViewById(R.id.opening_time_fill_up_field);
        final EditText closingTimeFillField = (EditText) findViewById(R.id.closing_time_fill_up_field);
        Button postPrivateCollectionPointButton = (Button) findViewById(R.id.postPrivateCollectionPointButton);

        postPrivateCollectionPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressFillField.getText()==((EditText) findViewById(R.id.address_fill_up_field)).getHint() ||
                        zipFillField.getText()==(EditText) findViewById(R.id.zip_fill_up_field) ||
                        contactDetailsFillField.getText()==(EditText) ((EditText) findViewById(R.id.zip_fill_up_field)).getHint() ||
                        typeOfTrashFillField.getText()==null ||
                        pricesFillField.getText()==null ||
                        openingTimeFillField.getText()==null ||
                        closingTimeFillField.getText()==null ){
                    Log.d(TAG, "onClick: null fields present");
                    Toast.makeText(getApplicationContext(), "Fill up all fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "onClick: submitted post");

                    Log.d(TAG, "onClick: address" + addressFillField.getText().toString());

                    //save texts


                    Intent intent = new Intent(PostPrivateCollectionPointActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


}
