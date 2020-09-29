package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class HousingOfficerViewApplications extends AppCompatActivity {

        TextView tv;
        private String TAG = HousingOfficerViewApplications.class.getSimpleName();
        private ListView lv;
        private String password, username;
        private ListAdapter adapter;
        ArrayList<HashMap<String, String>> applicationList;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_housing_officer_view_applications);
            setTitle("Applications");

            applicationList = new ArrayList<>();
            lv = (ListView) findViewById(R.id.lv_applicationsHO);
            // tv = (TextView) findViewById(R.id.selectedAppointment);
            username = getIntent().getStringExtra("intent_username");

            new HousingOfficerViewApplications.GetAllApplication().execute();


        }


        private class GetAllApplication extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... arg0) {

                try {

                    URL url = new URL("http://" +  IPContainer.IP + "/mhs/getHousingOfficerApplications.php"); // here is your URL path

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));

                    writer.write("staffID=" + username);
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
                        Log.e(TAG, "Staff ID: " + username);

                        if (answer != null) {
                            try {
                                JSONObject jsonObj = new JSONObject(answer);

                                // Getting JSON Array node
                                JSONArray contacts = jsonObj.getJSONArray("manageRecord");

                                // looping through All Contacts
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);
                                    String residenceID = "Residence id: ";
                                    String requiredMonth = "Required month: ";
                                    String requiredYear = "Required year: ";
                                    residenceID += c.getString("residenceID");
                                    String applicantID = c.getString("applicantID");
                                    requiredMonth += c.getString("requiredMonth");
                                    requiredYear += c.getString("requiredYear");
                                    String numOfUnit = c.getString("numOfUnit");
                                    String monthlyRental = c.getString("monthlyRental");
                                    String monthlyIncome = c.getString("monthlyIncome");
                                    String applicationID = c.getString("applicationID");

                                    // tmp hash map for single contact
                                    HashMap<String, String> record = new HashMap<>();

                                    // adding each child node to HashMap key => value
                                    record.put("residenceID", residenceID);
                                    record.put("applicantID", applicantID);
                                    record.put("requiredMonth", requiredMonth);
                                    record.put("requiredYear", requiredYear);
                                    record.put("numOfUnit", numOfUnit);
                                    record.put("monthlyRental", monthlyRental);
                                    record.put("monthlyIncome", monthlyIncome);
                                    record.put("applicationID", applicationID);


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
                adapter = new SimpleAdapter(HousingOfficerViewApplications.this, applicationList,
                        R.layout.ho_application_list_item, new String[]{"residenceID", "requiredMonth","requiredYear"},
                        new int[]{R.id.residenceID, R.id.requiredMonth, R.id.requiredYear});
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                        HashMap<String,String> map =(HashMap<String,String>)lv.getItemAtPosition(position);
                        String residenceID = map.get("residenceID");
                        residenceID = residenceID.replace("Residence id:", "" );
                        String applicantID = map.get("applicantID");
                        String requiredMonth = map.get("requiredMonth");
                        requiredMonth = requiredMonth.replace("Required month:", "" );
                        String requiredYear = map.get("requiredYear");
                        requiredYear = requiredYear.replace("Required year:", "");
                        String numOfUnit = map.get("numOfUnit");
                        String monthlyRental = map.get("monthlyRental");
                        String monthlyIncome = map.get("monthlyIncome");
                        String applicationID = map.get("applicationID");

                        Intent intent = new Intent(HousingOfficerViewApplications.this, AllocationDetails.class);
                        intent.putExtra("residenceID", residenceID);
                        intent.putExtra("applicantID", applicantID);
                        intent.putExtra("numOfUnit", numOfUnit);
                        intent.putExtra("requiredMonth", requiredMonth);
                        intent.putExtra("requiredYear", requiredYear);
                        intent.putExtra("intent_username", username);
                        intent.putExtra("monthlyRental", monthlyRental);
                        intent.putExtra("monthlyIncome", monthlyIncome);
                        intent.putExtra("applicationID", applicationID);
                        startActivity(intent);
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

