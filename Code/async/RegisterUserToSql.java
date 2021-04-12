package com.example.bstufinderv2.async;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.bstufinderv2.MainActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterUserToSql extends AsyncTask<String, String, Boolean> {
    private static final String TAG = RegisterUserToSql.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "BSTUFinder_Ostapuk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";


    @Override
    protected Boolean doInBackground(String... voids) {
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            Log.d(TAG, "Driver is registered");

            ConnectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            try {

                connection = DriverManager.getConnection(ConnectionURL);
            }
            catch (Exception c){
                Log.d("exception","Check ur connection");
            }
            Log.d(TAG, "Driver is registered");

            PreparedStatement preparedStatement = null;

            preparedStatement = connection.prepareStatement("insert into Users(Name, Email, Phone,Pass,Role) " +
                    "values(?, ?, ?, ?, ?)");

            preparedStatement.setString(1, voids[0]);
            preparedStatement.setString(2, voids[1]);
            preparedStatement.setString(3, voids[2]);
            preparedStatement.setString(4, voids[3]);
            preparedStatement.setInt(5, 0);

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
        catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return true;
    }
}
