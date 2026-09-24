package com.swu.phinma.studentlife;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Student Support Assistant / Concern chat screen.
 * Matches student_life_student_support_chatbot.png.
 */
public class StudentConcernActivity extends AppCompatActivity {

    private EditText etConcernMessage;
    private LinearLayout layoutMessagesContainer;
    private ScrollView scrollChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_concern);

        findViewById(R.id.btnBackConcern).setOnClickListener(v -> finish());

        etConcernMessage = findViewById(R.id.etConcernMessage);
        scrollChat = findViewById(R.id.scrollChat);

        // Find parent linear layout inside scrollChat
        if (scrollChat != null && scrollChat.getChildCount() > 0) {
            layoutMessagesContainer = (LinearLayout) scrollChat.getChildAt(0);
        }

        findViewById(R.id.btnSendMessage).setOnClickListener(v -> sendMessage());

        findViewById(R.id.btnAttachFile).setOnClickListener(v -> {
            Toast.makeText(this, "Select supporting documentation (Max 100MB)", Toast.LENGTH_SHORT).show();
        });
    }

    private void sendMessage() {
        if (etConcernMessage == null) return;
        String text = etConcernMessage.getText().toString().trim();
        if (TextUtils.isEmpty(text)) return;

        etConcernMessage.setText("");
        addUserMessageBubble(text);

        // Auto reply from Student Support Bot
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            addBotMessageBubble("Thank you Maria. Your urgent appeal has been routed to the Dean of Student Affairs. Tracking ticket #SL-2026-000143 has been generated.");
            Toast.makeText(this, "Concern ticket #SL-2026-000143 created", Toast.LENGTH_SHORT).show();
        }, 1000);
    }

    private void addUserMessageBubble(String text) {
        if (layoutMessagesContainer == null) return;
        LinearLayout bubble = new LinearLayout(this);
        bubble.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.topMargin = 24;
        params.leftMargin = 120;
        params.gravity = android.view.Gravity.END;
        bubble.setLayoutParams(params);

        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(getResources().getColor(R.color.white, null));
        tv.setTextSize(13);
        tv.setBackgroundResource(R.drawable.bg_card_maroon_banner);
        tv.setPadding(32, 24, 32, 24);
        bubble.addView(tv);

        TextView time = new TextView(this);
        time.setText("Just now ✔✔");
        time.setTextSize(10);
        time.setTextColor(getResources().getColor(R.color.text_secondary, null));
        time.setGravity(android.view.Gravity.END);
        bubble.addView(time);

        layoutMessagesContainer.addView(bubble);
        scrollChat.post(() -> scrollChat.fullScroll(View.FOCUS_DOWN));
    }

    private void addBotMessageBubble(String text) {
        if (layoutMessagesContainer == null) return;
        LinearLayout bubble = new LinearLayout(this);
        bubble.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.topMargin = 24;
        params.rightMargin = 80;
        bubble.setLayoutParams(params);

        TextView sender = new TextView(this);
        sender.setText("Student Support Bot");
        sender.setTextSize(11);
        sender.setTextColor(getResources().getColor(R.color.text_secondary, null));
        bubble.addView(sender);

        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(getResources().getColor(R.color.text_primary, null));
        tv.setTextSize(13);
        tv.setBackgroundResource(R.drawable.bg_card_rounded_2xl);
        tv.setPadding(32, 24, 32, 24);
        bubble.addView(tv);

        layoutMessagesContainer.addView(bubble);
        scrollChat.post(() -> scrollChat.fullScroll(View.FOCUS_DOWN));
    }
}
