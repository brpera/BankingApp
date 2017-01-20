package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class AccountInfoActivity extends AppCompatActivity {

    TextView accNo, fName, address, conNo, currentBal;
    DecimalFormat df = new DecimalFormat("0.00");
    double curBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        curBal = Double.parseDouble(AccountInfo.accountBalance);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        getAccountInfo();
    }

    void getAccountInfo(){
        accNo = (TextView)findViewById(R.id.txtAccNo);
        fName = (TextView)findViewById(R.id.txtFname);
        address = (TextView)findViewById(R.id.txtAddress);
        conNo = (TextView)findViewById(R.id.txtContactNo);
        currentBal = (TextView)findViewById(R.id.txtCurBal);

        accNo.setText(AccountInfo.accountID);
        fName.setText(AccountInfo.lastName + ", " + AccountInfo.firstName + " " + AccountInfo.middleName);
        address.setText(AccountInfo.currentAddress);
        conNo.setText(AccountInfo.contactNo);
        currentBal.setText("PHP " + df.format(curBal));

    }

    public void editAccount(View view){
        Intent intent = new Intent(AccountInfoActivity.this, EditAccountInfo.class);
        startActivity(intent);
    }

    public void backToHome(View view){
        Intent intent = new Intent(AccountInfoActivity.this, Home.class);
        startActivity(intent);
    }
}
