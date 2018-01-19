package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;

/**
 * Created by asus on 2017/10/5.
 */

public class Mine_JieBangActivity_1 extends BaseActivity implements View.OnClickListener{
    private LinearLayout mine_jiebang_back;
    private Button button_genghuan_phone;
    private TextView exchange_phone;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_jiebang_activity);
    }

    @Override
    public void initView() {
        mine_jiebang_back= (LinearLayout) findViewById(R.id.mine_jiebang_back);
        mine_jiebang_back.setOnClickListener(this);
        button_genghuan_phone= (Button) findViewById(R.id.button_genghuan_phone);
        button_genghuan_phone.setOnClickListener(this);
        exchange_phone= (TextView) findViewById(R.id.exchange_phone);
        exchange_phone.setText(BPApplication.getInstance().getMobile());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_jiebang_back:
                finish();
                break;
            case R.id.button_genghuan_phone:
                Intent intent = new Intent(Mine_JieBangActivity_1.this,Mine_YanZhengPhone_Activity_2.class);
                startActivity(intent);
                break;
        }
    }
}
