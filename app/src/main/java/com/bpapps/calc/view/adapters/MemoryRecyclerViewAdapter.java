package com.bpapps.calc.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bpapps.calc.R;
import com.bpapps.calc.model.MemoryEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MemoryRecyclerViewAdapter extends RecyclerView.Adapter<MemoryRecyclerViewAdapter.ViewHolder> {
    private ArrayList<MemoryEntry> mData;
    private IOnMemoryButtonClickListener mOnButtonClickListener;
    private IOnMemoryItemClickCallBack mOnItemClickCallBack;
    private Context mContext;

    public interface IOnMemoryButtonClickListener {
        void onClick(int buttonID, int entryId);
    }

    public interface IOnMemoryItemClickCallBack {
        void onClick(MemoryEntry item);
    }

    public MemoryRecyclerViewAdapter(ArrayList<MemoryEntry> data,
                                     IOnMemoryButtonClickListener onClickListener,
                                     IOnMemoryItemClickCallBack onItemClickListener,
                                     Context context) {
        mData = data;
        mOnButtonClickListener = onClickListener;
        mOnItemClickCallBack = onItemClickListener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memory_item_line, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MemoryEntry entry = mData.get(position);

        holder.bind(entry);

        holder.mContianer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickCallBack != null)
                    mOnItemClickCallBack.onClick(holder.mItem);
                else
                    return;

                TabLayout tabLayout = ((Activity) mContext).findViewById(R.id.tab_layout);
                tabLayout.getTabAt(0).select();
            }
        });

        holder.mTextViewValueShower.setText(entry.getValue() + "");
        holder.mTextViewValueShower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickCallBack != null)
                    mOnItemClickCallBack.onClick(holder.mItem);
                else
                    return;

                TabLayout tabLayout = ((Activity) mContext).findViewById(R.id.tab_layout);
                tabLayout.getTabAt(0).select();
            }
        });

        holder.mButtonDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onClick(R.id.button_delete_memory_item, position);
            }
        });

        holder.mButtonAddToValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onClick(R.id.button_add_to_item_memory_value, position);
            }
        });

        holder.mButtonSubtractFromValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnButtonClickListener.onClick(R.id.button_item_subtract_from_memory, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private MemoryEntry mItem;
        private ConstraintLayout mContianer;
        private TextView mTextViewValueShower;
        private Button mButtonDeleteItem;
        private Button mButtonAddToValue;
        private Button mButtonSubtractFromValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mContianer = itemView.findViewById(R.id.memory_item_container);
            mTextViewValueShower = itemView.findViewById(R.id.text_view_memory_item_value_shower);
            mButtonDeleteItem = itemView.findViewById(R.id.button_delete_memory_item);
            mButtonAddToValue = itemView.findViewById(R.id.button_add_to_item_memory_value);
            mButtonSubtractFromValue = itemView.findViewById(R.id.button_item_subtract_from_memory);
        }

        public void bind(MemoryEntry item) {
            mItem = item;
        }
    }
}
