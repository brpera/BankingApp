package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    EditText accNum, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    public void login(View view)
    {
        Intent intent;

        accNum = (EditText)findViewById(R.id.txtID);
        pw = (EditText)findViewById(R.id.txtPassword);
        Log.e("TEST",accNum.getText().toString() + " = " + pw.getText().toString());



        if (accNum.getText().toString().trim().equals("")){
            accNum.setError("This field can not be blank");
        }
        else if (pw.getText().toString().trim().equals("")){
            pw.setError("This field can not be blank");
        }
        if(accNum.getText().toString().trim().equals("ACCT0000") && (pw.getText().toString().trim().equals("0000"))){
         //   Log.e("TEST",accNum.getText().toString() + " = " + pw.getText().toString());
            DatabaseHandler.flag = true;

            intent = new Intent(MainActivity.this, HomeAdmin.class);
            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else{
          //  Log.e("TEST","dito pumasok");
            intent = new Intent(MainActivity.this, Home.class);
            DatabaseHandler db = new DatabaseHandler(this);


            db.getloginData(accNum.getText().toString(), pw.getText().toString());
            if(DatabaseHandler.flag == false){
                Toast.makeText(getApplicationContext(), "Invalid Login credentials!", Toast.LENGTH_SHORT).show();
                accNum.setText("");
                pw.setText("");
            }
            else if(DatabaseHandler.flag == true){

                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }



        }


    }

    public void createAccount(View view)
    {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
