package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class ApplicantViewApplications extends AppCompatActivity {


    TextView tv;
    private String TAG = ApplicantViewApplications.class.getSimpleName();
    private ListView lv;
    private String password, username;
    private ListAdapter adapter;
    AlertDialog.Builder alert;

    ArrayList<HashMap<String, String>> applicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_view_applications);
        setTitle("Applications");

        applicationList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lv_applicationsAP);
        // tv = (TextView) findViewById(R.id.selectedAppointment);
        username = getIntent().getStringExtra("intent_username");

        new ApplicantViewApplications.GetAllApplication().execute();


    }


    private class GetAllApplication extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                URL url = new URL("http://" +  IPContainer.IP + "/mhs/getApplicantApplications2.php"); // here is your URL path

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                writer.write("applicantID=" + username);
                writer.flush();
                writer.close();
                os.close();


                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    String answer = sb.toString();

                    Log.e(TAG, "Response from url: " + answer);

                    if (answer != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(answer);

                            // Getting JSON Array node
                            JSONArray contacts = jsonObj.getJSONArray("manageRecord");

                            // looping through All Contacts
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);
                                String residenceID = "Residence id: ";
                                String status = "Status: ";
                                String numOfUnit = "Number of units: ";
                                String monthlyRental = "Monthly rental: ";
                                //String address=*/
                                residenceID += c.getString("residenceID");
                                status += c.getString("status");
                                numOfUnit += c.getString("numOfUnit");
                                //address = c.getString("address");
                                monthlyRental += c.getString("monthlyRental");

                                // tmp hash map for single contact
                                HashMap<String, String> record = new HashMap<>();

                                // adding each child node to HashMap key => value
                                record.put("residenceID", residenceID);
                                record.put("status", status);
                                record.put("numOfUnit", numOfUnit);
                                //record.put("address", address);
                                record.put("monthlyRental", monthlyRental);


                                // adding contact to contact list
                                applicationList.add(record);
                            }
                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    } else {
                        Log.e(TAG, "Couldn't get json from server.");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Couldn't get json from server. Check LogCat for possible errors!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    // return new String("false : "+responseCode);
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Cannot connect to php file",
                        Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new SimpleAdapter(ApplicantViewApplications.this, applicationList,
                    R.layout.application_list_item, new String[]{"residenceID","numOfUnit","monthlyRental","status",},
                    new int[]{R.id.residenceID, R.id.numOfUnit, R.id.monthlyRental, R.id.status});
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                  /*  HashMap<String,String> map =(HashMap<String,String>)lv.getItemAtPosition(position);
                    String residenceID = map.get("residenceID");
                    String address = map.get("address");
                    String numOfUnit = map.get("numOfUnit");
                    String sizePerUnit = map.get("sizePerUnit");
                    String monthlyRental = map.get("monthlyRental");

                    Intent intent = new Intent(ApplicantViewApplications.this, UpdateResidence.class);
                    intent.putExtra("residenceID", residenceID);
                    intent.putExtra("address", address);
                    intent.putExtra("numOfUnit", numOfUnit);
                    intent.putExtra("sizePerUnit", sizePerUnit);
                    intent.putExtra("monthlyRental", monthlyRental);
                    intent.putExtra("intent_username", username);
                    intent.putExtra("intent_psw", password);
                    startActivity(intent);*/
                }
            });

                }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}

