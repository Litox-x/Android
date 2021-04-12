package com.example.bstufinderv2.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bstufinderv2.helpers.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetUserInfoToSql extends AsyncTask<Item, String, String> {
    private static final String TAG = GetUserToSql.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "BSTUFinder_Ostapuk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    ResultSet resultSet = null;
    String result="";

    @Override
    protected String doInBackground(Item... params) {
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Log.d(TAG, "Driver is registered");

            ConnectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(ConnectionURL);

            String query = "select * from Users where Id = " + "'"+params[0].getFounder_id()+"'";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            result+="Нашел: "+"\n"+resultSet.getString(2)+"\n"+resultSet.getString(3)+"\n"+resultSet.getString(4);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        return result;
    }
}