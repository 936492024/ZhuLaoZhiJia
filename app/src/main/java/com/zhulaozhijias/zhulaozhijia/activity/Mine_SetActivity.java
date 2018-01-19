package com.zhulaozhijias.zhulaozhijia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.ListView_MineSet_Adapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.HeaderPresenter;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.service.DemoService;
import com.zhulaozhijias.zhulaozhijia.view.IHeaderView;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.JtFtConvert;
import com.zhulaozhijias.zhulaozhijia.widgets.ListViewForScrollView;
import com.zhulaozhijias.zhulaozhijia.widgets.PickerView;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;
import com.zhulaozhijias.zhulaozhijia.widgets.updata.CustomVersionDialogActivity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;


/**
 * Created by asus on 2017/9/27.
 */

public class Mine_SetActivity extends BaseActivity implements View.OnClickListener,IHeaderView,MainView{
    private MainPresenter mainPresenter;
    private RelativeLayout login_out;
    private Intent intent;
    private LinearLayout mine_set_back;
    private TextView tianjia,mine_idcard,mine_phone,mine_name,version_name;
    private RelativeLayout mine_set_shouqi,version_upload;
    private ImageView mine_set_tubiao,mine_set_heading;
    private RelativeLayout mine_set_down,mine_set_down_2,make_password_rt,mine_jiebangshouji,mine_set_heading_rt;
    private int a=1;
    private ToastUtil toastUtil;
    private String imagePath;
    private static final int REQUEST_CODE = 0x00000011;
    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    private TextView mine_set_edit_1;
    private EditText mine_set_edit_2,mine_set_edit_3;
    private HeaderPresenter headerPresenter;
    private String relation="本人";
    private ListViewForScrollView mine_listviewforscrollView;
    private ListView_MineSet_Adapter listView_mineSet_adapter;
    private JSONArray jsonArray1,jsonArray2;
    private String member_id,web,intro,name;
    //身份证正则
    private String id_regular="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    private JtFtConvert jtFtConvert=new JtFtConvert();
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_set);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        headerPresenter=new HeaderPresenter(this,this);
        mine_set_shouqi= (RelativeLayout) findViewById(R.id.mine_set_shouqi);
        mine_set_shouqi.setOnClickListener(this);
        mine_set_down= (RelativeLayout) findViewById(R.id.mine_set_down);
        mine_set_down_2= (RelativeLayout) findViewById(R.id.mine_set_down_2);
        mine_set_tubiao= (ImageView) findViewById(R.id.mine_set_tubiao);
        tianjia= (TextView) findViewById(R.id.tianjia);
        tianjia.setOnClickListener(this);
        login_out= (RelativeLayout) findViewById(R.id.login_out);
        login_out.setOnClickListener(this);
        mine_set_back= (LinearLayout) findViewById(R.id.mine_set_back);
        mine_set_back.setOnClickListener(this);
        mine_listviewforscrollView= (ListViewForScrollView) findViewById(R.id.mine_listviewforscrollView);
        mine_idcard= (TextView) findViewById(R.id.mine_idcard);
        mine_name= (TextView) findViewById(R.id.mine_name);
        mine_phone= (TextView) findViewById(R.id.mine_phone);
        mine_name.setText(BPApplication.getInstance().getName());
        version_name= (TextView) findViewById(R.id.version_name);
        version_upload= (RelativeLayout) findViewById(R.id.version_upload);
        version_upload.setOnClickListener(this);
        String versionName = null;
        try {
            versionName = this.getPackageManager().getPackageInfo(this.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version_name.setText("V"+versionName);
        String Card_id=BPApplication.getInstance().getCard_Id();

        if(!TextUtils.isEmpty(BPApplication.getInstance().getCard_Id()) && BPApplication.getInstance().getCard_Id().length() > 10 ){
                    String show_id = Card_id.substring(0, 6) + "********" + Card_id.substring(14,18);
                    mine_idcard.setText(show_id);
        }else {
            mine_idcard.setText(BPApplication.getInstance().getCard_Id());
        }
        if(!TextUtils.isEmpty(BPApplication.getInstance().getMobile()) && BPApplication.getInstance().getMobile().length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < BPApplication.getInstance().getMobile().length(); i++) {
                char c = BPApplication.getInstance().getMobile().charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            mine_phone.setText(sb.toString());
        }
        make_password_rt= (RelativeLayout) findViewById(R.id.make_password_rt);
        make_password_rt.setOnClickListener(this);
        mine_jiebangshouji= (RelativeLayout) findViewById(R.id.mine_jiebangshouji);
        mine_jiebangshouji.setOnClickListener(this);
        mine_set_heading= (ImageView) findViewById(R.id.mine_set_heading);
        Glide.with(Mine_SetActivity.this).load(BPApplication.getInstance().getHeadimgurl()).placeholder(R.mipmap.portrait)
                .error(R.mipmap.portrait) .into(mine_set_heading);
        mine_set_heading_rt= (RelativeLayout) findViewById(R.id.mine_set_heading_rt);
        mine_set_heading_rt.setOnClickListener(this);
        mine_set_edit_1= (TextView) findViewById(R.id.mine_set_edit_1);
        mine_set_edit_1.setOnClickListener(this);
        mine_set_edit_2= (EditText) findViewById(R.id.mine_set_edit_2);
        mine_set_edit_3= (EditText) findViewById(R.id.mine_set_edit_3);
        member_id = BPApplication.getInstance().getMember_Id();
        alldata();
    }


    public void alldata(){
        Map<String,String> map = new HashMap<>();
        map.put("member_id",member_id);
        map.put("secret",CreateMD5.getMd5(member_id+"z!l@z#j$"));
        mainPresenter.wodes(SystemConstant.GEREN_ZHONGXIN.Mine_SEL_BETWEEN,map);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_out:
                intent=new Intent(Mine_SetActivity.this,LoginActivity.class);
                intent.putExtra("LoginActivity","LoginActivity");
                startActivity(intent);
                break;
            case R.id.mine_set_back:
                finish();
                break;
            case R.id.tianjia://保存
                if(TextUtils.isEmpty(mine_set_edit_1.getText().toString())){
                    toastUtil.showToast(Mine_SetActivity.this,"请输入关系");
                    return;
                }
                if(TextUtils.isEmpty( mine_set_edit_2.getText().toString())){
                    toastUtil.showToast(Mine_SetActivity.this,"请输入姓名");
                    return;
                }
                if(!isNameric(mine_set_edit_2.getText().toString())){
                    toastUtil.showToast(Mine_SetActivity.this,"姓名必须为简体中文");
                    return;
                }
                if(mine_set_edit_2.getText().toString().length()<2){
                    toastUtil.showToast(Mine_SetActivity.this,"姓名不得少于两个字");
                    return;
                }
                if(mine_set_edit_2.getText().toString().length()>4){
                    toastUtil.showToast(Mine_SetActivity.this,"姓名不得大于四个字");
                    return;
                }
                mine_set_edit_2.setText(jtFtConvert.convert(mine_set_edit_2.getText().toString(),0));
                if(TextUtils.isEmpty(mine_set_edit_3.getText().toString())){
                    toastUtil.showToast(Mine_SetActivity.this,"请输入身份证号");
                    return;
                }
                Pattern pattern = Pattern.compile(id_regular);
                Matcher matcher = pattern.matcher(mine_set_edit_3.getText().toString());
                boolean b = matcher.matches();
                if(!b){
                    toastUtil.showToast(Mine_SetActivity.this,"身份证号输入不正确");
                    return;
                }
                Map<String,String> map = new HashMap<>();
                map.put("member_id",member_id);
                map.put("between",mine_set_edit_1.getText().toString());
                map.put("relatives",mine_set_edit_2.getText().toString());
                map.put("card_id",mine_set_edit_3.getText().toString());
                map.put("secret", CreateMD5.getMd5(mine_set_edit_1.getText().toString()+
                        mine_set_edit_3.getText().toString()+member_id+mine_set_edit_2.getText().toString()+"z!l@z#j$"));
                mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_BETWEEN,map);
                break;
            case R.id.mine_set_shouqi:
                if(a==1) {
                    mine_set_down.setVisibility(View.VISIBLE);
                    mine_set_down_2.setVisibility(View.VISIBLE);
                    mine_set_tubiao.setImageResource(R.mipmap.arrows);
                    a=0;
                }else if(a==0){
                    mine_set_down.setVisibility(View.GONE);
                    mine_set_down_2.setVisibility(View.GONE);
                    mine_set_tubiao.setImageResource(R.mipmap.arrowright);
                    a=1;
                }
                break;
            case R.id.make_password_rt:
                Intent intenst = new Intent(Mine_SetActivity.this,Make_PasswordActivity.class);
                startActivity(intenst);
                break;
            case R.id.mine_jiebangshouji:
                Intent intent  = new Intent(Mine_SetActivity.this,Mine_JieBangActivity_1.class);
                startActivity(intent);
                break;
            case R.id.mine_set_heading_rt:
                //头像修改
//                ImageSelectorUtils.openPhoto(Mine_SetActivity.this, REQUEST_CODE, true, 0);
                headerPresenter.showHeadDialog();
                break;
            case R.id.mine_set_edit_1:
                game_clock_pay_dialog();
                break;
            case R.id.version_upload:
                try {
                    int versionCode = Mine_SetActivity.this.getPackageManager().getPackageInfo("com.zhulaozhijias.zhulaozhijia", 0).versionCode;
                    HttpParams httpParams = new HttpParams();
                    httpParams.put("name","APP");
                    httpParams.put("version",versionCode+"");
                    httpParams.put("secret", CreateMD5.getMd5("APP"+versionCode+"z!l@z#j$"));
                    VersionParams.Builder builder = new VersionParams.Builder()
                            .setRequestUrl("http://hbhz.sesdf.org/update/check")
                            .setRequestMethod(HttpRequestMethod.POST)
                            .setRequestParams(httpParams)
                            .setService(DemoService.class);
                    stopService(new Intent(this, DemoService.class));
                    CustomVersionDialogActivity.customVersionDialogIndex = 2;
                    builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
                    CustomVersionDialogActivity.isCustomDownloading = false;
                    builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
                    CustomVersionDialogActivity.isForceUpdate = false;
                    builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
                    AllenChecker.startVersionCheck(this, builder.build());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 点击取消按钮
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case 2:
                Uri uri = data.getData();
                headerPresenter.startPhotoZoom(uri);
                break;
            case 3:
                File file = new File(Environment.getExternalStorageDirectory() + "/" + "iMon.jpg");
                headerPresenter.startPhotoZoom(Uri.fromFile(file));
                break;
            case 4:
                if (data != null) {
                    headerPresenter.setView(data);
                }
                break;
        }
    }

    @Override
    public void setHeaderBitmap(final Bitmap Imagpath) {
        Map<String ,String> map = new HashMap<>();
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("base64","data:image/jpg;base64,"+bitmapToBase64(Imagpath));
        map.put("secret",CreateMD5.getMd5(("data:image/jpg;base64,"+bitmapToBase64(Imagpath))+BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.wodesss(SystemConstant.GEREN_ZHONGXIN.Mine_USER_IMG,map);
    }
    public  String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    @SuppressWarnings("static-access")
    public void game_clock_pay_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Mine_SetActivity.this);
        View view = inflater.inflate(R.layout.sex_select_dialog, null);
        final Dialog dialog = new Dialog(Mine_SetActivity.this);
        final PickerView wv_birth_month= (PickerView) view.findViewById(R.id.wv_birth_month);
        TextView btn_sex_cancel= (TextView) view.findViewById(R.id.btn_sex_cancel);
        TextView btn_sex_sure= (TextView) view.findViewById(R.id.btn_sex_sure);
        List<String> mDatas=new ArrayList<>();
        mDatas.add("本人");
        mDatas.add("父亲");
        mDatas.add("母亲");
        mDatas.add("配偶");
        mDatas.add("子女");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  (display.getWidth()); // 设置宽度
        lp.height=getResources().getDimensionPixelSize(R.dimen.sex_select_dialog_height);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.mainfstyle);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        wv_birth_month.setData(mDatas);
        wv_birth_month.setSelected("本人");
        wv_birth_month.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                relation=text;
            }
        });
        btn_sex_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mine_set_edit_1.setText(relation);
                dialog.cancel();
            }
        });
        btn_sex_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject =JSONObject.fromObject(s);
                jsonArray1 = new JSONArray();
                if(jsonObject.getString("success").equals("true")){
                  JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("date"));
                    for(int i=0;i<jsonArray.size();i++){
                        jsonArray1.add(jsonArray.getJSONObject(i));
                    }
                    listView_mineSet_adapter=new ListView_MineSet_Adapter(Mine_SetActivity.this,jsonArray1,1);
                    mine_listviewforscrollView.setAdapter(listView_mineSet_adapter);
                    mine_set_edit_1.setText("");
                    mine_set_edit_2.setText("");
                    mine_set_edit_3.setText("");
                }else {
                    toastUtil.showToast(Mine_SetActivity.this,jsonObject.getString("msg"));
                }
            }
        });
    }

    @Override
    public void postViews(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray Array1 = JSONArray.fromObject(s);
                JSONArray Array2 = new JSONArray();
                for(int i=0;i<Array1.size();i++){
                    Array2.add(Array1.getJSONObject(i));
                }
                listView_mineSet_adapter=new ListView_MineSet_Adapter(Mine_SetActivity.this,Array2,1);
                mine_listviewforscrollView.setAdapter(listView_mineSet_adapter);
            }
        });

    }

    @Override
    public void postViewss(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                JSONObject jsonObject =JSONObject.fromObject(s);
//                if(jsonObject.getString("success").equals("true")){
//                    if(jsonObject.getString("update").equals("0")){//已经是最新版本
//                        toastUtil.showToast(Mine_SetActivity.this,"已是最新版本");
//                    }else if(jsonObject.getString("update").equals("1")){//不强制更新
//                        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("intro"));
//                        jsonArray2=new JSONArray();
//                        for(int i =0;i<jsonArray.size();i++){
//                            jsonArray2.add(jsonArray.getJSONObject(i));
//                        }
////                        VersionParams.Builder builder = new VersionParams.Builder();
////                        builder.setOnlyDownload(true)
////                                .setDownloadUrl(jsonObject.getString("web"))
////                                .setTitle(jsonObject.getString("title")+"版本更新")
////                                .setUpdateMsg("检测到新版本2\nzzz\nzzz\nzzz\nzzz\nzzz");
////                        AllenChecker.startVersionCheck(Mine_SetActivity.this, builder.build());
//                        updata_dialog(jsonArray2,jsonObject.getString("web"),jsonObject.getString("title"));
//                    }else {//强制更新
//                        if(android.os.Build.BRAND.equals("samsung")){
//                            samsung(jsonObject.getString("web"));
//                        }else {
//                            updata(jsonObject.getString("web"));
//                        }
//                    }
//                    jsonObject.getString("web");
//                }
            }
        });

    }

    @Override
    public void postViewsss(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                 JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    BPApplication.getInstance().setHeadimgurl(jsonObject.getString("filename"));
                    Glide.with(Mine_SetActivity.this).load(BPApplication.getInstance().getHeadimgurl()).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait) .into(mine_set_heading);
                    EventBus.getDefault().post(new MainSendEvent("Headimgurl"));
                }else {
                }
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public void samsung(String web){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(web);
            intent.setData(content_url);
            startActivity(intent);
    }

    public void updata_dialog(JSONArray jsonArray3,final String web,String version) {
        final LayoutInflater inflater = LayoutInflater.from(Mine_SetActivity.this);
        View view = inflater.inflate(R.layout.updata_dialog, null);
        final Dialog dialog = new Dialog(Mine_SetActivity.this);
        TextView cancel_load= (TextView) view.findViewById(R.id.cancel_load);
        TextView version_load = (TextView) view.findViewById(R.id.version_load);
        Button liji_load = (Button) view.findViewById(R.id.liji_load);
        ListView upload_listview = (ListView) view.findViewById(R.id.upload_listview);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  getResources().getDimensionPixelSize(R.dimen.upload_dialog_wight);
        lp.height=getResources().getDimensionPixelSize(R.dimen.upload_dialog_height);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        version_load.setText(version+"版本更新");
        cancel_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        upload_listview.setAdapter(new ListView_MineSet_Adapter(Mine_SetActivity.this,jsonArray3,2));
        liji_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(android.os.Build.BRAND.equals("samsung")){
                    samsung(web);
                }else {
//                    updata(web);
                }
                dialog.cancel();
            }
        });
    }

    public boolean isNameric(String str){
        Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5]+$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && data != null) {
//            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
//            String a =images.get(0);
//            Glide.with(Mine_SetActivity.this).load(new File(a)).into(mine_set_heading);
//        }
//    }
}
