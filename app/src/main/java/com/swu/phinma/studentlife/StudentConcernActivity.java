package com.swu.phinma.studentlife;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Student Support Assistant / Concern chat screen.
 * Matches student_life_student_support_chatbot.png.
 */
public class StudentConcernActivity extends AppCompatActivity {

    private EditText etConcernMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_concern);

        findViewById(R.id.btnBackConcern).setOnClickListener(v -> finish());

        etConcernMessage = findViewById(R.id.etConcernMessage);

        findViewById(R.id.btnSendMessage).setOnClickListener(v -> {
            String text = etConcernMessage.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                return;
            }
            etConcernMessage.setText("");
            Toast.makeText(this, "Concern submitted to Dean of Student Affairs Office", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnAttachFile).setOnClickListener(v -> {
            Toast.makeText(this, "Select supporting documentation (Max 100MB)", Toast.LENGTH_SHORT).show();
        });
    }
}
