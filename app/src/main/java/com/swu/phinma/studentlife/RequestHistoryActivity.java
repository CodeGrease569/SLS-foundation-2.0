package com.swu.phinma.studentlife;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.swu.phinma.studentlife.adapters.RequestHistoryAdapter;
import com.swu.phinma.studentlife.data.DemoDataProvider;

/**
 * Request History screen.
 * Matches student_life_request_history.png.
 */
public class RequestHistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_history);

        rvHistory = findViewById(R.id.rvRequestHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(new RequestHistoryAdapter(DemoDataProvider.getRequestHistory(), item -> {
            Toast.makeText(this, "Opening details for Ref: " + item.getReferenceNumber(), Toast.LENGTH_SHORT).show();
        }));

        findViewById(R.id.btnExportHistory).setOnClickListener(v -> {
            Toast.makeText(this, "Exporting request history report (PDF/CSV)", Toast.LENGTH_SHORT).show();
        });
    }
}
