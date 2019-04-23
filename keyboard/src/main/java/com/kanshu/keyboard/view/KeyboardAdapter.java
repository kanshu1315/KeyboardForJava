package com.kanshu.keyboard.view;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kanshu.keyboard.KeyboardCenter;
import com.kanshu.keyboard.model.Key;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.ViewHolder> {


    private List<Key> mKeys;
    private OnKeyDownListener mOnKeyDownListener;

    public KeyboardAdapter(List<Key> keys, OnKeyDownListener listener) {
        this.mKeys = keys;
        mOnKeyDownListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Button btnKey = new Button(viewGroup.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnKey.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        btnKey.setLayoutParams(layoutParams);
        btnKey.setAllCaps(false);
        if (viewType == KeyboardCenter.KEY_TYPE_HOLDER) {
            btnKey.setVisibility(View.INVISIBLE);
        }
        return new ViewHolder(btnKey);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Key key = mKeys.get(position);
        viewHolder.btnKey.setText(key.text);
        viewHolder.btnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnKeyDownListener != null)
                    mOnKeyDownListener.onKeyDown(viewHolder.btnKey, key);
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        return mKeys.get(position).type;
    }

    @Override
    public int getItemCount() {
        return mKeys.size();
    }


    public void changeData(List<Key> newKeys) {
        if (newKeys != null) {
            int previousSize = mKeys.size();
            mKeys.clear();
            notifyItemRangeRemoved(0, previousSize);
            mKeys.addAll(newKeys);
            notifyItemRangeInserted(0, newKeys.size());
        }
    }

    public void changeRangeData(int from, List<Key> partKeys) {
        if (partKeys != null) {
         /*   List<Key> otherKeys = mKeys.subList(partKeys.size(),mKeys.size());
            partKeys.addAll(partKeys.size(),otherKeys);*/
            for (int i = from, j = 0; j < partKeys.size(); i++, j++) {
                mKeys.get(i).text = partKeys.get(j).text;

            }
            notifyItemRangeChanged(from, partKeys.size());
        }


    }

    public interface OnKeyDownListener {

        /**
         * 点击事件
         *
         * @param item 被点击的按钮
         * @param key  按钮对应的键
         */
        void onKeyDown(Button item, Key key);

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        Button btnKey;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnKey = (Button) itemView;
        }
    }

}
