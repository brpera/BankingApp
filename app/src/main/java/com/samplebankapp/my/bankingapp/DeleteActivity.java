package com.samplebankapp.my.bankingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {

    DatabaseHandler db = new DatabaseHandler(this);
    DecimalFormat df = new DecimalFormat("0.00");


    ArrayList<String> ls, custId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        ListView lv = (ListView)findViewById(R.id.deleteCustList);
        ls = new ArrayList<String>();
        custId = new ArrayList<String>();
        getList();

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ls);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
//
                String selectedLog = custId.get(position);
                Intent objIndent = new Intent(getApplicationContext(), DeleteDetails.class);
                objIndent.putExtra("txtAccNo", selectedLog);
                startActivity(objIndent);
//
//                Log.e("TEST", Integer.toString(position));
            }
        });
    }

    void getList() {

        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        List<Customer> custList = db.getAllCustomers();
        //int i = 0;
        for (Customer lg : custList) {
            String log = "[" + lg.getAccNum() + "] " + lg.getLname() + ", " + lg.getFname() + " " + lg.getMname();
            String id = lg.getAccNum();

            ls.add(log);
            custId.add(id);
        }
    }

    public void backToMaintFromDeleteActivity(View view){
        Intent intent = new Intent(DeleteActivity.this, Maintenance.class);
        startActivity(intent);
    }

}
