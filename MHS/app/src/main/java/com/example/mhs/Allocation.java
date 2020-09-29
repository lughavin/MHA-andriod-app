package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Allocation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView ivStatus, ivDuration;
    private TextView tvStatus,tvDuration, appDateTV;
    private String username, date, unitNo, residenceID, applicationID, applicantID;
    private CharSequence status, duration;
    private Button appDateBtn, allocatebtn;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Spinner units;
    private Void getUnits;
    ArrayAdapter unitAdapter;
    List<String> unitNos;
    private StringBuffer sb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation);
        setTitle("Applications");


        username = getIntent().getStringExtra("intent_username");
        residenceID = getIntent().getStringExtra("residenceID");
        applicationID = getIntent().getStringExtra("applicationID");
        applicantID = getIntent().getStringExtra("applicantID");

        status = "";
        duration = "";
        date = "";

        ivStatus = (ImageView) findViewById(R.id.status);
        ivDuration = (ImageView) findViewById(R.id.duration);


        appDateBtn = (Button) findViewById(R.id.appDateBtn);
        appDateTV = (TextView) findViewById(R.id.appDateTV);

        allocatebtn = (Button) findViewById(R.id.allocatebtn);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvDuration = (TextView) findViewById(R.id.tvDuration);

        unitNos = new ArrayList<String>();
        units = (Spinner) findViewById(R.id.unitSpinner);


        ivStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Allocation.this, ivStatus);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        status = item.getTitle();
                        tvStatus.setText(status);
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        ivDuration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Allocation.this, ivDuration);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        duration = item.getTitle();
                        tvDuration.setText(duration);
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        appDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Allocation.this,
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

        allocatebtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                /*Intent intent = new Intent(Appointment.this, SeeYou.class);
                startActivity(intent);*/
                if (status.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please select a status", Toast.LENGTH_LONG).show();
                } else {
                    if (status.equals("Rejected")) {
                        new AlertDialog.Builder(Allocation.this)
                                .setTitle("Reject application")
                                .setMessage("Do you really want to reject?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(Allocation.this, "Rejected", Toast.LENGTH_SHORT).show();
                                        new Allocation.makeAllocation().execute();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                    else if (status.equals("Approved")){
                        if (date.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please select a date", Toast.LENGTH_LONG).show();
                        }
                        else if (duration.equals("")) {
                            Toast.makeText(getApplicationContext(), "Please select a duration", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(Allocation.this, status, Toast.LENGTH_SHORT).show();
                            new Allocation.makeAllocation().execute();

                        }
                    }
                    else {
                        Toast.makeText(Allocation.this, status, Toast.LENGTH_SHORT).show();
                        new Allocation.makeAllocation().execute();
                    }
                }

            }
        });

        units.setOnItemSelectedListener(this);

        try {
            getUnits = new GetUnits().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int selectedUnitNo = parent.getSelectedItemPosition();
            unitNo = unitNos.get(selectedUnitNo);
            Toast.makeText(getApplicationContext(), unitNo, Toast.LENGTH_LONG).show();
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class GetUnits extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(IPContainer.this,"XML Data is downloading",Toast.LENGTH_LONG).show();
        }


        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                URL url = new URL("http://" + IPContainer.IP + "/mhs/getUnits.php"); // here is your URL path

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

                writer.write("residenceID=" + residenceID);
                writer.write("");
                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    try {
                        InputStream is = conn.getInputStream();

                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(is);

                        Element element=doc.getDocumentElement();
                        element.normalize();

                        NodeList nList = doc.getElementsByTagName("unit");

                        for (int i=0; i<nList.getLength(); i++) {

                            Node node = nList.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element2 = (Element) node;

                                unitNos.add(getValue("unitNo", element2));
                            }
                        }

                        BufferedReader in=new BufferedReader(new
                                InputStreamReader(
                                is));

                        StringBuffer sb = new StringBuffer("");
                        String line="";

                        while((line = in.readLine()) != null) {

                            sb.append(line);
                            break;
                        }

                        in.close();
                        // return sb.toString();
                        String answer = sb.toString();

                        Log.e("Hothayfa: " ,"Response from url XML: " + answer);

                    } catch (Exception e) {e.printStackTrace();}
                }
                else {
                    //return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                //return new String("Exception: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            unitAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.preference_category, unitNos);

            // Drop down layout style
            unitAdapter.setDropDownViewResource(android.R.layout.preference_category);

            // attaching data adapter to spinner
            units.setAdapter(unitAdapter);
        }
    }

    public class makeAllocation extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://" +  IPContainer.IP + "/mhs/allocation.php"); // here is your URL path

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));

                if (status.equals("Approved")) {
                    writer.write("fromDate=" + date + "&duration=" + duration + "&unitNo=" + unitNo + "&applicationID=" + applicationID
                            + "&status=" + status + "&applicantID=" + applicantID);
                }
                else if (status.equals("Waitlist")) {
                    writer.write("status=" + status + "&applicationID=" + applicationID + "&applicantID=" );
                }
                else {
                    writer.write("status=" + status + "&applicantID=" + applicantID );

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
            Log.d("Hothayfa", sb.toString());
            if(sb.toString().equals("false")){
                Toast.makeText(getApplicationContext(), "Failed to make allocation", Toast.LENGTH_SHORT).show();
            }else if(sb.toString().equals("true")){
                Toast.makeText(getApplicationContext(), "Allocation saved successfully",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Allocation.this, HousingOfficerMenu.class);
                intent.putExtra("intent_username", username);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Sorry, we are having problems to make this application. Try again later",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}