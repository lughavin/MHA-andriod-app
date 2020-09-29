package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HousingOfficerMenu extends AppCompatActivity {

    String username;
    TextView usernameHouseing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing_officer_menu);
        setTitle("Housing Officer Menu");


            CardView newResidence = findViewById(R.id.newResidence);
            CardView viewApplication = findViewById(R.id.viewApplication);
            CardView ViewResidence = findViewById(R.id.viewResidencesHO);

            username = getIntent().getStringExtra("intent_username");

            usernameHouseing = (TextView) findViewById(R.id.usernameHouseing);
            usernameHouseing.setText(username);

            viewApplication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewApplication();
                }
             });
            newResidence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setUpResidence();
                }
              });
             ViewResidence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewResidence();
                }
             });

            }

        public void setUpResidence(){
            Intent intent = new Intent(HousingOfficerMenu.this, AddResidence.class);
            intent.putExtra("intent_username", username);
            startActivity(intent);
        }

        public void viewApplication() {
            Intent intent2 = new Intent(HousingOfficerMenu.this, HousingOfficerViewApplications.class);
            intent2.putExtra("intent_username", username);
            startActivity(intent2);
        }
        public void viewResidence() {
            Intent intent3 = new Intent(HousingOfficerMenu.this, ResidenceList.class);
            intent3.putExtra("intent_username", username);
            startActivity(intent3);
        }

        public void LogOut(View view) {
            Toast.makeText(HousingOfficerMenu.this,"Log Out Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(HousingOfficerMenu.this, Login.class);
            startActivity(intent);
            finish();
        }

        }


