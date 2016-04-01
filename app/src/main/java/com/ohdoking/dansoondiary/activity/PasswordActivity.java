package com.ohdoking.dansoondiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ohdoking.dansoondiary.R;

public class PasswordActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        Button b = (Button) findViewById(R.id.pass);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(getVisitState()){
                    i = new Intent(PasswordActivity.this,IconListActivity.class);
                }
                else{
                    i = new Intent(PasswordActivity.this,MainListActivity.class);
                }
                startActivity(i);
                finish();
            }
        });


    }
}
