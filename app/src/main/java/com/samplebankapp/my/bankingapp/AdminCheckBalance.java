package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class AdminCheckBalance extends AppCompatActivity {

    TextView txtCurrentBalance, lblCurBal;
    EditText txtAccno;
    double curBal;
    DecimalFormat df = new DecimalFormat("0.00");
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_balance);

        df.setGroupingUsed(true);
        df.setGroupingSize(3);
    }

    public void adminCheckBal(View view){
        txtAccno = (EditText)findViewById(R.id.txtAminCheckBalAccno);
        txtCurrentBalance = (TextView)findViewById(R.id.txtCurBal);
        lblCurBal = (TextView)findViewById(R.id.lblCurBal);

        if (txtAccno.getText().toString().trim().equals("")){
            txtAccno.setError("This field can not be blank");
        }
        else{
            db.findUserFromAdmin(txtAccno.getText().toString().trim());
            if (AccountInfo.hasAccount == true) {
                curBal = Double.parseDouble(AccountInfo.accountBalance);
                lblCurBal.setVisibility(View.VISIBLE);
                txtCurrentBalance.setVisibility(View.VISIBLE);
                txtCurrentBalance.setText("PHP " + df.format(curBal));
            }
            else if(AccountInfo.hasAccount == false){
                txtAccno.setText("");
                Toast.makeText(getApplicationContext(), "Account does not exist!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cancelCheckBal(View view){
        Intent intent = new Intent(AdminCheckBalance.this, Transactions.class);
        startActivity(intent);
    }
}
