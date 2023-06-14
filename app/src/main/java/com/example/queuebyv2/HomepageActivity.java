package com.example.queuebyv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.queuebyv2.databinding.ActivityHomepageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomepageActivity extends AppCompatActivity {

    ActivityHomepageBinding binding;

    private static final int SELECTED_TEXT_COLOR = Color.parseColor("#132540"); // Text color for selected item
    private static final int UNSELECTED_TEXT_COLOR = Color.parseColor("#F8F8F8"); // Text color for unselected items
    private static final int SELECTED_BACKGROUND_COLOR = Color.parseColor("#F8F8F8"); // Background color for selected item
    private static final int UNSELECTED_BACKGROUND_COLOR = Color.parseColor("#132540"); // Background color for unselected items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        // Set custom colors for the bottom navigation view
        ColorStateList itemColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        SELECTED_TEXT_COLOR,
                        UNSELECTED_TEXT_COLOR
                }
        );

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setItemIconTintList(itemColorStateList);
        bottomNavigationView.setItemTextColor(itemColorStateList);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            handleNavigationItemSelected(item);
            return true;
        });

        // Set background color of selected item
        int selectedItemIndex = 0; // Set the index of the initially selected item
        BottomNavigationItemView selectedItem = bottomNavigationView.findViewById(bottomNavigationView.getMenu().getItem(selectedItemIndex).getItemId());
        if (selectedItem != null) {
            selectedItem.setBackgroundColor(SELECTED_BACKGROUND_COLOR);
        }
    }

    private void handleNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.request:
                replaceFragment(new RequestFragment());
                break;
            case R.id.profile:
                replaceFragment(new ProfileFragment());
                break;
        }

        // Reset text and background colors for all items
        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            BottomNavigationItemView itemView = bottomNavigationView.findViewById(bottomNavigationView.getMenu().getItem(i).getItemId());
            if (itemView != null) {
                TextView itemText = itemView.findViewById(com.google.android.material.R.id.title);
                if (itemText != null) {
                    itemText.setTextColor(UNSELECTED_TEXT_COLOR);
                }
                itemView.setBackgroundColor(UNSELECTED_BACKGROUND_COLOR);
            }
        }

        // Set text and background color for selected item
        BottomNavigationItemView selectedItemView = bottomNavigationView.findViewById(item.getItemId());
        if (selectedItemView != null) {
            TextView selectedItemText = selectedItemView.findViewById(com.google.android.material.R.id.title);
            if (selectedItemText != null) {
                selectedItemText.setTextColor(SELECTED_TEXT_COLOR); // Update the text color to selected color
            }
            selectedItemView.setBackgroundColor(SELECTED_BACKGROUND_COLOR);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
