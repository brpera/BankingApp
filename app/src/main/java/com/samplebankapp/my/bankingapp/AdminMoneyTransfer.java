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

public class AdminMoneyTransfer extends AppCompatActivity {

    TextView txtCurrentBalance, lblNewBal;
    EditText txtAmt, txtAccnoSender, txtAccnoReceiver;
    double curBal, subBal, totBal, otherTotal;
    DecimalFormat df = new DecimalFormat("0.00");
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_money_transfer);

        df.setGroupingUsed(true);
        df.setGroupingSize(3);
    }

    public void adminMoneyTrans(View view){
        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = sdf.format(c.getTime());

        lblNewBal = (TextView)findViewById(R.id.lblCurrentBal);
        txtCurrentBalance = (TextView)findViewById(R.id.txtCurrentAdminBal);
        txtAccnoSender = (EditText)findViewById(R.id.txtAdminMTAccno);
        txtAccnoReceiver = (EditText)findViewById(R.id.txtAdminMTAccnoReceiver);
        txtAmt = (EditText)findViewById(R.id.txtAdminMTAmount);

        if (txtAccnoSender.getText().toString().trim().equals("")){
            txtAccnoSender.setError("This field can not be blank");
        }
        else if(txtAccnoReceiver.getText().toString().trim().equals("")){
            txtAccnoReceiver.setError("This field can not be blank");
        }
        else if(txtAmt.getText().toString().trim().equals("")){
            txtAmt.setError("This field can not be blank");
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog

                    subBal = Double.parseDouble(txtAmt.getText().toString());

                    db.findUserFromAdmin(txtAccnoSender.getText().toString().trim());
                    if (AccountInfo.hasAccount == true) {
                        curBal = Double.parseDouble(AccountInfo.accountBalance);
                        AccountInfo.hasAccount = false;

                        db.findUser(txtAccnoReceiver.getText().toString().trim());
                        if (AccountInfo.hasAccount == true) {
                            double fromOtherAcc = Double.parseDouble(AccountInfo.fromOtherAccount);
                            otherTotal = fromOtherAcc + subBal;
                            if (subBal < 200) {
                                Toast.makeText(getApplicationContext(), "Must be greater than or equal to PHP 200.00!", Toast.LENGTH_SHORT).show();
                                txtAmt.setText("");
                            } else if (subBal > 10000) {
                                Toast.makeText(getApplicationContext(), "Must be less than or equal to PHP 10, 000.00!", Toast.LENGTH_SHORT).show();
                                txtAmt.setText("");
                            } else if (curBal <= 100 || curBal < subBal) {
                                Toast.makeText(getApplicationContext(), "Insufficient Balance!", Toast.LENGTH_SHORT).show();
                                txtAmt.setText("");
                            } else {
                                totBal = curBal - subBal;
                                if (totBal == 0) {
                                    Toast.makeText(getApplicationContext(), "Must have maintaining balance of PHP 50.00!", Toast.LENGTH_SHORT).show();
                                    txtAmt.setText("");
                                } else if (totBal == 50 && subBal < totBal) {
                                    Toast.makeText(getApplicationContext(), "Must have maintaining balance of PHP 50.00!", Toast.LENGTH_SHORT).show();
                                    txtAmt.setText("");
                                } else {
                                    db.updateSavings(AccountInfo.accountID, totBal);
                                    db.updateSavings(txtAccnoReceiver.getText().toString(), otherTotal);
                                    db.addLogs(new TransactionLogs("MONEY TRANSFER to " + txtAccnoReceiver.getText().toString(), AccountInfo.accountID, subBal, formattedDate));
                                    db.addLogs(new TransactionLogs("MONEY TRANSFER from " + AccountInfo.accountID, txtAccnoReceiver.getText().toString(), subBal, formattedDate));
                                    AccountInfo.accountBalance = Double.toString(totBal);
                                    txtAmt.setText("");
                                    txtAccnoReceiver.setText("");
                                    txtAccnoSender.setText("");
                                    lblNewBal.setVisibility(View.VISIBLE);
                                    txtCurrentBalance.setVisibility(View.VISIBLE);
                                    txtCurrentBalance.setText("PHP" + df.format(totBal));
                                    Toast.makeText(getApplicationContext(), "Transaction Success!", Toast.LENGTH_SHORT).show();
                                    AccountInfo.clearInfo();
                                }

                            }
                        } else if (AccountInfo.hasAccount == false) {
                            Toast.makeText(getApplicationContext(), "Receiver Account does not exist!", Toast.LENGTH_SHORT).show();
                            txtAccnoReceiver.setText("");
                            txtAmt.setText("");
                        }


                    } else if (AccountInfo.hasAccount == false) {
                        Toast.makeText(getApplicationContext(), "Sender Account does not exist!", Toast.LENGTH_SHORT).show();
                        txtAccnoSender.setText("");
                        txtAccnoReceiver.setText("");
                        txtAmt.setText("");
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

    public void backToTransFromMT(View view){
        Intent intent = new Intent(AdminMoneyTransfer.this, Transactions.class);
        startActivity(intent);
    }
}
