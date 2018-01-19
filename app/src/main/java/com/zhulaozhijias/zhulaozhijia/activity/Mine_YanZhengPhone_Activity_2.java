package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

/**
 * Created by asus on 2017/10/5.
 */

public class Mine_YanZhengPhone_Activity_2 extends BaseActivity implements View.OnClickListener{
    private LinearLayout mine_yanzheng_phone_back;
    private EditText edittext_yanzheng_phone;
    private Button yanzheng_xiayibu;
    private ToastUtil toastUtil;
    private TextView exchange_phone_2;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_yanzhengphone_activity);
    }

    @Override
    public void initView() {
        mine_yanzheng_phone_back= (LinearLayout) findViewById(R.id.mine_yanzheng_phone_back);
        mine_yanzheng_phone_back.setOnClickListener(this);
        edittext_yanzheng_phone= (EditText) findViewById(R.id.edittext_yanzheng_phone);
        yanzheng_xiayibu= (Button) findViewById(R.id.yanzheng_xiayibu);
        yanzheng_xiayibu.setOnClickListener(this);
        exchange_phone_2= (TextView) findViewById(R.id.exchange_phone_2);
        exchange_phone_2.setText(BPApplication.getInstance().getMobile());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_yanzheng_phone_back:
                finish();
                break;
            case R.id.yanzheng_xiayibu:
                String phone=edittext_yanzheng_phone.getText().toString();
                if(!TextUtils.isEmpty(phone)){
                    Intent intent = new Intent(Mine_YanZhengPhone_Activity_2.this,Mine_YanZhengPhone_Activity_3.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }else {
                    toastUtil.showToast(Mine_YanZhengPhone_Activity_2.this,"输入的手机号为空");
                }
                break;
        }
    }
}
