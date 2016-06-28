package com.example.administrator.industry4app.activity;

import android.app.ActionBar;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.db.DBCurd;
import com.example.administrator.industry4app.entity.PlanBean;
import com.example.administrator.industry4app.util.Constant;
import com.example.administrator.industry4app.util.PlanAdapter;
import com.example.administrator.industry4app.util.ReceiveDataCallback;
import com.example.administrator.industry4app.util.TcpHelper;
import com.example.administrator.industry4app.util.ToastUtil;
import com.example.administrator.industry4app.util.WaitDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private static final String TAG = "PlanActivity";
    private ListView lvPlan;
    private PlanAdapter planAdapter;
    private List<PlanBean> list = new ArrayList<>();

    private TcpHelper tcpHelper;
    private SwipeRefreshLayout sl;
    private Handler handler = new Handler();
    private DBCurd dbCurd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ActionBar actionBar ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("计划任务");
            }
        }
        sl = (SwipeRefreshLayout)findViewById(R.id.slPlan);
        dbCurd = DBCurd.getInstance(PlanActivity.this);
        dbCurd.delAllPlanData();
        tcpHelper = TcpHelper.getInstance();
//        try {
//            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_PLAN, Constant.CMD_PLAN, Constant.KEY_GET_PLAN_DATA, "1").getBytes());
//            WaitDialog.showDialog(PlanActivity.this, "请稍候", 5000);
//            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
//                @Override
//                public void onReceiveData(String receiveData) {
//                    WaitDialog.immediatelyDismiss();
//                    String cmd_plan = null;
//                    String plan_order_id = null;
//                    String plan_product_model = null;
//                    String plan_order_number = null;
//                    String plan_charge_man = null;
//                    String plan_start_date = null;
//                    String plan_finish_date = null;
//                    String plan_id = null;
//                    String plan_status = null;
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(receiveData);
//                        cmd_plan = jsonObject.getString("plan");
//                        plan_order_id = jsonObject.getString("plan_order_id");
//                        plan_product_model = jsonObject.getString("plan_product_model");
//                        plan_order_number = jsonObject.getString("plan_order_number");
//                        plan_charge_man = jsonObject.getString("plan_charge_man");
//                        plan_start_date = jsonObject.getString("plan_start_date");
//                        plan_finish_date = jsonObject.getString("plan_finish_date");
//                        plan_id = jsonObject.getString("plan_id");
//                        plan_status = jsonObject.getString("plan_status");
//                        Log.e(TAG,"cmd_plan="+cmd_plan+" plan_order_id="+plan_order_id);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    if (cmd_plan != null && cmd_plan.equalsIgnoreCase(Constant.CMD_PLAN)) {
//                        PlanBean planBean = new PlanBean();
//                        planBean.setStartDate(plan_start_date);
//                        planBean.setStatus(plan_status);
//                        planBean.setFinishDate(plan_finish_date);
//                        planBean.setProductModel(plan_product_model);
//                        planBean.setChargeMan(plan_charge_man);
//                        planBean.setOrderId(plan_order_id);
//                        planBean.setOrderNumber(plan_order_number);
//                        planBean.setPlanId(plan_id);
//                        dbCurd.addPlanData(planBean);
//                        list = dbCurd.selectAllPlanData();
//                        planAdapter = new PlanAdapter(PlanActivity.this, list);
//                        lvPlan.setAdapter(planAdapter);
////                        list.add(planBean);
////                        planAdapter.notifyDataSetChanged();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            ToastUtil.showToast(PlanActivity.this, "服务器可能断开连接了！");
//        }

        sendAndSetting();
        WaitDialog.showDialog(PlanActivity.this, "请稍候", 5000);
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dbCurd.delAllPlanData();
                sendAndSetting();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sl.setRefreshing(false);
                    }
                }, 2000);
            }
        });


//        planBean.setOrderId("001");
//        planBean.setChargeMan("张三");
//        planBean.setFinishDate("2016-02-02");
//        planBean.setOrderNumber("20");
//        planBean.setPlanId(0);
//        planBean.setProductModel("螺丝");
//        planBean.setStatus("待加工");
//        planBean.setStartDate("2016-01-01");
//
//        planBean2.setOrderId("002");
//        planBean2.setChargeMan("李四");
//        planBean2.setFinishDate("2016-04-04");
//        planBean2.setOrderNumber("20");
//        planBean2.setPlanId(1);
//        planBean2.setProductModel("螺丝2");
//        planBean2.setStatus("待加工");
//        planBean2.setStartDate("2016-03-03");
//
//        planBean3.setOrderId("003");
//        planBean3.setChargeMan("王五");
//        planBean3.setFinishDate("2016-06-06");
//        planBean3.setOrderNumber("20");
//        planBean3.setPlanId(2);
//        planBean3.setProductModel("螺丝2");
//        planBean3.setStatus("待加工");
//        planBean3.setStartDate("2016-05-05");
//
//        list.add(planBean);
//        list.add(planBean2);
//        list.add(planBean3);

//        int screenWidth;//屏幕宽度
//        int screenHeight;//屏幕高度
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        screenWidth = display.getWidth();
//        screenHeight = display.getHeight();

        lvPlan = (ListView) findViewById(R.id.lvPlan);
        planAdapter = new PlanAdapter(PlanActivity.this, list);
        lvPlan.setAdapter(planAdapter);
    }

    private void sendAndSetting() {
        try {
            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_PLAN, Constant.CMD_PLAN, Constant.KEY_GET_PLAN_DATA, "1").getBytes());
            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
                @Override
                public void onReceiveData(String receiveData) {
                    WaitDialog.immediatelyDismiss();
                    String cmd_plan = null;
                    String plan_order_id = null;
                    String plan_product_model = null;
                    String plan_order_number = null;
                    String plan_charge_man = null;
                    String plan_start_date = null;
                    String plan_finish_date = null;
                    String plan_id = null;
                    String plan_status = null;

                    try {
                        JSONObject jsonObject = new JSONObject(receiveData);
                        cmd_plan = jsonObject.getString("plan");
                        plan_order_id = jsonObject.getString("plan_order_id");
                        plan_product_model = jsonObject.getString("plan_product_model");
                        plan_order_number = jsonObject.getString("plan_order_number");
                        plan_charge_man = jsonObject.getString("plan_charge_man");
                        plan_start_date = jsonObject.getString("plan_start_date");
                        plan_finish_date = jsonObject.getString("plan_finish_date");
                        plan_id = jsonObject.getString("plan_id");
                        plan_status = jsonObject.getString("plan_status");
                        Log.e(TAG, "cmd_plan=" + cmd_plan + " plan_order_id=" + plan_order_id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (cmd_plan != null && cmd_plan.equalsIgnoreCase(Constant.CMD_PLAN)) {
                        PlanBean planBean = new PlanBean();
                        planBean.setStartDate(plan_start_date);
                        planBean.setStatus(plan_status);
                        planBean.setFinishDate(plan_finish_date);
                        planBean.setProductModel(plan_product_model);
                        planBean.setChargeMan(plan_charge_man);
                        planBean.setOrderId(plan_order_id);
                        planBean.setOrderNumber(plan_order_number);
                        planBean.setPlanId(plan_id);
                        dbCurd.addPlanData(planBean);
                        list = dbCurd.selectAllPlanData();
                        planAdapter = new PlanAdapter(PlanActivity.this, list);
                        lvPlan.setAdapter(planAdapter);
                    }
                }
            });
        }catch (Exception e){
            ToastUtil.showToast(PlanActivity.this, "服务器可能断开连接了！");
        }
    }

}
