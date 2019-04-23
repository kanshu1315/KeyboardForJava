package com.kanshu.demo;

import android.os.Bundle;
import android.widget.EditText;

import com.kanshu.keyboard.Keyboard;
import com.kanshu.keyboard.KeyboardCenter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.et_text1);
        editText2 = findViewById(R.id.et_text2);
        editText3 = findViewById(R.id.et_text3);
        Keyboard.bind(editText1, KeyboardCenter.InputMethod.ONLY_NUMBER);
        Keyboard.bind(editText2, KeyboardCenter.InputMethod.B_NUMBER);
        Keyboard.bind(editText3, KeyboardCenter.InputMethod.C_NUMBER);
    }
}
