package com.samplebankapp.my.bankingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Deposit extends AppCompatActivity {

    TextView txtCurrentBalance, txtNewBalance, lblNewBal;
    EditText txtAddBal;
    double curBal, addBal, totBal;
    DecimalFormat df = new DecimalFormat("0.00");
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);



        curBal = Double.parseDouble(AccountInfo.accountBalance);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        txtCurrentBalance = (TextView)findViewById(R.id.txtCurrentBal);
        txtCurrentBalance.setText("PHP " + df.format(curBal));






    }

    public void depositMoney(View view){
        curBal = Double.parseDouble(AccountInfo.accountBalance);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = sdf.format(c.getTime());



        txtAddBal = (EditText)findViewById(R.id.txtAccount);
        lblNewBal = (TextView)findViewById(R.id.lblNewBal);
        txtNewBalance = (TextView)findViewById(R.id.txtNewBal);
        if (txtAddBal.getText().toString().trim().equals("")){
            txtAddBal.setError("This field can not be blank");
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    addBal = Double.parseDouble(txtAddBal.getText().toString());
                    if (addBal < 200) {
                        Toast.makeText(getApplicationContext(), "Must be greater than or equal to PHP 200.00!", Toast.LENGTH_SHORT).show();
                        txtAddBal.setText("");
                    } else if (addBal > 10000) {
                        Toast.makeText(getApplicationContext(), "Must be less than or equal to PHP 10, 000.00!", Toast.LENGTH_SHORT).show();
                        txtAddBal.setText("");
                    } else {
                        totBal = curBal + addBal;
                        db.updateSavings(AccountInfo.accountID, totBal);
                        db.addLogs(new TransactionLogs("DEPOSIT", AccountInfo.accountID, addBal, formattedDate));
                        AccountInfo.accountBalance = Double.toString(totBal);
                        txtAddBal.setText("");
                        lblNewBal.setVisibility(View.VISIBLE);
                        txtNewBalance.setText("PHP " + df.format(totBal));
                        txtNewBalance.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Transaction Success!", Toast.LENGTH_SHORT).show();
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
    }

    public void cancelDeposit(View view){
        Intent intent = new Intent(Deposit.this, Home.class);
        startActivity(intent);
    }




}
