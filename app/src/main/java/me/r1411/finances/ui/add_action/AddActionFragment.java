package me.r1411.finances.ui.add_action;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import me.r1411.finances.R;

public class AddActionFragment extends Fragment {

    private AddActionViewModel addActionViewModel;
    private CollectionAdapter collectionAdapter;
    private ViewPager2 viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addActionViewModel = ViewModelProviders.of(this).get(AddActionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_action, container, false);
        TabLayout tabLayout = root.findViewById(R.id.add_action_tab_layout);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        collectionAdapter = new CollectionAdapter(this);
        viewPager = view.findViewById(R.id.add_action_pager);
        viewPager.setAdapter(collectionAdapter);
        TabLayout tabLayout = view.findViewById(R.id.add_action_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(position == 0 ? R.string.expense : R.string.income);
            }
        }).attach();
    }
}

