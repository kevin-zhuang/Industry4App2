package com.example.administrator.industry4app.activity;

import android.app.ActionBar;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.util.Constant;
import com.example.administrator.industry4app.util.ReceiveDataCallback;
import com.example.administrator.industry4app.util.TcpHelper;
import com.example.administrator.industry4app.util.ToastUtil;
import com.example.administrator.industry4app.util.WaitDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class OPCActivity extends AppCompatActivity {

    private TcpHelper tcpHelper;
    private TextView tvOpcAgv;
    private TextView tvOpcProcess;
    private TextView tvOpcAssembly;
    private TextView tvOpcShunt;
    private TextView tvOpcWarehouse;
    private SwipeRefreshLayout slOpc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opc);
        findViews();
        ActionBar actionBar ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("OPC");
            }
        }

        tcpHelper = TcpHelper.getInstance();
//        try {
//            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_OPC, Constant.CMD_OPC, Constant.KEY_GET_OPC_DATA, "1").getBytes());
//            WaitDialog.showDialog(OPCActivity.this, "请稍候", 5000);
//            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
//                @Override
//                public void onReceiveData(String receiveData) {
//                    WaitDialog.immediatelyDismiss();
//
//
//
////                    {"opc":"cmd_opc", "opc_agv":"信息", "opc_process":"信息", "opc_assembly":"信息", "opc_shunt":"信息", "opc_warehouse":"信息"}
//
//                    String cmd_opc;
//                    String opc_agv;
//                    String opc_process;
//                    String opc_assembly;
//                    String opc_shunt;
//                    String opc_warehouse;
//
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(receiveData);
//                        cmd_opc = jsonObject.getString(Constant.KEY_OPC);
//                        if (cmd_opc.equalsIgnoreCase(Constant.CMD_OPC)) {
//                            opc_agv = jsonObject.getString("opc_agv");
//                            opc_process = jsonObject.getString("opc_process");
//                            opc_assembly = jsonObject.getString("opc_assembly");
//                            opc_shunt = jsonObject.getString("opc_shunt");
//                            opc_warehouse = jsonObject.getString("opc_warehouse");
//
//                            tvOpcAgv.setText(opc_agv);
//                            tvOpcProcess.setText(opc_process);
//                            tvOpcAssembly.setText(opc_assembly);
//                            tvOpcShunt.setText(opc_shunt);
//                            tvOpcWarehouse.setText(opc_warehouse);
//
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//            }
//        });
//    }
//
//    catch(Exception e)
//    {
//        ToastUtil.showToast(OPCActivity.this, "服务器可能断开连接了！");
//    }
        sendAndSetting();
        WaitDialog.showDialog(OPCActivity.this, "请稍候", 5000);
        slOpc.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendAndSetting();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slOpc.setRefreshing(false);
                    }
                }, 2000);
            }
        });

}

    private void sendAndSetting() {
        try {
            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_OPC, Constant.CMD_OPC, Constant.KEY_GET_OPC_DATA, "1").getBytes());
            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
                @Override
                public void onReceiveData(String receiveData) {
                    WaitDialog.immediatelyDismiss();



//                    {"opc":"cmd_opc", "opc_agv":"信息", "opc_process":"信息", "opc_assembly":"信息", "opc_shunt":"信息", "opc_warehouse":"信息"}

                    String cmd_opc;
                    String opc_agv;
                    String opc_process;
                    String opc_assembly;
                    String opc_shunt;
                    String opc_warehouse;


                    try {
                        JSONObject jsonObject = new JSONObject(receiveData);
                        cmd_opc = jsonObject.getString(Constant.KEY_OPC);
                        if (cmd_opc.equalsIgnoreCase(Constant.CMD_OPC)) {
                            opc_agv = jsonObject.getString("opc_agv");
                            opc_process = jsonObject.getString("opc_process");
                            opc_assembly = jsonObject.getString("opc_assembly");
                            opc_shunt = jsonObject.getString("opc_shunt");
                            opc_warehouse = jsonObject.getString("opc_warehouse");

                            tvOpcAgv.setText(opc_agv);
                            tvOpcProcess.setText(opc_process);
                            tvOpcAssembly.setText(opc_assembly);
                            tvOpcShunt.setText(opc_shunt);
                            tvOpcWarehouse.setText(opc_warehouse);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        catch(Exception e)
        {
            ToastUtil.showToast(OPCActivity.this, "服务器可能断开连接了！");
        }
    }

    private void findViews() {
        tvOpcAgv = (TextView) findViewById(R.id.tvOpcAgv);
        tvOpcProcess = (TextView) findViewById(R.id.tvOpcProcess);
        tvOpcAssembly = (TextView) findViewById(R.id.tvOpcAssembly);
        tvOpcShunt = (TextView) findViewById(R.id.tvOpcShunt);
        tvOpcWarehouse = (TextView) findViewById(R.id.tvOpcWarehouse);
        slOpc = (SwipeRefreshLayout)findViewById(R.id.slOpc);
    }
}
