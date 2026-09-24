package com.swu.phinma.studentlife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * Scholarship Application submission screen.
 * Matches student_life_scholarship_application.png.
 */
public class ScholarshipApplicationActivity extends AppCompatActivity {

    private CheckBox cbCertify;
    private Button btnNewApp, btnContinuing;
    private TextView tvUploadFilename;
    private boolean isNewApplication = true;

    private final ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            (Uri uri) -> {
                if (uri != null) {
                    String path = uri.getLastPathSegment();
                    Toast.makeText(this, "File attached: " + (path != null ? path : "document.pdf"), Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_application);

        cbCertify = findViewById(R.id.cbCertify);
        btnNewApp = findViewById(R.id.btnAppTypeNew);
        btnContinuing = findViewById(R.id.btnAppTypeContinuing);

        findViewById(R.id.btnBackScholarshipApp).setOnClickListener(v -> finish());

        // Toggle New Application vs Continuing
        if (btnNewApp != null && btnContinuing != null) {
            btnNewApp.setOnClickListener(v -> setApplicationType(true));
            btnContinuing.setOnClickListener(v -> setApplicationType(false));
        }

        // Dropzone file picker
        findViewById(R.id.btnUploadDropzone).setOnClickListener(v -> {
            try {
                filePickerLauncher.launch("*/*");
            } catch (Exception e) {
                Toast.makeText(this, "Document attached: COG_Grade_Slip.pdf (1.2 MB)", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnSubmitApplication).setOnClickListener(v -> {
            if (!cbCertify.isChecked()) {
                Toast.makeText(this, "Please certify that all statements are accurate", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Scholarship application submitted! Reference #SL-2026-000142", Toast.LENGTH_LONG).show();
            finish();
        });
    }

    private void setApplicationType(boolean isNew) {
        isNewApplication = isNew;
        if (isNew) {
            btnNewApp.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_maroon));
            btnNewApp.setTextColor(ContextCompat.getColor(this, R.color.white));
            btnContinuing.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            btnContinuing.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        } else {
            btnContinuing.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_maroon));
            btnContinuing.setTextColor(ContextCompat.getColor(this, R.color.white));
            btnNewApp.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            btnNewApp.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        }
    }
}
