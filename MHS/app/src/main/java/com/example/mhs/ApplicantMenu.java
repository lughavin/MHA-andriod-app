package com.example.mhs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicantMenu extends AppCompatActivity {

    String username;
    TextView usernameApplicant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_menu);
        setTitle("Applicant Menu");

        CardView viewResidences = findViewById(R.id.viewResidences);
        CardView viewApplication = findViewById(R.id.viewApplication);

        username = getIntent().getStringExtra("intent_username");
        usernameApplicant = (TextView) findViewById(R.id.usernameApplicant);
        usernameApplicant.setText(username);

        viewApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewApplication();
            }
        });
        viewResidences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewResidences();
            }
        });

    }
        public void viewApplication(){
            Intent intent = new Intent(ApplicantMenu.this, ApplicantViewApplications.class);
            intent.putExtra("intent_username", username);
            startActivity(intent);
        }

        public void viewResidences() {
            Intent intent2 = new Intent(ApplicantMenu.this, ApplicantViewResidence.class);
            intent2.putExtra("intent_username", username);
            startActivity(intent2);
        }

    public void LogOut(View view) {
        Toast.makeText(ApplicantMenu.this,"Log Out Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ApplicantMenu.this, Login.class);
        startActivity(intent);
        finish();
    }

    }