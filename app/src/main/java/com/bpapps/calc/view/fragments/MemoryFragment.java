package com.bpapps.calc.view.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bpapps.calc.CalculatorObjectsContainer;
import com.bpapps.calc.R;
import com.bpapps.calc.contractsmvp.IMemoryContract;
import com.bpapps.calc.view.adapters.MemoryRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MemoryFragment extends Fragment
        implements IMemoryContract.View, MemoryRecyclerViewAdapter.IOnMemoryButtonClickListener {

    private IMemoryContract.Presenter mPresenter;

    private TextView mTextViewEmptyMemoryMsgShower;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;

    private MemoryRecyclerViewAdapter mAdapter;

    private MemoryRecyclerViewAdapter.IOnMemoryItemClickCallBack mOnMemoryItemClickCallBack;

    public static MemoryFragment getInstance() {
        return new MemoryFragment();
    }

    public MemoryFragment() {
    }

    public void setOnMemoryItemClickCallBack(MemoryRecyclerViewAdapter.IOnMemoryItemClickCallBack onMemoryItemClickCallBack) {
        mOnMemoryItemClickCallBack = onMemoryItemClickCallBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        setCalculatorPresenter(CalculatorObjectsContainer.getInstance().getMemoryPresenter());

        mAdapter = new MemoryRecyclerViewAdapter(mPresenter.getDataBase(), this, mOnMemoryItemClickCallBack, requireContext());

        mRecyclerView = view.findViewById(R.id.recycler_view_memory);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mRecyclerView.setAdapter(mAdapter);

        mTextViewEmptyMemoryMsgShower = view.findViewById(R.id.text_view_empty_memory_msg_shower);
        setTextViewEmptyMemoryMsgShowerVisibilityStatus();

        mFloatingActionButton = view.findViewById(R.id.floating_action_bar_clear_memory);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.clearDataBase();
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
    public void setCalculatorPresenter(IMemoryContract.Presenter calculatorPresenter) {
        mPresenter = calculatorPresenter;
        mPresenter.attachView(this);
    }

    private void setTextViewEmptyMemoryMsgShowerVisibilityStatus() {
        if (mPresenter.isDataBaseEmpty())
            mTextViewEmptyMemoryMsgShower.setVisibility(View.VISIBLE);
        else
            mTextViewEmptyMemoryMsgShower.setVisibility(View.GONE);
    }

    @Override
    public void onClick(int buttonID, int entryId) {
        switch (buttonID) {
            case R.id.button_delete_memory_item:
                mPresenter.deleteMemoryItem(entryId);
                break;
            case R.id.button_add_to_item_memory_value:
                mPresenter.addToMemoryItem(entryId);
                break;
            case R.id.button_item_subtract_from_memory:
                mPresenter.subtractFromMemory(entryId);
                break;
        }
    }

    @Override
    public void onDataBaseUpdated() {
        if (mAdapter != null)
            mRecyclerView.getAdapter().notifyDataSetChanged();

        setTextViewEmptyMemoryMsgShowerVisibilityStatus();
    }
}
