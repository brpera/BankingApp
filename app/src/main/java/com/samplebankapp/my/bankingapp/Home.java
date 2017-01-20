package com.samplebankapp.my.bankingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class Home extends AppCompatActivity {

    TextView welcomeName;
    DecimalFormat df = new DecimalFormat("0.00");
    DatabaseHandler dbh = new DatabaseHandler(this);
    double curBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcomeName = (TextView)findViewById(R.id.textView13);
        welcomeName.setText("Welcome, " + AccountInfo.firstName + "!");

        curBal = Double.parseDouble(AccountInfo.accountBalance);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        Log.e("Reading: ", "Reading all contacts..");
        List<Savings> contacts = dbh.getAllSavings();

        for (Savings cn : contacts) {
            String log = "Id: " + cn.getSavingsIdId() + " ,accnum: " + cn.getAccountNum() + " ,amount: " + cn.getSavingsAmount();
            // Writing Contacts to log
            Log.e("Name: ", log);
        }
    }

    public void showDeposit(View view){
        Intent intent = new Intent(Home.this, Deposit.class);
        startActivity(intent);
    }

    public void showWithdraw(View view){
        Intent intent = new Intent(Home.this, Withdraw.class);
        startActivity(intent);
    }

    public void checkBalance(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your current balance is: PHP " + df.format(curBal))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //uhmm
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showAccountInfo(View view){
        Intent intent = new Intent(Home.this, AccountInfoActivity.class);
        startActivity(intent);
    }

    public void showMoneyTrans(View view){
        Intent intent = new Intent(Home.this, MoneyTransfer.class);
        startActivity(intent);
    }

    public void logoutHome(View view)
    {
        DatabaseHandler.flag = false;
        AccountInfo.clearInfo();
        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
    }


}
