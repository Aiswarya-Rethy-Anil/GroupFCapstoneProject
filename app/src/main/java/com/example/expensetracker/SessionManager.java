package com.example.expensetracker;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Login";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String id,String Name,String Email,String Phoneno,String Gender,String district,String income,String Useranme,String Password){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString("id",id);
        editor.putString("name", Name);
        editor.putString("email",Email);
        editor.putString("phoneno",Phoneno);
        editor.putString("gender",Gender);
        editor.putString("district",district);
        editor.putString("income",income);
        editor.putString("username",Useranme);
        editor.putString("password",Password);



        // commit changes
        editor.commit();
    }

    public boolean checkLogin(){
        // Check login status
        if(this.isLoggedIn()) {
            return true;
        } else {
            return false;

        }

    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put("id", pref.getString("id", null));
        user.put("name", pref.getString("name", null));

        // user email id
        user.put("email", pref.getString("email", null));
        user.put("phoneno", pref.getString("phoneno", null));
        user.put("gender", pref.getString("gender", null));
        user.put("district", pref.getString("district", null));
        user.put("income", pref.getString("income", null));
        user.put("username", pref.getString("username", null));
        user.put("password", pref.getString("password", null));



        // return user
        return user;

    }


    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }


    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
