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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;

public class SignUp extends AppCompatActivity {


    private Button signUpBtn, dobSignUp;
    private EditText usernameET, fullNameET, passwordET, emailET, monthlyIncomeET;
    private String username, fullname, password, email, monthlyIncome;
    private CharSequence userType;
    private RadioGroup rg;
    private RadioButton rb;
    private RadioButton applicantRB;
    private RadioButton houseOfficerRB;
    private StringBuffer sb;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");


        //buying EditTexts by ID
        usernameET = (EditText) findViewById(R.id.CustUsernameET);
        fullNameET = (EditText) findViewById(R.id.CustFullNameET);
        passwordET = (EditText) findViewById(R.id.CustPasswordET);
        emailET = (EditText) findViewById(R.id.email);
        monthlyIncomeET = (EditText) findViewById(R.id.monthlyIncome);
        rg = (RadioGroup) findViewById(R.id.radioUsderType);
        applicantRB =(RadioButton) findViewById(R.id.radioApplicant);
        houseOfficerRB = (RadioButton) findViewById(R.id.radioHouseingOfficer);

        //buying buttons
        signUpBtn = (Button) findViewById(R.id.CustRegisterBtn);



        //On click method for signUpBtn
        signUpBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                username = usernameET.getText().toString();
                fullname = fullNameET.getText().toString();
                password = passwordET.getText().toString();
                email = emailET.getText().toString();
                monthlyIncome = monthlyIncomeET.getText().toString();


                int selectedId = rg.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                rb = (RadioButton) findViewById(selectedId);
                userType = rb.getText();

                if(applicantRB.isChecked()) {
                    if (username.isEmpty() || fullname.isEmpty() ||
                            password.isEmpty() || email.isEmpty() || monthlyIncome.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please, fill out all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        new SignUp.registerCustomer().execute();
                    }
                }
                else {
                    if (username.isEmpty() || fullname.isEmpty() ||
                            password.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please, fill out all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        new SignUp.registerCustomer().execute();
                    }
                }

            }
        });
        //End of on click method
    }

    public void Btn_visibilty(View view) {
        if (houseOfficerRB.isChecked()) {
            emailET.setVisibility(View.GONE);
            monthlyIncomeET.setVisibility(View.GONE);
        } else {
            emailET.setVisibility(View.VISIBLE);
            monthlyIncomeET.setVisibility(View.VISIBLE);

        }
    }



    //Registering new Admin in the database.
    public class registerCustomer extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://" +  IPContainer.IP + "/mhs/signup.php"); // here is your URL path

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                if (userType.equals("Applicant")) {
                    writer.write( "userType=" + userType +"&username=" + username + "&password=" + password + "&fullname=" + fullname +
                            "&email=" + email + "&monthlyIncome=" + monthlyIncome);
                }
                else {
                    writer.write( "userType=" + userType +"&username=" + username + "&password=" + password + "&fullname=" + fullname );
                }
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
            Log.d("Umar", sb.toString());
            if(sb.toString().equals("duplicate")){
                Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
            }else if(sb.toString().equals("false")){
                Toast.makeText(getApplicationContext(), "Failed to sign up", Toast.LENGTH_SHORT).show();
            }else if(sb.toString().equals("true")){
                Toast.makeText(getApplicationContext(), "Registered successfully",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Sorry, we are having problems to sign you in. Try again later",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    //End of registering

}