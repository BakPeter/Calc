package com.bpapps.calc.view.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bpapps.calc.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends Fragment {

    private ViewPager2 mViewPager;

    public MainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new CaclViewPagerAdapter(this));

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);


        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout,
                mViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        String tabText;
                        switch (position) {
                            case 0:
                                tabText = getString(R.string.tab_calculator_text);
                                break;
                            case 1:
                                tabText = getString(R.string.tab_history_text);
                                break;
                            case 2:
                                tabText = getString(R.string.tab_memory_text);
                                break;
                            default:
                                tabText = null;
                        }
                        tab.setText(tabText);
                    }
                });
        tabLayoutMediator.attach();

        return view;
    }

}
