package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCustomer extends AppCompatActivity {

    EditText fname, mname, lname, address, contact, pw, confpw;
    TextView accID, initDepo;
    Button btn;
    int custCount;
    String prefix;

    DecimalFormat df = new DecimalFormat("0.00");
    double temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        smartCounter();
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        temp = Double.parseDouble(AccountInfo.initDepo);
        initDepo = (TextView)findViewById(R.id.textView12Admin);
        initDepo.setText("PHP " + df.format(temp));
    }

    void smartCounter(){
        DatabaseHandler dbh = new DatabaseHandler(this);
        custCount = dbh.getCustomersCount();
        int prefixNum = 726997 + custCount;
        prefix = "ACCT" + Integer.toString(prefixNum);
        accID = (TextView)findViewById(R.id.txtAdminAccID);
        accID.setText(prefix);
    }

    public void addCust(View view){
        fname = (EditText)findViewById(R.id.txtAdminFirstName);
        mname = (EditText)findViewById(R.id.txtAdminMidName);
        lname = (EditText)findViewById(R.id.txtAdminLastName);
        address = (EditText)findViewById(R.id.txtAdminAddress);
        contact = (EditText)findViewById(R.id.txtAdminContactNo);
        pw = (EditText)findViewById(R.id.txtAdminPassword);
        accID = (TextView)findViewById(R.id.txtAdminAccID);
        initDepo = (TextView)findViewById(R.id.textView12Admin);
        confpw = (EditText) findViewById(R.id.txtAdminConfirm);

        if (!pw.getText().toString().trim().equals(confpw.getText().toString().trim())){
            pw.setText("");
            confpw.setText("");
            Toast.makeText(getApplicationContext(), "Passwords didn't match!", Toast.LENGTH_SHORT).show();
        }
        else if (fname.getText().toString().trim().equals("")){
            fname.setError("This field can not be blank");
        }
        else if (lname.getText().toString().trim().equals("")){
            lname.setError("This field can not be blank");
        }
        else if (address.getText().toString().trim().equals("")){
            address.setError("This field can not be blank");
        }
        else if (contact.getText().toString().trim().equals("")){
            contact.setError("This field can not be blank");
        }
        else if (pw.getText().toString().trim().equals("")){
            pw.setError("This field can not be blank");
        }
        else{
            Intent intent = new Intent(AddCustomer.this, Maintenance.class);
            DatabaseHandler dbh = new DatabaseHandler(this);

            Calendar c = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String formattedDate = sdf.format(c.getTime());

            String[] part = initDepo.getText().toString().split("(?<=\\D)(?=\\d)");
            double initDeposit = Double.parseDouble(part[1]);

            dbh.addCustomer(new Customer(accID.getText().toString(), fname.getText().toString(),
                    mname.getText().toString(), lname.getText().toString(),
                    address.getText().toString(), contact.getText().toString(),
                    pw.getText().toString()));

            dbh.addSavings(new Savings(accID.getText().toString(), initDeposit));

            dbh.addLogs(new TransactionLogs("NEW ACCOUNT CREATED: " + lname.getText().toString()
                    + ", " + fname.getText().toString() + " " + mname.getText().toString() + " INITBAL: ", accID.getText().toString(),  initDeposit, formattedDate));

//            AccountInfo.accountID = accID.getText().toString();
//            AccountInfo.firstName = fname.getText().toString();
//            AccountInfo.middleName = mname.getText().toString();
//            AccountInfo.lastName = lname.getText().toString();
//            AccountInfo.currentAddress = address.getText().toString();
//            AccountInfo.contactNo = contact.getText().toString();
//            AccountInfo.pass = pw.getText().toString();
//            AccountInfo.accountBalance = Double.toString(initDeposit);

//            Log.e("Reading: ", "Reading all contacts..");
//            List<Savings> contacts = dbh.getAllSavings();
//
//            for (Savings cn : contacts) {
//                String log = "Id: " + cn.getSavingsIdId() + " ,accnum: " + cn.getAccountNum() + " ,amount: " + cn.getSavingsAmount();
//                // Writing Contacts to log
//                Log.e("Name: ", log);
//            }

            Toast.makeText(getApplicationContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    public void cancelAddCust(View view){
        Intent intent = new Intent(AddCustomer.this, Maintenance.class);
        startActivity(intent);
    }
}
