package com.samplebankapp.my.bankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 11/22/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static boolean flag = false;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbBankingApp";

    private static final String TABLE_CUSTOMERS = "tblCustomers";
    private static final String TABLE_SAVINGS = "tblSavings";
    private static final String TABLE_TRANSACTION_LOGS = "tblLogs";

    private static final String KEY_ID = "strID";
    private static final String KEY_ACCNUM = "strAccountNum";

    private static final String KEY_FNAME = "strFname";
    private static final String KEY_MNAME = "strMname";
    private static final String KEY_LNAME = "strLname";
    private static final String KEY_ADDRESS = "strAddress";
    private static final String KEY_CONTACT = "strContactNo";
    private static final String KEY_PASSWORD = "strPassword";

    private static final String KEY_SAVED_AMOUNT = "strSavedAmount";

    private static final String KEY_TRANSACTION_TYPE = "strTransactionType";
    private static final String KEY_TRANSACTION_AMOUNT = "strTransactionAmount";
    private static final String KEY_TRANSACTION_DATETIME = "strTransactionDtm";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_ACCNUM +  " TEXT, " + KEY_FNAME + " TEXT, "
                + KEY_MNAME + " TEXT, " + KEY_LNAME + " TEXT, "
                + KEY_ADDRESS + " TEXT, " + KEY_CONTACT + " TEXT, "
                + KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_CUSTOMER_TABLE);


        String CREATE_SAVINGS_TABLE = "CREATE TABLE " + TABLE_SAVINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_ACCNUM +  " TEXT, "
                + KEY_SAVED_AMOUNT + " REAL" + ")";
        db.execSQL(CREATE_SAVINGS_TABLE);

        String CREATE_LOGS_TABLE = "CREATE TABLE " + TABLE_TRANSACTION_LOGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_TRANSACTION_TYPE + " TEXT, "
                + KEY_ACCNUM + " TEXT, "
                + KEY_TRANSACTION_AMOUNT + " REAl, "
                + KEY_TRANSACTION_DATETIME + " TEXT" + ")";


        db.execSQL(CREATE_LOGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SAVINGS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_TRANSACTION_LOGS);

        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    void addCustomer(Customer customer){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ACCNUM, customer.getAccNum());
        values.put(KEY_FNAME, customer.getFname());
        values.put(KEY_MNAME, customer.getMname());
        values.put(KEY_LNAME, customer.getLname());
        values.put(KEY_ADDRESS, customer.getAddress());
        values.put(KEY_CONTACT, customer.getContact());
        values.put(KEY_PASSWORD, customer.getPassword());

        db.insert(TABLE_CUSTOMERS, null, values);
        db.close();
    }

    void addSavings(Savings savings){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ACCNUM, savings.getAccountNum());
        values.put(KEY_SAVED_AMOUNT, savings.getSavingsAmount());

        db.insert(TABLE_SAVINGS, null, values);
        db.close();
    }

    void addLogs(TransactionLogs tLogs){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TRANSACTION_TYPE, tLogs.getTransactionType());
        values.put(KEY_ACCNUM, tLogs.getTransactionAccNum());
        values.put(KEY_TRANSACTION_AMOUNT, tLogs.getTransactionAmount());
        values.put(KEY_TRANSACTION_DATETIME, tLogs.getTransDateAndTime());

        db.insert(TABLE_TRANSACTION_LOGS, null, values);
        db.close();
    }

    public boolean updateCustomer(Customer customer){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ACCNUM, customer.getAccNum());
        values.put(KEY_FNAME, customer.getFname());
        values.put(KEY_MNAME, customer.getMname());
        values.put(KEY_LNAME, customer.getLname());
        values.put(KEY_ADDRESS, customer.getAddress());
        values.put(KEY_CONTACT, customer.getContact());
        values.put(KEY_PASSWORD, customer.getPassword());

        //Log.e("STRING VALUEOF", String.valueOf(customer.getAccNum()));

        int i =  db.update(TABLE_CUSTOMERS, values, KEY_ACCNUM + " = ?",
                new String[] { String.valueOf(customer.getAccNum()) });
       //Log.e("STRING VALUEOF", Integer.toString(i));
        return i > 0;

    }



//    public boolean updateSavings(Savings savings){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_ACCNUM, savings.getAccountNum());
//        values.put(KEY_SAVED_AMOUNT, savings.getSavingsAmount());
//
////        Log.e("STRING VALUEOF", String.valueOf(savings.getAccountNum()));
////        Log.e("STRING VALUEOF", String.valueOf(savings.getSavingsAmount()));
//
//        int i =  db.update(TABLE_SAVINGS, values, KEY_ACCNUM + " = ?",
//                new String[] { String.valueOf(savings.getAccountNum()) });
//        Log.e("STRING VALUEOF", String.valueOf(i));
//
//        return i > 0;
//
//    }

    public boolean updateSavings(String id, double amount){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ACCNUM, id);
        values.put(KEY_SAVED_AMOUNT, amount);

        Log.e("STRING VALUEOF", String.valueOf(id));
        Log.e("STRING VALUEOF", String.valueOf(amount));

        int i =  db.update(TABLE_SAVINGS, values, KEY_ACCNUM + " = ?",
                new String[] { id });

        return i > 0;
    }

    public void deleteCust(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("DELETE", "pumasok dito");
        db.delete(TABLE_CUSTOMERS, KEY_ACCNUM + " = '" + id + "'", null);
        deleteSavings(id);



    }

    void deleteSavings(String id){

        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_SAVINGS, KEY_ACCNUM + " = '" + id + "'", null);
        deleteTransactions(id);




    }

    void deleteTransactions(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_TRANSACTION_LOGS, KEY_ACCNUM + " = '" + id + "'", null);
        DeleteDetails.isDeleted = true;

    }

    // Getting All Customer
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<Customer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setID(Integer.parseInt(cursor.getString(0)));
                customer.setAccNum(cursor.getString(1));
                customer.setFname(cursor.getString(2));
                customer.setMname(cursor.getString(3));
                customer.setLname(cursor.getString(4));
                customer.setAddress(cursor.getString(5));
                customer.setContact(cursor.getString(6));
                customer.setPassword(cursor.getString(7));
                // Adding contact to list
                customerList.add(customer);
            } while (cursor.moveToNext());
        }

        // return contact list
        return customerList;
    }

    public List<Savings> getAllSavings() {
        List<Savings> customerList = new ArrayList<Savings>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SAVINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Savings customer = new Savings();
                customer.setId(Integer.parseInt(cursor.getString(0)));
                customer.setAccountNum(cursor.getString(1));
                customer.setSaveAmount(Double.parseDouble(cursor.getString(2)));
                // Adding contact to list
                customerList.add(customer);
            } while (cursor.moveToNext());
        }

        // return contact list
        return customerList;
    }

    public List<TransactionLogs> getAllLogs() {
        List<TransactionLogs> logList = new ArrayList<TransactionLogs>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTION_LOGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TransactionLogs logs = new TransactionLogs();
                logs.setTransactionId(Integer.parseInt(cursor.getString(0)));
                logs.setTransactionType(cursor.getString(1));
                logs.setTransactionAccNum(cursor.getString(2));
                logs.setTransactionAmount(Double.parseDouble(cursor.getString(3)));
                logs.setTransDateAndTime(cursor.getString(4));
                // Adding contact to list
                logList.add(logs);
            } while (cursor.moveToNext());
        }

        // return contact list
        return logList;
    }

    //GET CUSTOMERS COUNT
    public int getCustomersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CUSTOMERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        // return count
        return cursor.getCount();
    }

    public void getloginData(String id, String pw){
        String selectQuery = "SELECT " + TABLE_CUSTOMERS + ".*, " + TABLE_SAVINGS + "." +KEY_SAVED_AMOUNT
                + " FROM " + TABLE_CUSTOMERS + ", " + TABLE_SAVINGS
                + " WHERE (" + TABLE_CUSTOMERS + "." + KEY_ACCNUM + " = '" +  id
                + "' AND " + TABLE_SAVINGS + "." + KEY_ACCNUM + " = '" +  id
                + "') AND " + TABLE_CUSTOMERS + "." + KEY_PASSWORD + " = '" + pw + "'";
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                AccountInfo.accountID = cursor.getString(1);
                AccountInfo.firstName = cursor.getString(2);
                AccountInfo.middleName = cursor.getString(3);
                AccountInfo.lastName = cursor.getString(4);
                AccountInfo.currentAddress = cursor.getString(5);
                AccountInfo.contactNo = cursor.getString(6);
                AccountInfo.pass = cursor.getString(7);
                AccountInfo.accountBalance = cursor.getString(8);

                Log.e("LOGIN", AccountInfo.accountID);
                Log.e("LOGIN", AccountInfo.accountBalance);

                flag = true;

            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    void findUser(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + KEY_SAVED_AMOUNT + " FROM " + TABLE_SAVINGS
                + " WHERE " + KEY_ACCNUM + " = '" + id + "' ", null);
        if(c.moveToFirst()){
            do{
                AccountInfo.fromOtherAccount = c.getString(0);
                Log.e("OTHER", AccountInfo.fromOtherAccount);

            }while(c.moveToNext());
            AccountInfo.hasAccount = true;
        }
    }

    void findUserFromAdmin(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + TABLE_CUSTOMERS + ".*, " + TABLE_SAVINGS + "." +KEY_SAVED_AMOUNT
                + " FROM " + TABLE_CUSTOMERS + ", " + TABLE_SAVINGS
                + " WHERE " + TABLE_CUSTOMERS + "." + KEY_ACCNUM + " = '" +  id
                + "' AND " + TABLE_SAVINGS + "." + KEY_ACCNUM + " = '" +  id
                + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                AccountInfo.accountID = c.getString(1);
                AccountInfo.firstName = c.getString(2);
                AccountInfo.middleName = c.getString(3);
                AccountInfo.lastName = c.getString(4);
                AccountInfo.currentAddress = c.getString(5);
                AccountInfo.contactNo = c.getString(6);
                AccountInfo.pass = c.getString(7);
                AccountInfo.accountBalance = c.getString(8);
                Log.e("SENDER", AccountInfo.accountID + " BALANCE: " + AccountInfo.accountBalance);
            }while(c.moveToNext());
            AccountInfo.hasAccount = true;
        }
    }

}
