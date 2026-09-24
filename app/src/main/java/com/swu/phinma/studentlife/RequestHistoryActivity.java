package com.swu.phinma.studentlife;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.swu.phinma.studentlife.adapters.RequestHistoryAdapter;
import com.swu.phinma.studentlife.data.DemoDataProvider;
import com.swu.phinma.studentlife.models.RequestHistoryItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Request History screen with live search and status filter chips.
 * Matches student_life_request_history.png.
 */
public class RequestHistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private RequestHistoryAdapter adapter;
    private List<RequestHistoryItem> allItems;
    private List<RequestHistoryItem> displayedItems;
    private EditText etSearch;

    private TextView filterAll, filterUnderReview, filterProcessing, filterCompleted;
    private String currentFilter = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_history);

        rvHistory = findViewById(R.id.rvRequestHistory);
        etSearch = findViewById(R.id.etSearchHistory);

        filterAll = findViewById(R.id.filterAll);
        filterUnderReview = findViewById(R.id.filterUnderReview);
        filterProcessing = findViewById(R.id.filterProcessing);
        filterCompleted = findViewById(R.id.filterCompleted);

        allItems = DemoDataProvider.getRequestHistory();
        displayedItems = new ArrayList<>(allItems);

        adapter = new RequestHistoryAdapter(displayedItems, item -> {
            Toast.makeText(this, "Tracking: " + item.getTitle() + " (" + item.getStatus() + ")", Toast.LENGTH_SHORT).show();
        });

        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(adapter);

        setupFilterChips();
        setupSearch();

        findViewById(R.id.btnExportHistory).setOnClickListener(v -> {
            Toast.makeText(this, "Exporting request history report (PDF/CSV)", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupFilterChips() {
        if (filterAll != null) filterAll.setOnClickListener(v -> applyFilter("ALL", filterAll));
        if (filterUnderReview != null) filterUnderReview.setOnClickListener(v -> applyFilter("Under Review", filterUnderReview));
        if (filterProcessing != null) filterProcessing.setOnClickListener(v -> applyFilter("Processing", filterProcessing));
        if (filterCompleted != null) filterCompleted.setOnClickListener(v -> applyFilter("Completed", filterCompleted));
    }

    private void applyFilter(String filter, TextView activeView) {
        currentFilter = filter;
        resetFilterChipStyles();

        activeView.setBackgroundResource(R.drawable.bg_card_maroon_banner);
        activeView.setTextColor(ContextCompat.getColor(this, R.color.white));

        filterList();
    }

    private void resetFilterChipStyles() {
        TextView[] chips = {filterAll, filterUnderReview, filterProcessing, filterCompleted};
        for (TextView chip : chips) {
            if (chip != null) {
                chip.setBackgroundResource(R.drawable.bg_pill_processing);
                chip.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.card_surface));
                chip.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
            }
        }
    }

    private void setupSearch() {
        if (etSearch == null) return;
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void filterList() {
        String query = etSearch != null ? etSearch.getText().toString().trim().toLowerCase() : "";
        displayedItems.clear();

        for (RequestHistoryItem item : allItems) {
            boolean matchesFilter = currentFilter.equals("ALL")
                    || item.getStatus().equalsIgnoreCase(currentFilter)
                    || (currentFilter.equals("Completed") && item.getStatus().equalsIgnoreCase("Approved"));

            boolean matchesQuery = query.isEmpty()
                    || item.getTitle().toLowerCase().contains(query)
                    || item.getReferenceNumber().toLowerCase().contains(query)
                    || item.getDepartment().toLowerCase().contains(query);

            if (matchesFilter && matchesQuery) {
                displayedItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
