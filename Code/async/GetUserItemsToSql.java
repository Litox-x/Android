package com.example.bstufinderv2.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.helpers.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GetUserItemsToSql extends AsyncTask<Void, String, ArrayList<Item>> {
    private static final String TAG = GetItemsToSql.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "BSTUFinder_Ostapuk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    ResultSet resultSet = null;
    ArrayList<Item> resultlist;

    @Override
    protected ArrayList<Item> doInBackground(Void... params) {
        Connection connection = null;
        String ConnectionURL = null;
        resultlist = new ArrayList<Item>();
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Log.d(TAG, "Driver is registered");

            ConnectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(ConnectionURL);

            String query = "select * from Items where FounderID ="+"'"+ MainActivity.UserInfo.getInstance().id +"'";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                resultlist.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBytes(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                ));
            }
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

        return resultlist;
    }
}