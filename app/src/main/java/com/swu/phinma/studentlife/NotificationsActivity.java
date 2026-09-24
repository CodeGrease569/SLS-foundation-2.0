package com.swu.phinma.studentlife;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.swu.phinma.studentlife.adapters.NotificationsAdapter;
import com.swu.phinma.studentlife.data.DemoDataProvider;
import com.swu.phinma.studentlife.models.NotificationItem;
import java.util.List;

/**
 * Notifications Center screen.
 * Matches student_life_notifications.png.
 */
public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView rvNotifications;
    private NotificationsAdapter adapter;
    private List<NotificationItem> notificationList;
    private TextView tvInboxUnreadCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        findViewById(R.id.btnBackNotifications).setOnClickListener(v -> finish());

        tvInboxUnreadCount = findViewById(R.id.tvInboxUnreadCount);
        rvNotifications = findViewById(R.id.rvNotifications);

        notificationList = DemoDataProvider.getNotifications();
        adapter = new NotificationsAdapter(notificationList, item -> {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            updateUnreadCount();
        });

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setAdapter(adapter);

        findViewById(R.id.btnMarkAllRead).setOnClickListener(v -> {
            for (NotificationItem item : notificationList) {
                item.setUnread(false);
            }
            adapter.notifyDataSetChanged();
            tvInboxUnreadCount.setText("0 unread");
            Toast.makeText(this, "All notifications marked as read", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateUnreadCount() {
        int count = 0;
        for (NotificationItem item : notificationList) {
            if (item.isUnread()) count++;
        }
        tvInboxUnreadCount.setText(count + " unread");
    }
}
