package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Transactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_transactions);
    }

    public void showAdminDeposit(View view){
        Intent intent = new Intent(Transactions.this, AdminDeposit.class);
        startActivity(intent);
    }

    public void showAdminWithdraw(View view){
        Intent intent = new Intent(Transactions.this, AdminWithdraw.class);
        startActivity(intent);
    }

    public void showAdminMoneyTrans(View view){
        Intent intent = new Intent(Transactions.this, AdminMoneyTransfer.class);
        startActivity(intent);
    }

    public void showCheckBal(View view){
        Intent intent = new Intent(Transactions.this, AdminCheckBalance.class);
        startActivity(intent);
    }

    public void showHomeAdminFromTrans(View view){
        Intent intent = new Intent(Transactions.this, HomeAdmin.class);
        startActivity(intent);
    }
}
