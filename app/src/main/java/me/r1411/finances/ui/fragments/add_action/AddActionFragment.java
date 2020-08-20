package me.r1411.finances.ui.fragments.add_action;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import me.r1411.finances.R;

public class AddActionFragment extends Fragment {

    private AddActionViewModel addActionViewModel;
    private CollectionAdapter collectionAdapter;
    private ViewPager2 viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addActionViewModel = new ViewModelProvider(this).get(AddActionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_action, container, false);
        TabLayout tabLayout = root.findViewById(R.id.add_action_tab_layout);
        collectionAdapter = new CollectionAdapter(this);
        viewPager = root.findViewById(R.id.add_action_pager);
        viewPager.setAdapter(collectionAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(position == 0 ? R.string.expense : R.string.income)).attach();
        return root;
    }

}

