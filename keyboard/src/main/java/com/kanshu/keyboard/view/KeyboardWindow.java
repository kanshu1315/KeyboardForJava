package com.kanshu.keyboard.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.kanshu.keyboard.KeyboardCenter;
import com.kanshu.keyboard.R;
import com.kanshu.keyboard.model.Key;
import com.kanshu.keyboard.util.DeviceUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * @author yuquanmao
 * <p>
 * <p>
 * 键盘的窗体
 */
public class KeyboardWindow extends PopupWindow {


    private static final String TAG = "KeyboardWindow";
    private KeyboardAdapter mKeyAdapter;

    public KeyboardWindow(@NonNull final Context context, KeyboardCenter.InputMethod inputMethod, @NonNull final OnKeyDownListener listener) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.layout_pop_keyboard, null);
        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        //this.setWidth(DeviceUtils.getScreenSizePixels(context)[0]/2);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xCC000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框

        RecyclerView mKeyRecView = view.findViewById(R.id.rv_keyboard);

        mKeyAdapter = new KeyboardAdapter(inputMethod.getKeyList(), new KeyboardAdapter.OnKeyDownListener() {
            @Override
            public void onKeyDown(Button item, Key key) {
                listener.onKeyDown(KeyboardWindow.this, item, key);
            }
        });
        GridLayoutManager mRecLayoutManager = new GridLayoutManager(context, inputMethod.getSpanCount());
        mKeyRecView.setLayoutManager(mRecLayoutManager);
        //基本的分割线
        mKeyRecView.addItemDecoration(new KeyboardDecoration());
        mKeyRecView.getItemAnimator().setAddDuration(0);
        mKeyRecView.getItemAnimator().setChangeDuration(0);
        mKeyRecView.getItemAnimator().setMoveDuration(0);
        mKeyRecView.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) mKeyRecView.getItemAnimator()).setSupportsChangeAnimations(false);

        mKeyRecView.setAdapter(mKeyAdapter);
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = v.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    public void changeInputMethod(KeyboardCenter.InputMethod method) {
        mKeyAdapter.changeData(method.getKeyList());
    }

    public void changeRangeKeys(int from, List<Key> partKeys) {
        mKeyAdapter.changeRangeData(from, partKeys);
    }


    public interface OnKeyDownListener {

        /**
         * 点击事件
         *
         * @param window 窗口
         * @param item   被点击的按钮
         * @param key    按钮对应的键
         */
        void onKeyDown(KeyboardWindow window, Button item, Key key);

    }

}
