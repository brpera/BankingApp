package com.samplebankapp.my.bankingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EditAccountInfo extends AppCompatActivity {

    TextView fName, mName, lName, address, conNo, pw;
    DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_info);

        getAccountDetails();
    }

    void getAccountDetails(){

        fName = (TextView)findViewById(R.id.txtEditFname);
        mName = (TextView)findViewById(R.id.txtEditMname);
        lName = (TextView)findViewById(R.id.txtEditLname);
        address = (TextView)findViewById(R.id.txtEditAddress);
        conNo = (TextView)findViewById(R.id.txtEditContact);
        pw = (TextView)findViewById(R.id.txtEditPassword);

        fName.setText(AccountInfo.firstName);
        mName.setText(AccountInfo.middleName);
        lName.setText(AccountInfo.lastName);
        address.setText(AccountInfo.currentAddress);
        conNo.setText(AccountInfo.contactNo);
        pw.setText(AccountInfo.pass);
    }

    public void updateInfo(View view){

        if (fName.getText().toString().trim().equals("")){
            fName.setError("This field can not be blank");
        }
        else if (lName.getText().toString().trim().equals("")){
            lName.setError("This field can not be blank");
        }
        else if (address.getText().toString().trim().equals("")){
            address.setError("This field can not be blank");
        }
        else if (conNo.getText().toString().trim().equals("")){
            conNo.setError("This field can not be blank");
        }
        else if (pw.getText().toString().trim().equals("")){
            pw.setError("This field can not be blank");
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Save changes?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    Intent intent = new Intent(EditAccountInfo.this, AccountInfoActivity.class);

                    db.updateCustomer(new Customer(AccountInfo.accountID, fName.getText().toString(),
                            mName.getText().toString(), lName.getText().toString(),
                            address.getText().toString(), conNo.getText().toString(),
                            pw.getText().toString()));

                    AccountInfo.firstName = fName.getText().toString();
                    AccountInfo.middleName = mName.getText().toString();
                    AccountInfo.lastName = lName.getText().toString();
                    AccountInfo.currentAddress = address.getText().toString();
                    AccountInfo.contactNo = conNo.getText().toString();
                    AccountInfo.pass = pw.getText().toString();

                    Toast.makeText(getApplicationContext(), "Account updated successfully!", Toast.LENGTH_SHORT).show();


                    dialog.dismiss();
                    startActivity(intent);

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

    }

    public void backToInfo(View view){
        Intent intent = new Intent(EditAccountInfo.this, AccountInfoActivity.class);
        startActivity(intent);
    }


}

