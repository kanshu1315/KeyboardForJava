package com.kanshu.keyboard.handler;

import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.kanshu.keyboard.KeyboardCenter;
import com.kanshu.keyboard.model.Key;
import com.kanshu.keyboard.util.GsonHelper;
import com.kanshu.keyboard.view.KeyboardWindow;


public class KeyDownHandler implements IKeyDownHandler {

    private static final String TAG = "KeyDownHandler";

    private boolean TAG_LETTER_CASE = false;

    @Override
    public void handleKeyDown(KeyboardWindow window, EditText target, Button item, Key key) {
        switch (key.type) {
            case KeyboardCenter.KEY_TYPE_INPUT_CHAR:
                handleCharKeyDown(target, key);
                break;
            case KeyboardCenter.KEY_TYPE_OPERATOR:
                handleOperatorKeyDown(window, target, key);
                break;
            case KeyboardCenter.KEY_TYPE_SWITCH:
                handleSwitchKeyDown(window, item, key);
                break;
            default:
                break;

        }
    }

    protected void handleCharKeyDown(EditText target, Key key) {
        //如果是字符类型的键，则将其追加显式到输入框中
        target.append(key.text);
        //移动光标
        //target.setSelection(text.length());
    }

    protected void handleOperatorKeyDown(KeyboardWindow window, EditText target, Key key) {
        switch (key.id) {
            case KeyboardCenter.OPERATOR_KEY_ID_DEL:
                handleDel(target);
                break;
            case KeyboardCenter.OPERATOR_KEY_ID_CLEAR:
                handleClear(target);
                break;
            case KeyboardCenter.OPERATOR_KEY_ID_CONFIRM:
                handleConfirm(window);
                break;
            default:
                break;
        }
    }

    protected void handleSwitchKeyDown(KeyboardWindow window, Button item, Key key) {
        switch (key.id) {
            case KeyboardCenter.OPERATOR_KEY_ID_CHANGE_CASE:
                handleChangeCase(window, item);
                break;
            default:
                break;
        }
    }


    //删除键，删除输入框中的最后一个字符
    protected void handleDel(EditText target) {

        Editable editable = target.getText();
        if (editable.length() > 0) {
            StringBuilder stringBuilder = new StringBuilder(editable);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            target.setText(stringBuilder);
            //移动光标
            target.setSelection(stringBuilder.length());
        }
    }

    //清空键，清空输入框
    protected void handleClear(EditText target) {

        target.setText("");
        target.setSelection(0);
    }

    //确认键，隐藏键盘
    protected void handleConfirm(KeyboardWindow window) {

        if (window != null && window.isShowing()) {
            window.dismiss();
        }
    }


    public void handleChangeCase(KeyboardWindow window, Button item) {
       /* if (!TAG_LETTER_CASE) {
            Log.i(TAG, "handleChangeCase: 小写->大写");
            window.changeInputMethod(KeyboardCenter.InputMethod.LETTER_CAPITAL);
        } else {
            Log.i(TAG, "handleChangeCase: 大写->小写");
            window.changeInputMethod(KeyboardCenter.InputMethod.LETTER_LOWER);
        }

        TAG_LETTER_CASE = !TAG_LETTER_CASE;*/

        if (!TAG_LETTER_CASE) {
            Log.i(TAG, "handleChangeCase: 小写->大写");
            item.setText("小写");
            window.changeRangeKeys(0, GsonHelper.<Key>fromJson(KeyboardCenter.PART_CAPITAL_LETTER_JSON, Key[].class));
        } else {
            Log.i(TAG, "handleChangeCase: 大写->小写");
            item.setText("大写");
            window.changeRangeKeys(0, GsonHelper.<Key>fromJson(KeyboardCenter.PART_LOWER_LETTER_JSON, Key[].class));
        }
        TAG_LETTER_CASE = !TAG_LETTER_CASE;
    }


}
