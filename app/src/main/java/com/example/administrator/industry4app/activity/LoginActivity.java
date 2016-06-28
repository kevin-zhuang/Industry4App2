package com.example.administrator.industry4app.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.util.Constant;
import com.example.administrator.industry4app.util.ReceiveDataCallback;
import com.example.administrator.industry4app.util.SpHelper;
import com.example.administrator.industry4app.util.TcpHelper;
import com.example.administrator.industry4app.util.ToastUtil;
import com.example.administrator.industry4app.util.WaitDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUser;
    private EditText etPassword;
    private Button btnSure;
    private Button btnCancel;

    private ActionBar actionBar;
    private TcpHelper tcpHelper;
    private int loginStatus = -1;
    private long exitTime = 0;
    private SpHelper spHelper;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);//
        setContentView(R.layout.activity_login);
        findView();
        loginStatus = Constant.LOGIN_SUCCESS;
        tcpHelper = TcpHelper.getInstance();
        spHelper = SpHelper.getInstance(LoginActivity.this);
        if (!TextUtils.isEmpty(spHelper.getSpUserText()) && !TextUtils.isEmpty(spHelper.getSpPasswordText())) {
            etUser.setText(spHelper.getSpUserText());
            etPassword.setText(spHelper.getSpPasswordText());
        }
        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void findView() {
        etUser = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSure = (Button) findViewById(R.id.btnSure);
        btnCancel = (Button) findViewById(R.id.btnCancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSure:
                // TODO: 2016/6/21 登陆判断处理，最好加上sp
                try {
                    tcpHelper.sendData(toJsonData(etUser.getText().toString(), etPassword.getText().toString()).getBytes());
                    WaitDialog.showDialog(LoginActivity.this, "正在登陆", 5000);
                    tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
                        @Override
                        public void onReceiveData(String receiveData) {

                            String cmdLogin = null;
                            String loginStatu = null;
                            try {
                                JSONObject jsonObject = new JSONObject(receiveData);
                                cmdLogin = jsonObject.getString(Constant.KEY_LOGIN);
                                loginStatu = jsonObject.getString(Constant.KEY_LOGIN_STATUS);
                                Log.e(TAG, "cmdLogin=" + cmdLogin + " loginStatu=" + loginStatu);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (Constant.CMD_LOGIN.equalsIgnoreCase(cmdLogin)) {
//                                {"login":"cmd_login","login_status":"1"}
//                                {"plan":"cmd_plan","plan_order_id":"订单号","plan_product_model":"产品型号","plan_order_number":"订单数量","plan_charge_man":"负责人员","plan_start_date":"生产日期","plan_finish_date":"完成日期","plan_id":"id","plan_status":"状态"}



                                if (loginStatu != null && loginStatu.equalsIgnoreCase(String.valueOf(loginStatus))) {
                                    spHelper.saveSpUserText(etUser.getText().toString());
                                    spHelper.saveSpPasswordText(etPassword.getText().toString());
                                    WaitDialog.immediatelyDismiss();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    loginStatus = -1;
                                    finish();
                                } else {
                                    if (loginStatus != -1) {
                                        if(loginStatu != null && loginStatu.equalsIgnoreCase(String.valueOf(Constant.LOGIN_FAIL))){
                                            ToastUtil.customToast(LoginActivity.this, "登陆失败");
                                            WaitDialog.immediatelyDismiss();
                                        }


                                    }
                                }
                            }


                        }
                    });
                } catch (Exception e) {
                    ToastUtil.customToast(LoginActivity.this, "服务器没有开启！");
                    Log.e(TAG, "e=" + e.toString());
                }


                break;
            case R.id.btnCancel:
                if (tcpHelper != null) {
                    tcpHelper.closeTcp();
                }
                finish();
                break;

        }
    }

    private String toJsonData(String user, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constant.KEY_LOGIN, Constant.CMD_LOGIN);
            jsonObject.put(Constant.KEY_USER, user);
            jsonObject.put(Constant.KEY_PWD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtil.customToast(LoginActivity.this, "再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                if (tcpHelper != null) {
                    tcpHelper.closeTcp();
                }
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
