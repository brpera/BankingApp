package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Maintenance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maintenance);
    }

    public void showAddCust(View view){
        Intent intent = new Intent(Maintenance.this, AddCustomer.class);
        startActivity(intent);
    }

    public void showSearchToEdit(View view){
        Intent intent = new Intent(Maintenance.this, AdminFindUserToEdit.class);
        startActivity(intent);
    }

    public void showDelete(View view){
        Intent intent = new Intent(Maintenance.this, DeleteActivity.class);
        startActivity(intent);
    }

    public void showCustList(View view){
        Intent intent = new Intent(Maintenance.this, AdminCustomerList.class);
        startActivity(intent);
    }

    public void showChangeInit(View view){
        Intent intent = new Intent(Maintenance.this, ChangeInitDeposit.class);
        startActivity(intent);
    }

    public void backToHomeFromMaint(View view){
        Intent intent = new Intent(Maintenance.this, HomeAdmin.class);
        startActivity(intent);
    }





}
