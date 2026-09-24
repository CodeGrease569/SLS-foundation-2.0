package com.swu.phinma.studentlife;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Sign Up / Create Account screen for Student Life.
 * Matches student_life_create_account.png.
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etSignUpStudentId, etEmail, etSignUpPassword;
    private CheckBox cbTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etSignUpStudentId = findViewById(R.id.etSignUpStudentId);
        etEmail = findViewById(R.id.etEmail);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        cbTerms = findViewById(R.id.cbTerms);

        findViewById(R.id.tvLinkSignIn).setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        findViewById(R.id.btnDoSignUp).setOnClickListener(v -> attemptSignUp());
    }

    private void attemptSignUp() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String studentId = etSignUpStudentId.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etSignUpPassword.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError("First name is required");
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError("Last name is required");
            return;
        }
        if (TextUtils.isEmpty(studentId)) {
            etSignUpStudentId.setError("Student ID is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("University email is required");
            return;
        }
        if (!email.contains("@")) {
            etEmail.setError("Must be a valid email");
            return;
        }
        if (password.length() < 8) {
            etSignUpPassword.setError("Minimum 8 characters");
            return;
        }
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Please agree to the Terms of Service", Toast.LENGTH_SHORT).show();
            return;
        }

        // Frontend demonstration: navigate to MainActivity
        Toast.makeText(this, "Account created! Welcome, " + firstName, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
