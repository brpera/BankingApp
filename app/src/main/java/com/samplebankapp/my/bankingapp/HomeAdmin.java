package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class HomeAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home_admin);
    }

    public void showMaintenance(View view)
    {
        Intent intent = new Intent(HomeAdmin.this, Maintenance.class);
        startActivity(intent);
    }

    public void showTransactions(View view)
    {
        Intent intent = new Intent(HomeAdmin.this, Transactions.class);
        startActivity(intent);
    }

    public void showLogs(View view){
        Intent intent = new Intent(HomeAdmin.this, TransactionLogsActivity.class);
        startActivity(intent);
    }

    public void showLogout(View view)
    {
        Intent intent = new Intent(HomeAdmin.this, MainActivity.class);
        startActivity(intent);
    }
}
