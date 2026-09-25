package com.swu.phinma.studentlife;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.swu.phinma.studentlife.adapters.NotificationsAdapter;
import com.swu.phinma.studentlife.data.DemoDataProvider;
import com.swu.phinma.studentlife.models.NotificationItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Notifications Center screen with live tabs and unread badges.
 * Matches student_life_notifications.png.
 */
public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView rvNotifications;
    private NotificationsAdapter adapter;
    private List<NotificationItem> allNotifications;
    private List<NotificationItem> displayedNotifications;
    private TextView tvInboxUnreadCount, tabAll, tabUnread;
    private boolean showOnlyUnread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        findViewById(R.id.btnBackNotifications).setOnClickListener(v -> finish());

        tvInboxUnreadCount = findViewById(R.id.tvInboxUnreadCount);
        rvNotifications = findViewById(R.id.rvNotifications);
        tabAll = findViewById(R.id.tabNotificationsAll);
        tabUnread = findViewById(R.id.tabNotificationsUnread);

        allNotifications = DemoDataProvider.getNotifications();
        displayedNotifications = new ArrayList<>(allNotifications);

        adapter = new NotificationsAdapter(displayedNotifications, item -> {
            updateUnreadCount();
        });

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setAdapter(adapter);

        setupTabs();

        findViewById(R.id.btnMarkAllRead).setOnClickListener(v -> {
            for (NotificationItem item : allNotifications) {
                item.setUnread(false);
            }
            updateUnreadCount();
            refreshFilter();
            Toast.makeText(this, "All notifications marked as read", Toast.LENGTH_SHORT).show();
        });

        updateUnreadCount();
    }

    private void setupTabs() {
        if (tabAll != null) {
            tabAll.setOnClickListener(v -> {
                showOnlyUnread = false;
                tabAll.setBackgroundResource(R.drawable.bg_card_maroon_banner);
                tabAll.setTextColor(ContextCompat.getColor(this, R.color.white));
                tabUnread.setBackgroundResource(R.drawable.bg_pill_processing);
                tabUnread.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.card_surface));
                tabUnread.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
                refreshFilter();
            });
        }
        if (tabUnread != null) {
            tabUnread.setOnClickListener(v -> {
                showOnlyUnread = true;
                tabUnread.setBackgroundResource(R.drawable.bg_card_maroon_banner);
                tabUnread.setTextColor(ContextCompat.getColor(this, R.color.white));
                tabAll.setBackgroundResource(R.drawable.bg_pill_processing);
                tabAll.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.card_surface));
                tabAll.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
                refreshFilter();
            });
        }
    }

    private void refreshFilter() {
        displayedNotifications.clear();
        for (NotificationItem item : allNotifications) {
            if (!showOnlyUnread || item.isUnread()) {
                displayedNotifications.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void updateUnreadCount() {
        int count = 0;
        for (NotificationItem item : allNotifications) {
            if (item.isUnread()) count++;
        }
        if (tvInboxUnreadCount != null) {
            tvInboxUnreadCount.setText(count + " unread");
        }
        if (tabUnread != null) {
            tabUnread.setText("Unread (" + count + ")");
        }
    }
}
