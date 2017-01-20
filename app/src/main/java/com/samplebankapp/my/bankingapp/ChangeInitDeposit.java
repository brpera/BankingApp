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

public class ChangeInitDeposit extends AppCompatActivity {
    TextView currentInit;
    EditText txtInit;
    double temp;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_init_deposit);
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        temp = Double.parseDouble(AccountInfo.initDepo);
        currentInit = (TextView)findViewById(R.id.txtCurrentInitDeposit);
        txtInit = (EditText)findViewById(R.id.txtInitDeposit);
        currentInit.setText("PHP " + df.format(temp));
        txtInit.setText(AccountInfo.initDepo);

    }




    public void changeInitialDep(View view){
        txtInit = (EditText)findViewById(R.id.txtInitDeposit);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                Intent intent = new Intent(ChangeInitDeposit.this, Maintenance.class);
                if(txtInit.getText().toString().trim().equals("")){
                    txtInit.setError("This field can not be blank");
                }
                else{
                    double init = Double.parseDouble(txtInit.getText().toString());
                    if(init < 50){
                        Toast.makeText(getApplicationContext(), "Must be PHP 50.00 and above", Toast.LENGTH_SHORT).show();
                        txtInit.setText("");
                    }
                    else if(init > 10000){
                        Toast.makeText(getApplicationContext(), "Initial deposit is to high", Toast.LENGTH_SHORT).show();
                        txtInit.setText("");
                    }
                    else{
                        AccountInfo.initDepo = txtInit.getText().toString();
                        Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
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
    public void changeInitBackToMaint(View view){
        Intent intent = new Intent(ChangeInitDeposit.this, Maintenance.class);
        startActivity(intent);
    }
}
