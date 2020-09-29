package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MakeApplication extends AppCompatActivity {

    private static final String TAG_APPLICATION = "Application";
    private Button appDateBtn;
    private TextView appDateTV, addressTV;
    Spinner spinnerFrom, spinnerTime;
    List<String> categories, branchIDs, times, intervals;
    ArrayAdapter dataAdapter, timeAdapter;

    private ListView lv;
    private String TAG = MakeApplication.class.getSimpleName();
    //Intents
    private String address, residenceID, username;
    private String branchID, bankName, branchName, staffName, date, time, interval_id, time_interval;
    private Button submitApplication;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Void getBranches;
    private StringBuffer sb;
    ArrayList<HashMap<String, String>> applicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_application);
        setTitle("Make Application");

        //Getting intents

        residenceID = getIntent().getStringExtra("intent_residenceID");
        address = getIntent().getStringExtra("intent_address");
        username = getIntent().getStringExtra("intent_username");
        applicationList = new ArrayList<>();


        appDateBtn = (Button) findViewById(R.id.appDateBtn);
        appDateTV = (TextView) findViewById(R.id.appDateTV);
        addressTV = (TextView) findViewById(R.id.applicationaddress);
        addressTV.setText(address);

        appDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MakeApplication.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                // Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                date = year + "-" + month + "-" + day;
                appDateTV.setText(date);
            }
        };

        categories = new ArrayList<String>();
        branchIDs = new ArrayList<String>();
        times = new ArrayList<String>();
        intervals = new ArrayList<String>();

        submitApplication = (Button) findViewById(R.id.submitApplication);
        submitApplication.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                /*Intent intent = new Intent(Appointment.this, SeeYou.class);
                startActivity(intent);*/
                if (date == null) {
                    Toast.makeText(getApplicationContext(), "Please select a date and time", Toast.LENGTH_LONG).show();
                } else {
                    new MakeAppointment().execute();
                }

            }
        });
    }

    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }


    private class MakeAppointment extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(IPContainer.this,"XML Data is downloading",Toast.LENGTH_LONG).show();
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://" + IPContainer.IP + "/mhs/makeApplication.php"); // here is your URL path

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                //conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));


                Log.e(TAG, "date: " + date + " residenceID: " + residenceID + " branchID: "
                        + branchID + " interval: " + interval_id);
                writer.write("residenceID=" + residenceID + "&date=" + date
                        + "&applicantID=" + username);
                writer.write("");
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    //return sb.toString();
                    return sb.toString();

                } else {
                    //return new String("false : "+responseCode);
                }
            } catch (Exception e) {
                //return new String("Exception: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Application has been made", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MakeApplication.this, ApplicantMenu.class);
           /* intent.putExtra("appointment_date", date);
            intent.putExtra("bankName", bankName);
            intent.putExtra("branchName", branchName);
            intent.putExtra("serviceName", serviceName);
            intent.putExtra("staffName", staffName);
            intent.putExtra("time_interval", time_interval);
            intent.putExtra("serviceName", serviceName);
            intent.putExtra("intent_psw", password);*/
            intent.putExtra("intent_username", username);
            startActivity(intent);
            finish();
        }
    }
}
