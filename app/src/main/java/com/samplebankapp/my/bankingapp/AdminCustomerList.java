package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdminCustomerList extends AppCompatActivity {

    DatabaseHandler db = new DatabaseHandler(this);
    DecimalFormat df = new DecimalFormat("0.00");


    ArrayList<String> ls, custId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_customer_list);

        ListView lv = (ListView)findViewById(R.id.custListView);
        ls = new ArrayList<String>();
        custId = new ArrayList<String>();
        getList();

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ls);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
//
                String selectedLog = custId.get(position);
                Intent objIndent = new Intent(getApplicationContext(),CustomerDetails.class);
                objIndent.putExtra("txtAccNo", selectedLog);
                startActivity(objIndent);
//
//                Log.e("TEST", Integer.toString(position));
            }
        });
    }

    void getList(){

        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        List<Customer> custList = db.getAllCustomers();
        //int i = 0;
        for (Customer lg : custList) {
            String log = "[" + lg.getAccNum() + "] " + lg.getLname() + ", " + lg.getFname() + " " + lg.getMname();
            String id = lg.getAccNum();
//            AccountInfo.clearInfo();
//            AccountInfo.accountID = lg.getAccNum();
//            AccountInfo.firstName = lg.getFname();
//            AccountInfo.middleName = lg.getMname();
//            AccountInfo.lastName = lg.getLname();
//            AccountInfo.currentAddress = lg.getAddress();
//            AccountInfo.contactNo = lg.getContact();
//            AccountInfo.pass = lg.getPassword();
            // Writing Contacts to log

            Log.e("Name: ", log);
            ls.add(log);
            custId.add(id);
        }


    }

    public void backToMaintFromCustList(View view){
        Intent intent = new Intent(AdminCustomerList.this, Maintenance.class);
        startActivity(intent);
    }
}
