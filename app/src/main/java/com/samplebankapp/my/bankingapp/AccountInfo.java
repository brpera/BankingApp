package com.samplebankapp.my.bankingapp;

/**
 * Created by lenovo on 11/22/2016.
 */
public class AccountInfo {
    public static String accountID;
    public static String firstName;
    public static String middleName;
    public static String lastName;
    public static String accountBalance;
    public static String currentAddress;
    public static String contactNo;
    public static String pass;
    public static String fromOtherAccount;
    public static Boolean hasAccount = false;
    public static String initDepo = "200";

    public static void clearInfo(){
        accountID = "";
        firstName = "";
        middleName = "";
        lastName = "";
        accountBalance = "";
        currentAddress = "";
        contactNo = "";
        pass = "";
        fromOtherAccount = "";
    }
}
