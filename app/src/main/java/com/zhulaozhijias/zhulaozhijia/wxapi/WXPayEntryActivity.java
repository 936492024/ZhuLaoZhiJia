package com.zhulaozhijias.zhulaozhijia.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.activity.Mine_RechargeActiviy;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.Constants;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;

import de.greenrobot.event.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler,MainView {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;

	private MainPresenter mainPresenter;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);

		mainPresenter=new MainPresenter(this,this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {


	}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode == 0){
				EventBus.getDefault().post(new MainSendEvent("充值支付2"));//账户余额
				ToastUtil.showToast(WXPayEntryActivity.this,"支付成功");
				if(BPApplication.getInstance().getWeChat_1().equals("1")){
					EventBus.getDefault().post(new MainSendEvent("充值支付"));
					EventBus.getDefault().post(new MainSendEvent("充值支付2"));
				}else if(BPApplication.getInstance().getWeChat_2().equals("2")){
					finish();
					EventBus.getDefault().post(new MainSendEvent("随手捐支付"));
				}else if(BPApplication.getInstance().getWeChat_3().equals("3")){
					finish();
					EventBus.getDefault().post(new MainSendEvent("计划支付"));
				}else if(BPApplication.getInstance().getWeChat_4().equals("4")){
					finish();
					EventBus.getDefault().post(new MainSendEvent("打卡支付"));
				}
			}else if(resp.errCode == -1){
				ToastUtil.showToast(WXPayEntryActivity.this,"支付失败");
					BPApplication.getInstance().setWeChat_1("");
				    BPApplication.getInstance().setWeChat_2("");
				    BPApplication.getInstance().setWeChat_3("");
			}else if(resp.errCode == -2){
				ToastUtil.showToast(WXPayEntryActivity.this,"取消支付");
					BPApplication.getInstance().setWeChat_1("");
				    BPApplication.getInstance().setWeChat_2("");
				    BPApplication.getInstance().setWeChat_3("");
			}
			finish();
		}
	}

	@Override
	public void getView(String s) {

	}

	@Override
	public void postView(String s) {

	}

	@Override
	public void postViews(String s) {

	}

	@Override
	public void postViewss(String s) {

	}

	@Override
	public void postViewsss(String s) {

	}

	@Override
	public void postViewsss_1(String s) {

	}

	@Override
	public void postViewsss_2(String s) {

	}

	@Override
	public void fail(String s) {

	}

	@Override
	public void imgView(Bitmap bitmap) {

	}
}