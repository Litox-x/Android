package com.example.bstufinderv2.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bstufinderv2.helpers.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DeleteItemToSql extends AsyncTask<Item, String, Boolean> {
    private static final String TAG = GetItemsToSql.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "BSTUFinder_Ostapuk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    ResultSet resultSet = null;
    ArrayList<Item> resultlist;

    @Override
    protected Boolean doInBackground(Item... params) {
        Connection connection = null;
        String ConnectionURL = null;
        resultlist = new ArrayList<Item>();
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Log.d(TAG, "Driver is registered");

            ConnectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(ConnectionURL);

            String query = "delete from Items where Id = "+"'"+params[0].getId()+"'";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        return true;
    }
}