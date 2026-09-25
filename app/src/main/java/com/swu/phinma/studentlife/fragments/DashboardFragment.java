package com.swu.phinma.studentlife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.swu.phinma.studentlife.*;
import com.swu.phinma.studentlife.adapters.PendingActionsAdapter;
import com.swu.phinma.studentlife.adapters.RecentRequestsAdapter;
import com.swu.phinma.studentlife.data.DemoDataProvider;
import com.swu.phinma.studentlife.models.Student;

public class DashboardFragment extends Fragment {

    private TextView tvStudentName, tvStudentInfo, tvRequirementPercentage, tvVerifiedDocuments;
    private ProgressBar progressRequirements;
    private RecyclerView rvPendingActions, rvRecentRequests;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        bindViews(view);
        populateStudentData();
        setupRecyclerViews();
        setupClickListeners(view);
        return view;
    }

    private void bindViews(View view) {
        tvStudentName = view.findViewById(R.id.tvStudentName);
        tvStudentInfo = view.findViewById(R.id.tvStudentInfo);
        tvRequirementPercentage = view.findViewById(R.id.tvRequirementPercentage);
        tvVerifiedDocuments = view.findViewById(R.id.tvVerifiedDocuments);
        progressRequirements = view.findViewById(R.id.progressRequirements);
        rvPendingActions = view.findViewById(R.id.rvPendingActions);
        rvRecentRequests = view.findViewById(R.id.rvRecentRequests);
    }

    private void populateStudentData() {
        Student student = DemoDataProvider.getDemoStudent();
        tvStudentName.setText(student.getFullName());
        tvStudentInfo.setText("ID: " + student.getStudentNumber() + " • " + student.getCourse() + " • ");
        tvRequirementPercentage.setText(student.getRequirementsProgress() + "%");
        progressRequirements.setProgress(student.getRequirementsProgress());
        tvVerifiedDocuments.setText(student.getVerifiedDocumentsCount() + " of " + student.getTotalRequiredDocuments() + " verified documents");
    }

    private void setupRecyclerViews() {
        rvPendingActions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPendingActions.setAdapter(new PendingActionsAdapter(DemoDataProvider.getPendingActions(), action -> {
            Toast.makeText(getContext(), "Opening " + action.getTitle(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), ScholarshipApplicationActivity.class));
        }));

        rvRecentRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecentRequests.setAdapter(new RecentRequestsAdapter(DemoDataProvider.getRecentRequests(), req -> {
            startActivity(new Intent(getActivity(), RequestHistoryActivity.class));
        }));
    }

    private void setupClickListeners(View view) {
        view.findViewById(R.id.btnNotifications).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), NotificationsActivity.class));
        });

        view.findViewById(R.id.tvViewAllRecent).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), RequestHistoryActivity.class));
        });

        // Quick services
        view.findViewById(R.id.quickScholarship).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).selectTab(2);
            }
        });

        view.findViewById(R.id.quickDocuments).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DocumentRequestsActivity.class));
        });

        view.findViewById(R.id.quickConcerns).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), StudentConcernActivity.class));
        });

        view.findViewById(R.id.quickLostId).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DocumentRequestsActivity.class));
        });

        view.findViewById(R.id.quickHistory).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), RequestHistoryActivity.class));
        });

        view.findViewById(R.id.cardGuidance).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), StudentConcernActivity.class));
        });
    }
}
