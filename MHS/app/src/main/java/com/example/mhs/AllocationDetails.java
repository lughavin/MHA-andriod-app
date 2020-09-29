package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AllocationDetails extends AppCompatActivity {

    private TextView tvResidenceID, tvNumOfUnit, tvMonthlyRental, tvApplicantUsername, tvMonthlyIncome, tvRequiredMonth, tvRequiredYear;
    private Button applyBtn;
    private String residenceID, applicationID, applicantID, numOfUnit, monthlyRental, username, monthlyIncome, requiredMonth,requiredYear ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_details);
        setTitle("Allocation");

        username = getIntent().getStringExtra("intent_username");
        residenceID = getIntent().getStringExtra("residenceID");
        monthlyRental = getIntent().getStringExtra("monthlyRental");
        applicantID = getIntent().getStringExtra("applicantID");
        numOfUnit = getIntent().getStringExtra("numOfUnit");
        monthlyIncome = getIntent().getStringExtra("monthlyIncome");
        requiredMonth = getIntent().getStringExtra("requiredMonth");
        requiredYear = getIntent().getStringExtra("requiredYear");
        applicationID = getIntent().getStringExtra("applicationID");

        tvResidenceID = (TextView) findViewById(R.id.residenceID);
        tvNumOfUnit = (TextView) findViewById(R.id.numOfUnit);
        tvApplicantUsername = (TextView) findViewById(R.id.applicantID);
        tvMonthlyRental = (TextView) findViewById(R.id.monthlyRental);
        tvMonthlyIncome = (TextView) findViewById(R.id.monthlyIncome);
        tvRequiredMonth = (TextView) findViewById(R.id.requiredMonth);
        tvRequiredYear = (TextView) findViewById(R.id.requiredYear);
        applyBtn = (Button) findViewById(R.id.applyBtn);


        //linking button to the next page
        applyBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(AllocationDetails.this, Allocation.class);
                intent.putExtra("intent_username", username);
                intent.putExtra("intent_residenceID", residenceID);
                intent.putExtra("applicationID", applicationID);
                intent.putExtra("applicantID", applicationID);
                startActivity(intent);
            }
        });

        //Setting intent strings into text views
        tvResidenceID.setText(residenceID);
        tvNumOfUnit.setText(numOfUnit);
        tvApplicantUsername.setText(applicantID);
        tvMonthlyRental.setText(monthlyRental);
        tvMonthlyIncome.setText(monthlyIncome);
        tvRequiredMonth.setText(requiredMonth);
        tvRequiredYear.setText(requiredYear);
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
