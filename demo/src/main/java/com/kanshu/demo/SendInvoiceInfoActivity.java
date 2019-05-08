package com.kanshu.demo;


import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;


import com.kanshu.keyboard.Keyboard;
import com.kanshu.keyboard.KeyboardCenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;


/**
 * 填写电子发票信息的页面基类
 *
 * @author yuquanmao
 */

public class SendInvoiceInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "InvoiceInfoSendActivity";
    //抬头类型
    RadioGroup rgInvoiceType;
    //发票抬头（公司、个人，必填）
    protected AppCompatEditText etInvoiceHeader;

    //纳税人识别号（公司，必填）
    protected AppCompatEditText etTaxCode;
    LinearLayout llTaxCode;
    //开户行（公司）
    protected AppCompatEditText etTaxBank;
    LinearLayout llTaxBank;
    //开户行帐号（公司）
    protected AppCompatEditText etTaxBankAcount;
    LinearLayout llTaxBankAcount;
    //电话（公司）
    protected AppCompatEditText etTaxTel;
    LinearLayout llTaxTel;
    //地址（公司）
    protected AppCompatEditText etTaxAddr;
    LinearLayout llTaxAddr;

    //邮箱（必填）
    protected AppCompatEditText etRecvEmail;
    //号码（必填）
    protected AppCompatEditText etRecvPhone;


    AppCompatButton btnSend;
    AppCompatButton btnCancel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_info_send);
        initView();
    }

    protected void initView() {

        rgInvoiceType = findViewById(R.id.rg_invoice_type);
        etInvoiceHeader = findViewById(R.id.et_invoice_header);
        etTaxTel = findViewById(R.id.et_tax_tel);
        etTaxCode = findViewById(R.id.et_tax_code);
        etTaxBank = findViewById(R.id.et_tax_bank);
        etTaxBankAcount = findViewById(R.id.et_tax_bank_account);
        etTaxAddr = findViewById(R.id.et_tax_addr);
        etRecvEmail = findViewById(R.id.et_recv_email);
        etRecvPhone = findViewById(R.id.et_recv_phone);
        llTaxAddr = findViewById(R.id.ll_tax_addr);
        llTaxBank = findViewById(R.id.ll_tax_bank);
        llTaxBankAcount = findViewById(R.id.ll_tax_bank_account);
        llTaxTel = findViewById(R.id.ll_tax_tel);
        llTaxCode = findViewById(R.id.ll_tax_code);
        btnSend = findViewById(R.id.btn_send);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSend.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        etTaxBankAcount.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //发票抬头、开户行名称、地址 ，输入汉字 需要调用外部拼音输入法
        //开户行账号、电话、手机号 为纯数字
        Keyboard.bind(KeyboardCenter.InputMethod.ONLY_NUMBER, etTaxTel, etRecvPhone);
        //纳税人识别号为数字和字母混合键盘
        Keyboard.bind(etTaxCode, KeyboardCenter.InputMethod.NUMBER_LOWER_LETTER);
        //电子邮箱
        Keyboard.bind(etRecvEmail, KeyboardCenter.InputMethod.EMAIL);

        Keyboard.bind(etTaxBankAcount, KeyboardCenter.InputMethod.ONLY_NUMBER, true);

        rgInvoiceType.setOnCheckedChangeListener((group, checkedId) -> changeInfoView(checkedId));

        etInvoiceHeader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG, "beforeTextChanged:" + s);
            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "onTextChanged:" + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(TAG, "afterTextChanged:" + s);
                //只有公司抬头时，才提供税号检索功能
                int invoiceTypeId = rgInvoiceType.getCheckedRadioButtonId();
                if (invoiceTypeId == R.id.rbtn_company_head) {
                }
            }
        });


    }


    /**
     * 如果是个人抬头，需要隐藏：纳税人识别号、开户行、开户行帐号、电话、地址
     *
     * @param checkedId
     */
    private void changeInfoView(int checkedId) {
        int vFlag = checkedId == R.id.rbtn_company_head ? View.VISIBLE : View.INVISIBLE;

        //公司抬头提供发票信息检索功能,个人及事业单位不提供
        llTaxCode.setVisibility(vFlag);
        llTaxAddr.setVisibility(vFlag);
        llTaxBank.setVisibility(vFlag);
        llTaxBankAcount.setVisibility(vFlag);
        llTaxTel.setVisibility(vFlag);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                Log.i(TAG, "onClick: etTaxBankAccount.text=" + etTaxBankAcount.getText());
                break;
            case R.id.btn_cancel:
                break;
            default:
        }
    }
}
