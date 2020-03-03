package com.bpapps.calc.view.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpapps.calc.CalculatorObjectsContainer;
import com.bpapps.calc.R;
import com.bpapps.calc.contractsmvp.IHistoryContract;
import com.bpapps.calc.view.adapters.HistoryRecyclerViewAdapter;
import com.bpapps.calc.view.interfaces.IOnHistoryDataBaseChangedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HistoryFragment extends Fragment
        implements IHistoryContract.View, IOnHistoryDataBaseChangedListener {

    private static final String TAG = HistoryFragment.class.getSimpleName();


    public static HistoryFragment getInstance() {
        return new HistoryFragment();
    }

    private IHistoryContract.Presenter mPresenter;

    private HistoryRecyclerViewAdapter.IMemoryItemClicked mItemClickedCallBack;

    private RecyclerView mRecyclerView;
    private HistoryRecyclerViewAdapter mAdapter;
    private TextView mTextViewEmptyDataBaseMsg;

    private FloatingActionButton mFloatingActionButton;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public void setOnMemoryItemClickedCallBack(HistoryRecyclerViewAdapter.IMemoryItemClicked IMemoryItemClicked) {
        mItemClickedCallBack = IMemoryItemClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        setCalculatorPresenter(CalculatorObjectsContainer.getInstance().getHistoryPresenter());

        mTextViewEmptyDataBaseMsg = view.findViewById(R.id.text_view_history_empty_data_base_msg);
        setTextViewEmptyDataBaseMsgVisibilityStatus();

        mAdapter = new HistoryRecyclerViewAdapter(mPresenter.getHistoryDataBase(), requireActivity(), mItemClickedCallBack);

        mRecyclerView = view.findViewById(R.id.recycler_view_history);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.setAdapter(mAdapter);

        mFloatingActionButton = view.findViewById(R.id.floating_action_bar_clear_history);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteAllHistoryEntries();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    @Override
    public void setCalculatorPresenter(IHistoryContract.Presenter calculatorPresenter) {
        mPresenter = calculatorPresenter;

        mPresenter.attachView(this);
    }

    @Override
    public void onDataBaseUpdated() {
        onDataBaseChanged();
    }

    @Override
    public void onDataBaseChanged() {
        if (mAdapter != null)
            mRecyclerView.getAdapter().notifyDataSetChanged();

        setTextViewEmptyDataBaseMsgVisibilityStatus();
    }

    public void setTextViewEmptyDataBaseMsgVisibilityStatus() {
        if (mPresenter != null) {
            if (!mPresenter.isDataBaseEmpty())
                mTextViewEmptyDataBaseMsg.setVisibility(View.GONE);
            else
                mTextViewEmptyDataBaseMsg.setVisibility(View.VISIBLE);
        }
    }
}
