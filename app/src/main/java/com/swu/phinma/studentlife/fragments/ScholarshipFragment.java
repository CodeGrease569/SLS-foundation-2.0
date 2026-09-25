package com.swu.phinma.studentlife.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.swu.phinma.studentlife.*;
import com.swu.phinma.studentlife.adapters.ScholarshipRequirementsAdapter;
import com.swu.phinma.studentlife.data.DemoDataProvider;

public class ScholarshipFragment extends Fragment {

    private RecyclerView rvRequirements;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scholarship, container, false);
        bindViews(view);
        setupRecyclerView();
        setupClickListeners(view);
        return view;
    }

    private void bindViews(View view) {
        rvRequirements = view.findViewById(R.id.rvScholarshipRequirements);
    }

    private void setupRecyclerView() {
        rvRequirements.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRequirements.setAdapter(new ScholarshipRequirementsAdapter(
                DemoDataProvider.getScholarshipRequirements(),
                req -> {
                    Toast.makeText(getContext(), "Selected " + req.getTitle() + " for upload", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), ScholarshipApplicationActivity.class));
                }
        ));
    }

    private void setupClickListeners(View view) {
        view.findViewById(R.id.btnScholarshipNotifications).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), NotificationsActivity.class));
        });

        view.findViewById(R.id.btnSubmitFiles).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ScholarshipApplicationActivity.class));
        });

        view.findViewById(R.id.btnScholarshipGuidelines).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Downloading CHED Grant Guidelines (PDF)", Toast.LENGTH_SHORT).show();
        });
    }
}
