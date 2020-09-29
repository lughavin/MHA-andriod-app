package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResidenceDetails extends AppCompatActivity {

    private TextView tvAddress, tvNumOfUnit, tvSizePerUnit,tvMonthlyRental;
    private Button applyBtn;
    private String address, numOfUnit, sizePerUnit, monthlyRental, username, residenceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residence_details);
        setTitle("Residence Details");

        username = getIntent().getStringExtra("intent_username");
        residenceID = getIntent().getStringExtra("residenceID");
        address = getIntent().getStringExtra("address");
        numOfUnit = getIntent().getStringExtra("numOfUnit");
        sizePerUnit = getIntent().getStringExtra("sizePerUnit");
        monthlyRental = getIntent().getStringExtra("monthlyRental");

        tvAddress = (TextView) findViewById(R.id.address);
        tvNumOfUnit = (TextView) findViewById(R.id.numOfUnit);
        tvSizePerUnit = (TextView) findViewById(R.id.sizePerUnit);
        tvMonthlyRental = (TextView) findViewById(R.id.monthlyRental);
        applyBtn = (Button) findViewById(R.id.applyBtn);



        //linking button to the next page
        applyBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(ResidenceDetails.this, MakeApplication.class);
                intent.putExtra("intent_username", username);
                intent.putExtra("intent_residenceID", residenceID);
                intent.putExtra("intent_address", address);
                startActivity(intent);
            }
        });

        //Setting intent strings into text views
        tvAddress.setText(address);
        tvNumOfUnit.setText(numOfUnit);
        tvSizePerUnit.setText(sizePerUnit);
        tvMonthlyRental.setText(monthlyRental);
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
