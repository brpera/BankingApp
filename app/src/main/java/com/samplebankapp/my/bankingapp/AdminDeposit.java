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

public class AdminDeposit extends AppCompatActivity {

    TextView txtCurrentBalance, lblNewBal;
    EditText txtAddBal, txtAccno;
    double curBal, addBal, totBal;
    DecimalFormat df = new DecimalFormat("0.00");
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_deposit);


        df.setGroupingUsed(true);
        df.setGroupingSize(3);


    }

    public void adminDeposit(View view){

        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = sdf.format(c.getTime());

        lblNewBal = (TextView)findViewById(R.id.lblCurrentBal);
        txtCurrentBalance = (TextView)findViewById(R.id.txtCurrentAdminBal);
        txtAccno = (EditText)findViewById(R.id.txtAminCheckBalAccno);
        txtAddBal = (EditText)findViewById(R.id.txtAdminDepAmount);

        if (txtAccno.getText().toString().trim().equals("")){
            txtAccno.setError("This field can not be blank");
        }
        else if(txtAddBal.getText().toString().trim().equals("")){
            txtAddBal.setError("This field can not be blank");
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            db.findUserFromAdmin(txtAccno.getText().toString().trim());
                            if (AccountInfo.hasAccount == true) {
                                curBal = Double.parseDouble(AccountInfo.accountBalance);
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
                                    txtAccno.setText("");
                                    lblNewBal.setVisibility(View.VISIBLE);
                                    txtCurrentBalance.setVisibility(View.VISIBLE);
                                    txtCurrentBalance.setText("PHP " + df.format(totBal));
                                    Toast.makeText(getApplicationContext(), "Transaction Success!", Toast.LENGTH_SHORT).show();
                                    AccountInfo.clearInfo();
                                }
                            } else if (AccountInfo.hasAccount == false) {
                                Toast.makeText(getApplicationContext(), "Account does not exist!", Toast.LENGTH_SHORT).show();
                                txtAccno.setText("");
                                txtAddBal.setText("");
                            }


                            dialog.dismiss();
                        }
                    }

            );

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener()

                        {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                dialog.dismiss();
                            }
                        }

                );

                AlertDialog alert = builder.create();
                alert.show();
            }
        }

    public void adminDepositCancel(View view){
        Intent intent = new Intent(AdminDeposit.this, Transactions.class);
        startActivity(intent);
    }
}
