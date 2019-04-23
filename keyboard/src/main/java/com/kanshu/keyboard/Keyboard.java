package com.kanshu.keyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.kanshu.keyboard.handler.KeyDownHandler;
import com.kanshu.keyboard.handler.IKeyDownHandler;
import com.kanshu.keyboard.model.Key;
import com.kanshu.keyboard.util.DeviceUtils;
import com.kanshu.keyboard.view.KeyboardWindow;

/**
 * @author yuquanmao
 * <p>
 * 提供自定义键盘的对外接口
 * <p>
 * <p>
 * 目前主要提供四种键盘：
 * 纯数字键盘
 * 含有大写字母B和数字的键盘
 * 含有大写字母C和数字的键盘
 * 含有小数点.和数字的键盘
 */
public class Keyboard {


    private static final String TAG = "Keyboard";


    private static final IKeyDownHandler DEFAULT_KEY_DOWN_HANDLER = new KeyDownHandler();


    @SuppressLint("ClickableViewAccessibility")
    public static void bind(final EditText target, final KeyboardCenter.InputMethod method, final IKeyDownHandler handler) {
        //阻止点击输入框时，弹出系统输入法
        target.setInputType(InputType.TYPE_NULL);

        target.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    InputMethodManager imm = (InputMethodManager) target.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null && imm.isActive()) {
                        imm.hideSoftInputFromWindow(target.getWindowToken(), 0);
                    }

                    final KeyboardWindow keyboardWindow = new KeyboardWindow(target.getContext(), method, new KeyboardWindow.OnKeyDownListener() {
                        @Override
                        public void onKeyDown(KeyboardWindow window, Button item, Key key) {
                            Log.i(TAG, "onKeyDown: " + key);
                            handler.handleKeyDown(window, target, item, key);

                        }
                    });
                    //获取焦点
                    target.requestFocus();
                    //计算键盘的弹出位置
                    int[] pos = calculatePopWindowPos(target, keyboardWindow.getContentView());
                    keyboardWindow.showAtLocation(v.getRootView(), Gravity.TOP | Gravity.START, pos[0], pos[1]);
                }
                return false;
            }
        });
    }

    public static void bind(final EditText target, final KeyboardCenter.InputMethod method) {
        bind(target, method, DEFAULT_KEY_DOWN_HANDLER);
    }

    public static void bind(KeyboardCenter.InputMethod method, EditText... targets) {
        for (EditText target : targets) {
            bind(target, method);
        }
    }

    public static void bindNumberPan(EditText... targets) {
        bind(KeyboardCenter.InputMethod.ONLY_NUMBER, targets);
    }

    public static void bindBNumberPan(EditText... targets) {
        bind(KeyboardCenter.InputMethod.B_NUMBER, targets);
    }

    public static void bindCNumberPan(EditText... targets) {
        bind(KeyboardCenter.InputMethod.C_NUMBER, targets);
    }

    public static void bindDotNumberPan(EditText... targets) {
        bind(KeyboardCenter.InputMethod.DOT_NUMBER, targets);
    }

    public static void bindEmailPan(EditText... targets) {
        bind(KeyboardCenter.InputMethod.DOT_NUMBER, targets);
    }


    public static void unbind() {

    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    private static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];

        // 获取锚点View在屏幕上的左上角坐标位置
        final int anchorLoc[] = new int[2];
        anchorView.getLocationOnScreen(anchorLoc);

        //锚点View的高和宽
        // 计算contentView的高宽
        final int anchorHeight = anchorView.getHeight();
        final int anchorWidth = anchorView.getWidth();
        // 获取屏幕的高宽
        final int[] screenSize = DeviceUtils.getScreenSizePixels(anchorView.getContext());
        final int screenWidth = screenSize[0];
        final int screenHeight = screenSize[1];

        // 计算contentView的高宽
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();

        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) { //如果下方空间不足,则向上弹
            windowPos[1] = anchorLoc[1] - windowHeight - 10;
        } else {
            windowPos[1] = anchorLoc[1] + anchorHeight + 10;
        }
        //判断需要与锚点View左对齐还是右对齐
        //原则：如果弹窗的宽度不超过锚点View的宽度，则左对齐；
        // 否则，锚点View的左右两侧，那侧更接近屏幕边缘，就和那侧对其。
        if (windowWidth <= anchorWidth) {
            //弹窗与锚点View左对齐
            windowPos[0] = anchorLoc[0];
        } else {
            if (anchorLoc[0] <= (screenWidth - anchorLoc[0] - anchorWidth)) {
                //如果锚点View的左侧距离屏幕边缘更近
                //弹窗与锚点View左对齐
                windowPos[0] = anchorLoc[0];
            } else {
                //弹窗与锚点View右对齐
                //windowPos[0] = anchorLoc[0] - (windowWidth - anchorWidth) + anchorView.getPaddingStart() + anchorView.getPaddingEnd();
                windowPos[0] = anchorLoc[0] - (windowWidth - anchorWidth) + anchorView.getPaddingStart();
            }
        }
        return windowPos;
    }


}
