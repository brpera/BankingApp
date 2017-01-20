package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CustomerDetails extends AppCompatActivity {

    String accId;
    DatabaseHandler db = new DatabaseHandler(this);
    TextView accNo, fName, address, conNo, currentBal;
    DecimalFormat df = new DecimalFormat("0.00");
    double curBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Intent intent = getIntent();
        accId = intent.getStringExtra(("txtAccNo"));

        AccountInfo.clearInfo();
        db.findUserFromAdmin(accId);

        curBal = Double.parseDouble(AccountInfo.accountBalance);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        accNo = (TextView)findViewById(R.id.txtAccCustNo);
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

    public void customerDetailsBackToMaint(View view){
        Intent intent = new Intent(CustomerDetails.this, AdminCustomerList.class);
        startActivity(intent);
    }
}
