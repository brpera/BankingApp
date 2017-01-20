package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminFindUserToEdit extends AppCompatActivity {

    EditText accNo;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_find_user_to_edit);
    }

    public void userSearch(View view){
        Intent intent = new Intent(AdminFindUserToEdit.this, EditCustomer.class);
        accNo = (EditText)findViewById(R.id.txtSearchAccNo);
        db.findUserFromAdmin(accNo.getText().toString().trim());
        if (AccountInfo.hasAccount == true) {
            startActivity(intent);
        }
        else if(AccountInfo.hasAccount == false){
            accNo.setText("");
            Toast.makeText(getApplicationContext(), "Account does not exist!", Toast.LENGTH_SHORT).show();
        }


    }

    public void cancelSearch(View view){
        Intent intent = new Intent(AdminFindUserToEdit.this, Maintenance.class);
        startActivity(intent);
    }
}
