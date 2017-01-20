package com.samplebankapp.my.bankingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class DeleteDetails extends AppCompatActivity {

    String accId;
    DatabaseHandler db = new DatabaseHandler(this);
    TextView accNo, fName, address, conNo, currentBal;
    DecimalFormat df = new DecimalFormat("0.00");
    double curBal;
    public static boolean isDeleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_details);

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

    public void deleteCustomer(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                Intent intent = new Intent(DeleteDetails.this, DeleteActivity.class);

                db.deleteCust(accId);
                if(isDeleted == true){
                    Toast.makeText(getApplicationContext(), "Account deleted!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else if(isDeleted == false){
                    Toast.makeText(getApplicationContext(), "Error deleting account!", Toast.LENGTH_SHORT).show();
                }


                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void cancelDelete(View view){
        Intent intent = new Intent(DeleteDetails.this, DeleteActivity.class);
        startActivity(intent);
    }
}
