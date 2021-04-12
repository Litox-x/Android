package com.example.bstufinderv2.async;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetUserToSql extends AsyncTask<String, String, ResultSet> {
    private static final String TAG = GetUserToSql.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "BSTUFinder_Ostapuk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    ResultSet resultSet = null;

    @Override
    protected ResultSet doInBackground(String... params) {
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Log.d(TAG, "Driver is registered");

            ConnectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
                connection = DriverManager.getConnection(ConnectionURL);

            String query = "select * from Users where Pass = " + "'"+params[1]+"'"+" and Email = "+"'"+params[0]+"'";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
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

        return resultSet;
    }
}
