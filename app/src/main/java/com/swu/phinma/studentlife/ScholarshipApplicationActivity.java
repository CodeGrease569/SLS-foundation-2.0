package com.swu.phinma.studentlife;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Scholarship Application submission screen.
 * Matches student_life_scholarship_application.png.
 */
public class ScholarshipApplicationActivity extends AppCompatActivity {

    private CheckBox cbCertify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_application);

        cbCertify = findViewById(R.id.cbCertify);

        findViewById(R.id.btnBackScholarshipApp).setOnClickListener(v -> finish());

        findViewById(R.id.btnUploadDropzone).setOnClickListener(v -> {
            Toast.makeText(this, "Select supporting document (PDF, JPG, PNG - Max 100MB)", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnSubmitApplication).setOnClickListener(v -> {
            if (!cbCertify.isChecked()) {
                Toast.makeText(this, "Please certify that all statements are accurate", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Scholarship application submitted for review! Reference #SL-2026-000142", Toast.LENGTH_LONG).show();
            finish();
        });
    }
}
