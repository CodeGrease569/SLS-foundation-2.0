package com.swu.phinma.studentlife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Document Requests Catalog screen.
 * Matches student_life_document_requests.png.
 */
public class DocumentRequestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_requests);

        findViewById(R.id.btnBackDocRequests).setOnClickListener(v -> finish());

        findViewById(R.id.btnDocNotifications).setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationsActivity.class));
        });
    }
}
