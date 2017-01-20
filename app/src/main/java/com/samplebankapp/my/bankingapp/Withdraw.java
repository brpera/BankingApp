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

public class Withdraw extends AppCompatActivity {

    TextView txtCurrentBalance, txtNewBalance, lblNewBal;
    EditText txtSubBal;
    double curBal, subBal, totBal;
    DecimalFormat df = new DecimalFormat("0.00");
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        curBal = Double.parseDouble(AccountInfo.accountBalance);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        txtCurrentBalance = (TextView)findViewById(R.id.txtCurrentBal);
        txtCurrentBalance.setText("PHP " + df.format(curBal));
    }

    public void withdrawMoney(View view){
        curBal = Double.parseDouble(AccountInfo.accountBalance);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = sdf.format(c.getTime());


        txtSubBal = (EditText)findViewById(R.id.txtAccount);
        lblNewBal = (TextView)findViewById(R.id.lblNewBal);
        txtNewBalance = (TextView)findViewById(R.id.txtNewBal);
        if (txtSubBal.getText().toString().trim().equals("")){
            txtSubBal.setError("This field can not be blank");
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    subBal = Double.parseDouble(txtSubBal.getText().toString());
                    if (subBal < 200) {
                        Toast.makeText(getApplicationContext(), "Must be greater than or equal to PHP 200.00!", Toast.LENGTH_SHORT).show();
                        txtSubBal.setText("");
                    } else if (subBal > 10000) {
                        Toast.makeText(getApplicationContext(), "Must be less than or equal to PHP 10, 000.00!", Toast.LENGTH_SHORT).show();
                        txtSubBal.setText("");
                    } else if (curBal <= 100 || curBal < subBal) {
                        Toast.makeText(getApplicationContext(), "Insufficient Balance!", Toast.LENGTH_SHORT).show();
                        txtSubBal.setText("");
                    } else {
                        totBal = curBal - subBal;
                        if (totBal == 0) {
                            Toast.makeText(getApplicationContext(), "Must have maintaining balance of PHP 50.00!", Toast.LENGTH_SHORT).show();
                            txtSubBal.setText("");
                        } else if(totBal == 50 && subBal < totBal) {
                            Toast.makeText(getApplicationContext(), "Must have maintaining balance of PHP 50.00!", Toast.LENGTH_SHORT).show();
                            txtSubBal.setText("");
                        }else{
                            db.updateSavings(AccountInfo.accountID, totBal);
                            db.addLogs(new TransactionLogs("WITHDRAW", AccountInfo.accountID, subBal, formattedDate));
                            AccountInfo.accountBalance = Double.toString(totBal);
                            txtSubBal.setText("");
                            lblNewBal.setVisibility(View.VISIBLE);
                            txtNewBalance.setText("PHP " + df.format(totBal));
                            txtNewBalance.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "Transaction Success!", Toast.LENGTH_SHORT).show();
                        }

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

    public void cancelWithdraw(View view){
        Intent intent = new Intent(Withdraw.this, Home.class);
        startActivity(intent);
    }
}
