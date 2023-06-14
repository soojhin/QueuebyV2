package com.example.queuebyv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class RequestFragment extends Fragment {

    TextView seedetails;

    Button btnAll, btnOngoing, btnCompleted, btnGotoRequest;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    UserAdapter userAdapter;
    ArrayList<UserRequest> list;

    private CurrentUser CurrentUser;
    private ValueEventListener valueEventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        recyclerView = view.findViewById(R.id.requestlist);
        btnAll = view.findViewById(R.id.btnAll);
        btnOngoing = view.findViewById(R.id.btnOngoing);
        btnCompleted = view.findViewById(R.id.btnCompleted);
        btnGotoRequest = view.findViewById(R.id.btnGotoRequest);

        CurrentUser = CurrentUser.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Documents");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        userAdapter = new UserAdapter(getActivity(), list);
        recyclerView.setAdapter(userAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserRequest userRequest = dataSnapshot.getValue(UserRequest.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    if (email != null && email.equals(CurrentUser.getEmail())) {
                        list.add(userRequest);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed
            }
        });

        btnGotoRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Request.class);
                startActivity(intent);
            }
        });
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByStatus("all");
                btnAll.setBackgroundResource(R.drawable.custombtn7);
                btnAll.setTextColor(Color.WHITE);
                btnOngoing.setBackgroundColor(Color.WHITE);
                btnOngoing.setTextColor(Color.parseColor("#132540"));
                btnCompleted.setBackgroundColor(Color.WHITE);
                btnCompleted.setTextColor(Color.parseColor("#132540"));
            }
        });

        btnOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByStatus("ongoing");
                btnAll.setBackgroundColor(Color.WHITE);
                btnAll.setTextColor(Color.parseColor("#132540"));
                btnOngoing.setBackgroundResource(R.drawable.custombtn7);
                btnOngoing.setTextColor(Color.WHITE);
                btnCompleted.setBackgroundColor(Color.WHITE);
                btnCompleted.setTextColor(Color.parseColor("#132540"));
            }
        });

        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByStatus("completed");
                btnAll.setBackgroundColor(Color.WHITE);
                btnAll.setTextColor(Color.parseColor("#132540"));
                btnOngoing.setBackgroundColor(Color.WHITE);
                btnOngoing.setTextColor(Color.parseColor("#132540"));
                btnCompleted.setBackgroundResource(R.drawable.custombtn7);
                btnCompleted.setTextColor(Color.WHITE);
            }
        });

        return view;
    }

    public void filterByStatus(String status) {
        if (list == null) {
            return;
        }
        List<UserRequest> filteredList = new ArrayList<>();
        for (UserRequest item : list) {
            if (status.equalsIgnoreCase("all")) {
                filteredList.add(item);
            } else if (status.equals("ongoing")) {
                if (item.getStatus().equalsIgnoreCase("pending") || item.getStatus().equalsIgnoreCase("to receive")) {
                    filteredList.add(item);
                }
            } else if (status.equals("completed")) {
                if (item.getStatus().equalsIgnoreCase("completed")) {
                    filteredList.add(item);
                }
            }
        }
        userAdapter.setData((ArrayList<UserRequest>) filteredList);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Add a listener to retrieve the user's data from Firebase Realtime Database
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserRequest userRequest = dataSnapshot.getValue(UserRequest.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    if (email != null && email.equals(CurrentUser.getEmail())) {
                        list.add(userRequest);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed
            }
        };

        // Add the ValueEventListener to the DatabaseReference
        databaseReference.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Remove the ValueEventListener when the fragment is stopped
        if (valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
    }
}