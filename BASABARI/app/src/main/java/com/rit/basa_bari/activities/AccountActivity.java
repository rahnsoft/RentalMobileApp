package com.rit.basa_bari.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.rit.basa_bari.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void createAccount(View view) {
        Intent intent=new Intent(AccountActivity.this,RegisterActivity.class);
        startActivity(intent);


    }

    public void clickSignin(View view) {
        Intent intent=new Intent(AccountActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
