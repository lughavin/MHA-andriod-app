package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AddResidence extends AppCompatActivity {

    private Button addBtn;
    private EditText addressET, numOfUnitET, sizePerUnitET, monthlyRentalET;
    private String username, adderss, numOfUnit, sizePerUnit, monthlyRental;
    private StringBuffer sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_residence);
        setTitle("Set Up New Residences");


        //buying EditTexts by ID
        addressET = (EditText) findViewById(R.id.AdressET);
        numOfUnitET = (EditText) findViewById(R.id.NumOfUnitET);
        sizePerUnitET = (EditText) findViewById(R.id.SizePerUnitET);
        monthlyRentalET = (EditText) findViewById(R.id.MonthlyRentalET);
        username = getIntent().getStringExtra("intent_username");


        //buying buttons
        addBtn = (Button) findViewById(R.id.AddResidenceBtn);
        Toast.makeText(getApplicationContext(),username, Toast.LENGTH_SHORT).show();




        //On click method for signUpBtn
        addBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                adderss = addressET.getText().toString();
                numOfUnit = numOfUnitET.getText().toString();
                sizePerUnit = sizePerUnitET.getText().toString();
                monthlyRental = monthlyRentalET.getText().toString();

                    if (adderss.isEmpty() || numOfUnit.isEmpty() ||
                            sizePerUnit.isEmpty() || monthlyRental.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please, fill out all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        new AddResidence.addResidence().execute();
                    }
                }

        });
        //End of on click method
    }


    //Registering new Admin in the database.
    public class addResidence extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://" +  IPContainer.IP + "/mhs/addResidence.php"); // here is your URL path

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write("address=" + adderss + "&numOfUnit=" + numOfUnit + "&sizePerUnit=" + sizePerUnit +
                            "&monthlyRental=" + monthlyRental + "&staffID=" + username );

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
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if(sb.toString().equals("false")){
                Toast.makeText(getApplicationContext(), "Failed to create a residence", Toast.LENGTH_SHORT).show();
            }else if(sb.toString().equals("true")){
                Toast.makeText(getApplicationContext(), "Residence added successfully",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddResidence.this, HousingOfficerMenu.class);
                intent.putExtra("intent_username", username);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Sorry, we are having problems to add residence. Try again later",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    //End of registering
}
