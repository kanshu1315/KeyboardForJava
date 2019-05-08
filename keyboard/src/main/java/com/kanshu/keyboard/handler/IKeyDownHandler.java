package com.kanshu.keyboard.handler;

import android.widget.Button;
import android.widget.EditText;

import com.kanshu.keyboard.model.Key;
import com.kanshu.keyboard.view.KeyboardWindow;

public interface IKeyDownHandler {


    void handleKeyDown(KeyboardWindow window, EditText target, Button item, Key key, boolean asPassword);

}
