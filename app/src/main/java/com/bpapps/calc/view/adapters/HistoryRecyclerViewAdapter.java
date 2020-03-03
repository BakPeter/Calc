package com.bpapps.calc.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bpapps.calc.R;
import com.bpapps.calc.model.HistoryEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    private ArrayList<HistoryEntry> mData;
    private Context mContext;

    private IMemoryItemClicked mOnItemClick;

    public interface IMemoryItemClicked {
        void onClick(HistoryEntry entry);
    }

    public HistoryRecyclerViewAdapter(ArrayList<HistoryEntry> data, Context context, IMemoryItemClicked onItemClick) {
        mData = data;
        mContext = context;
        mOnItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item_line, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final HistoryEntry entry = mData.get(position);

        holder.mTextViewResult.setText(entry.getValue() + "");
        holder.mTextViewFormula.setText(entry.getFormula());

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null)
                    mOnItemClick.onClick(entry);

                TabLayout tabLayout = ((Activity) mContext).findViewById(R.id.tab_layout);
                tabLayout.getTabAt(0).select();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mContainer;
        TextView mTextViewFormula;
        TextView mTextViewResult;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mContainer = itemView.findViewById(R.id.history_item_container);
            mTextViewFormula = itemView.findViewById(R.id.text_view_item_history_formula);
            mTextViewResult = itemView.findViewById(R.id.text_view_item_history_result);
        }
    }
}
