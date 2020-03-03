package com.bpapps.calc.view.ui.main;

import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bpapps.calc.view.fragments.CalculatorFragment;
import com.bpapps.calc.view.fragments.HistoryFragment;
import com.bpapps.calc.view.fragments.MemoryFragment;

public class CaclViewPagerAdapter extends FragmentStateAdapter {
    private CalculatorFragment mCalculatorFragment;
    private HistoryFragment mHistoryFragment;
    private MemoryFragment mMemoryFragment;

    public CaclViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);

        mCalculatorFragment = CalculatorFragment.getInstance();
        mHistoryFragment = HistoryFragment.getInstance();
        mMemoryFragment = MemoryFragment.getInstance();

        mHistoryFragment.setOnMemoryItemClickedCallBack(mCalculatorFragment);
        mMemoryFragment.setOnMemoryItemClickCallBack(mCalculatorFragment);
        mCalculatorFragment.setOnDataBaseChangedListener(mHistoryFragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return mCalculatorFragment;
                case 1:
                    return mHistoryFragment;
                case 2:
                    return mMemoryFragment;
                default:
                    return null;
            }
    }

    @Override
    public int getItemCount() {
            return 3;
    }
}
