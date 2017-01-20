package com.samplebankapp.my.bankingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MoneyTransfer extends AppCompatActivity {

    TextView txtCurrentBalance, txtNewBalance, lblNewBal, lblTranAmt, txtTranAmt;
    EditText txtAccNo, txtAmount;
    double curBal, subBal, totBal, otherTotal;
    DecimalFormat df = new DecimalFormat("0.00");
    DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);

        curBal = Double.parseDouble(AccountInfo.accountBalance);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        txtCurrentBalance = (TextView)findViewById(R.id.txtCurrentBal);
        txtCurrentBalance.setText("PHP " + df.format(curBal));
    }

    public void moneyTransfer(View view){
        curBal = Double.parseDouble(AccountInfo.accountBalance);
        txtNewBalance = (TextView)findViewById(R.id.txtNewBal);
        lblNewBal = (TextView)findViewById(R.id.lblNewBal);
        lblTranAmt = (TextView)findViewById(R.id.lblTranAmount);
        txtTranAmt = (TextView)findViewById(R.id.txtTranAmount);
        txtAccNo = (EditText)findViewById(R.id.txtAccount);
        txtAmount = (EditText)findViewById(R.id.txtTAmount);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = sdf.format(c.getTime());

        if (txtAccNo.getText().toString().trim().equals("")){
            txtAccNo.setError("This field can not be blank");
        }
        else if(txtAmount.getText().toString().trim().equals("")){
            txtAmount.setError("This field can not be blank");
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    subBal = Double.parseDouble(txtAmount.getText().toString());

                    db.findUser(txtAccNo.getText().toString().trim());
                    if(AccountInfo.hasAccount == true){
                        double fromOtherAcc = Double.parseDouble(AccountInfo.fromOtherAccount);
                        otherTotal = fromOtherAcc + subBal;
                        if (subBal < 200) {
                            Toast.makeText(getApplicationContext(), "Must be greater than or equal to PHP 200.00!", Toast.LENGTH_SHORT).show();
                            txtAmount.setText("");
                        } else if (subBal > 10000) {
                            Toast.makeText(getApplicationContext(), "Must be less than or equal to PHP 10, 000.00!", Toast.LENGTH_SHORT).show();
                            txtAmount.setText("");
                        } else if (curBal <= 100 || curBal < subBal) {
                            Toast.makeText(getApplicationContext(), "Insufficient Balance!", Toast.LENGTH_SHORT).show();
                            txtAmount.setText("");
                        } else {
                            totBal = curBal - subBal;
                            if (totBal == 0) {
                                Toast.makeText(getApplicationContext(), "Must have maintaining balance of PHP 50.00!", Toast.LENGTH_SHORT).show();
                                txtAmount.setText("");
                            } else if(totBal == 50 && subBal < totBal) {
                                Toast.makeText(getApplicationContext(), "Must have maintaining balance of PHP 50.00!", Toast.LENGTH_SHORT).show();
                                txtAmount.setText("");
                            }else{
                                db.updateSavings(AccountInfo.accountID, totBal);
                                db.updateSavings(txtAccNo.getText().toString(), otherTotal);
                                db.addLogs(new TransactionLogs("MONEY TRANSFER to " + txtAccNo.getText().toString(), AccountInfo.accountID, subBal, formattedDate));
                                db.addLogs(new TransactionLogs("MONEY TRANSFER from " + AccountInfo.accountID, txtAccNo.getText().toString(),  subBal, formattedDate));
                                AccountInfo.accountBalance = Double.toString(totBal);
                                txtAmount.setText("");
                                txtAccNo.setText("");
                                lblNewBal.setVisibility(View.VISIBLE);
                                txtNewBalance.setText("PHP " + df.format(totBal));
                                txtNewBalance.setVisibility(View.VISIBLE);
                                lblTranAmt.setVisibility(View.VISIBLE);
                                txtTranAmt.setVisibility(View.VISIBLE);
                                txtTranAmt.setText("PHP " + df.format(subBal));
                                Toast.makeText(getApplicationContext(), "Transaction Success!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    else if(AccountInfo.hasAccount == false){
                        Toast.makeText(getApplicationContext(), "Account does not exist!", Toast.LENGTH_SHORT).show();
                        txtAmount.setText("");
                        txtAccNo.setText("");
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



    public void cancelTransfer(View view){
        Intent intent = new Intent(MoneyTransfer.this, Home.class);
        startActivity(intent);
    }
}
