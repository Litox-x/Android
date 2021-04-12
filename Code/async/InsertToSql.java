package com.example.bstufinderv2.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.helpers.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertToSql extends AsyncTask<Item, String, Boolean> {
    private static final String TAG = InsertToSql.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "BSTUFinder_Ostapuk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    @Override
    protected Boolean doInBackground(Item... items) {
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Log.d(TAG, "Driver is registered");

            ConnectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(ConnectionURL);
            Log.d(TAG, "Driver is registered");

            PreparedStatement preparedStatement = null;

            preparedStatement = connection.prepareStatement("insert into Items(ItemName, Description, Photo, Place, FDate, FounderID) " +
                    "values(?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, items[0].getName());
            preparedStatement.setString(2, items[0].getDescription());
            preparedStatement.setBytes(3, items[0].getImage());
            preparedStatement.setString(4, items[0].getPlace());
            preparedStatement.setString(5, items[0].getDate().toString());
            preparedStatement.setInt(6, MainActivity.UserInfo.getInstance().id);

            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }

        return true;
    }
}