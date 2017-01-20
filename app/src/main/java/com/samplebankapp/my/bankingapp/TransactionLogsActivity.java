package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogsActivity extends AppCompatActivity {

    DatabaseHandler db = new DatabaseHandler(this);
    DecimalFormat df = new DecimalFormat("0.00");

    ArrayList<String> ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_logs);



        ListView lv = (ListView)findViewById(R.id.logListView);
        ls = new ArrayList<String>();

        getList();

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ls);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                String selectedLog = ls.get(position);
                Toast.makeText(getApplicationContext(), "LOG: " + selectedLog, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getList(){

        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        List<TransactionLogs> logsList = db.getAllLogs();
        for (TransactionLogs lg : logsList) {
            String log = "[" + lg.getTransDateAndTime() + "] " + lg.getTransactionAccNum()
                    + " - " + lg.getTransactionType() + " - PHP " + df.format(lg.getTransactionAmount());
            // Writing Contacts to log

            Log.e("Name: ", log);
            ls.add(log);
        }
    }

    public void backToAdminHome(View view){
        Intent intent = new Intent(TransactionLogsActivity.this, HomeAdmin.class);
        startActivity(intent);
    }
}
