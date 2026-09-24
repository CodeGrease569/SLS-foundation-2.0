package com.swu.phinma.studentlife;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Sign In / Welcome back screen for Student Life.
 * Matches student_life_welcome_sign_in.png.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etStudentId;
    private EditText etPassword;
    private ImageView ivTogglePassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etStudentId = findViewById(R.id.etStudentId);
        etPassword = findViewById(R.id.etPassword);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);

        // Pre-fill demo test credentials matching the design
        etStudentId.setText("2024-08912");
        etPassword.setText("student123");

        // Toggle password visibility
        if (ivTogglePassword != null) {
            ivTogglePassword.setOnClickListener(v -> {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etPassword.setSelection(etPassword.getText().length());
            });
        }

        // Tap demo card to refill
        findViewById(R.id.cardDemoAccess).setOnClickListener(v -> {
            etStudentId.setText("2024-08912");
            etPassword.setText("student123");
            Toast.makeText(this, "Demo credentials pre-filled", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btnDoSignIn).setOnClickListener(v -> attemptSignIn());

        TextView tvLinkSignUp = findViewById(R.id.tvLinkSignUp);
        if (tvLinkSignUp != null) {
            tvLinkSignUp.setOnClickListener(v -> {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            });
        }
    }

    private void attemptSignIn() {
        String studentId = etStudentId.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(studentId)) {
            etStudentId.setError("Student ID or Email is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return;
        }

        // Frontend demonstration navigation: proceed to MainActivity
        Toast.makeText(this, "Signed in as " + studentId, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
