package com.example.administrator.industry4app.activity;

import android.app.ActionBar;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.entity.PlanBean;
import com.example.administrator.industry4app.util.Constant;
import com.example.administrator.industry4app.util.ReceiveDataCallback;
import com.example.administrator.industry4app.util.TcpHelper;
import com.example.administrator.industry4app.util.ToastUtil;
import com.example.administrator.industry4app.util.WaitDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class AgvActivity extends AppCompatActivity {

    private static final String TAG = "AgvActivity";

    private TcpHelper tcpHelper;


    private TextView tvAgv1Status;
    private TextView tvAgv2Status;
    private TextView tvAgv1CurrentStation;
    private TextView tvAgv2CurrentStation;
    private TextView tvAgv1CurrentTask;
    private TextView tvAgv2CurrentTask;
    private TextView tvAgv1CurrentSingleTask;
    private TextView tvAgv2CurrentSingleTask;
    private ImageView ivAgvBg;
    private SwipeRefreshLayout slAgv;
    private int[][] imageBg={

//            1 2 == 0 0
//            1 3 == 0 1
//            1 4 == 0 2
//
//
//            2 1 == 1 0
//            2 3 == 1 1
//            2 4 == 1 2
//
//            3 1 == 2 0          4 1 == 3 0
//            3 2 == 2 1          4 2 == 3 1
//            3 4 == 2 2          4 3 == 3 2
//                                4 5 == 3 3
//
//
//         a>b   a b  = a-1 b-1
//         a<b   a b  = a-1 b-2

            {R.drawable.bg_101_202,R.drawable.bg_101_203,R.drawable.bg_101_204,R.drawable.bg_101_205,R.drawable.bg_101_206,
             R.drawable.bg_101_207,R.drawable.bg_101_208,R.drawable.bg_101_209,R.drawable.bg_101_210,R.drawable.bg_101_211},

            {R.drawable.bg_102_201,R.drawable.bg_102_203,R.drawable.bg_102_204,R.drawable.bg_102_205,R.drawable.bg_102_206,
             R.drawable.bg_102_207,R.drawable.bg_102_208,R.drawable.bg_102_209,R.drawable.bg_102_210,R.drawable.bg_102_211},

            {R.drawable.bg_103_201,R.drawable.bg_103_202,R.drawable.bg_103_204,R.drawable.bg_103_205,R.drawable.bg_103_206,
             R.drawable.bg_103_207,R.drawable.bg_103_208,R.drawable.bg_103_209,R.drawable.bg_103_210,R.drawable.bg_103_211},

            {R.drawable.bg_104_201,R.drawable.bg_104_202,R.drawable.bg_104_203,R.drawable.bg_104_205,R.drawable.bg_104_206,
             R.drawable.bg_104_207,R.drawable.bg_104_208,R.drawable.bg_104_209,R.drawable.bg_105_210,R.drawable.bg_106_211},

            {R.drawable.bg_105_201,R.drawable.bg_105_202,R.drawable.bg_105_203,R.drawable.bg_105_204,R.drawable.bg_105_206,
             R.drawable.bg_105_207, R.drawable.bg_105_208,R.drawable.bg_105_209,R.drawable.bg_105_210,R.drawable.bg_105_211},

            {R.drawable.bg_106_201,R.drawable.bg_106_202,R.drawable.bg_106_203,R.drawable.bg_106_204,R.drawable.bg_106_205,
             R.drawable.bg_106_207, R.drawable.bg_106_208,R.drawable.bg_106_209,R.drawable.bg_106_210,R.drawable.bg_106_211},

            {R.drawable.bg_107_201,R.drawable.bg_107_202,R.drawable.bg_107_203,R.drawable.bg_107_204,R.drawable.bg_107_205,
             R.drawable.bg_107_206,R.drawable.bg_107_208,R.drawable.bg_107_209,R.drawable.bg_107_210,R.drawable.bg_107_211},

            {R.drawable.bg_108_201,R.drawable.bg_108_202,R.drawable.bg_108_203,R.drawable.bg_108_204,R.drawable.bg_108_205,
             R.drawable.bg_108_206,R.drawable.bg_108_207,R.drawable.bg_108_209,R.drawable.bg_108_210,R.drawable.bg_108_211},

            {R.drawable.bg_109_201,R.drawable.bg_109_202,R.drawable.bg_109_203,R.drawable.bg_109_204,R.drawable.bg_109_205,
             R.drawable.bg_109_206,R.drawable.bg_109_207,R.drawable.bg_109_208,R.drawable.bg_109_210,R.drawable.bg_109_211},

            {R.drawable.bg_110_201,R.drawable.bg_110_202,R.drawable.bg_110_203,R.drawable.bg_110_204,R.drawable.bg_110_205,
             R.drawable.bg_110_206,R.drawable.bg_110_207,R.drawable.bg_110_208,R.drawable.bg_110_209,R.drawable.bg_110_211},

            {R.drawable.bg_111_201,R.drawable.bg_111_202,R.drawable.bg_111_203,R.drawable.bg_111_204,R.drawable.bg_111_205,
             R.drawable.bg_111_206,R.drawable.bg_111_207,R.drawable.bg_111_208,R.drawable.bg_111_209,R.drawable.bg_111_210}
                            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agv);
        findViews();
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle("AGV调度");
        }


        tcpHelper = TcpHelper.getInstance();
//        try {
//            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_AGV, Constant.CMD_AGV,Constant.KEY_GET_AGV_DATA,"1").getBytes());
//            WaitDialog.showDialog(AgvActivity.this, "请稍候", 5000);
//            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
//                @Override
//                public void onReceiveData(String receiveData) {
//                    WaitDialog.immediatelyDismiss();
//                    String cmd_agv;
//                    String agv1_communication;
//                    String agv2_communication;
//                    String agv1_station;
//                    String agv2_station;
//                    String agv1_task;
//                    String agv2_task;
//                    String agv1_single_task;
//                    String agv2_single_task;
//                    String agv1_current_loc;
//                    String agv2_current_loc;
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(receiveData);
//                        cmd_agv = jsonObject.getString(Constant.KEY_AGV);
//                        if(cmd_agv.equalsIgnoreCase(Constant.CMD_AGV_COMMUNICATION)){
//                            agv1_communication = jsonObject.getString("agv1_communication");
//                            agv2_communication = jsonObject.getString("agv2_communication");
//                            tvAgv1Status.setText(agv1_communication);
//                            tvAgv2Status.setText(agv2_communication);
//                        }else if(cmd_agv.equalsIgnoreCase(Constant.CMD_AGV_UNIT)){
//                            agv1_station = jsonObject.getString("agv1_station");
//                            agv2_station = jsonObject.getString("agv2_station");
//                            agv1_task = jsonObject.getString("agv1_task");
//                            agv2_task = jsonObject.getString("agv2_task");
//                            agv1_single_task = jsonObject.getString("agv1_single_task");
//                            agv2_single_task = jsonObject.getString("agv2_single_task");
//
//                            tvAgv1CurrentStation.setText(agv1_station);
//                            tvAgv2CurrentStation.setText(agv2_station);
//                            tvAgv1CurrentTask.setText(agv1_task);
//                            tvAgv2CurrentTask.setText(agv2_task);
//                            tvAgv1CurrentSingleTask.setText(agv1_single_task);
//                            tvAgv2CurrentSingleTask.setText(agv2_single_task);
//
//                        }else if(cmd_agv.equalsIgnoreCase(Constant.CMD_AGV_CURRENT_LOC)){
//                            agv1_current_loc = jsonObject.getString("agv1_current_loc");
//                            agv2_current_loc = jsonObject.getString("agv2_current_loc");
//                            int agv1_loc = Integer.parseInt(agv1_current_loc);
//                            int agv2_loc = Integer.parseInt(agv2_current_loc);
//                            if(agv1_loc>agv2_loc){
//                                ivAgvBg.setBackgroundResource(imageBg[agv1_loc-1][agv2_loc-1]);
//                            }else  if(agv1_loc<agv2_loc){
//                                ivAgvBg.setBackgroundResource(imageBg[agv1_loc-1][agv2_loc - 2]);
//                            }
//
//                        }
////                        {"agv":"cmd_agv_communication", "agv1_communication":"1", "agv2_communication":"1"}
////                        {"agv":"cmd_agv_unit", "agv1_station":"1", "agv2_station":"1", "agv1_task":"1", "agv2_task":"1", "agv1_single_task":"1", "agv2_single_task":"1"}
////                        {"agv":"cmd_agv_current_loc", "agv1_current_loc":"02", "agv2_current_loc":"03"}
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//        } catch (Exception e) {
//            ToastUtil.showToast(AgvActivity.this, "服务器可能断开连接了！");
//        }
        sendAndSetting();
        WaitDialog.showDialog(AgvActivity.this, "请稍候", 5000);

        slAgv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendAndSetting();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slAgv.setRefreshing(false);
                    }
                }, 2000);
            }
        });



    }

    private void sendAndSetting() {
        try {
            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_AGV, Constant.CMD_AGV, Constant.KEY_GET_AGV_DATA, "1").getBytes());
            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
                @Override
                public void onReceiveData(String receiveData) {
                    WaitDialog.immediatelyDismiss();
                    String cmd_agv;
                    String agv1_communication;
                    String agv2_communication;
                    String agv1_station;
                    String agv2_station;
                    String agv1_task;
                    String agv2_task;
                    String agv1_single_task;
                    String agv2_single_task;
                    String agv1_current_loc;
                    String agv2_current_loc;

                    try {
                        JSONObject jsonObject = new JSONObject(receiveData);
                        cmd_agv = jsonObject.getString(Constant.KEY_AGV);
                        if(cmd_agv.equalsIgnoreCase(Constant.CMD_AGV_COMMUNICATION)){
                            agv1_communication = jsonObject.getString("agv1_communication");
                            agv2_communication = jsonObject.getString("agv2_communication");
                            tvAgv1Status.setText(agv1_communication);
                            tvAgv2Status.setText(agv2_communication);
                        }else if(cmd_agv.equalsIgnoreCase(Constant.CMD_AGV_UNIT)){
                            agv1_station = jsonObject.getString("agv1_station");
                            agv2_station = jsonObject.getString("agv2_station");
                            agv1_task = jsonObject.getString("agv1_task");
                            agv2_task = jsonObject.getString("agv2_task");
                            agv1_single_task = jsonObject.getString("agv1_single_task");
                            agv2_single_task = jsonObject.getString("agv2_single_task");

                            tvAgv1CurrentStation.setText(agv1_station);
                            tvAgv2CurrentStation.setText(agv2_station);
                            tvAgv1CurrentTask.setText(agv1_task);
                            tvAgv2CurrentTask.setText(agv2_task);
                            tvAgv1CurrentSingleTask.setText(agv1_single_task);
                            tvAgv2CurrentSingleTask.setText(agv2_single_task);

                        }else if(cmd_agv.equalsIgnoreCase(Constant.CMD_AGV_CURRENT_LOC)){
                            agv1_current_loc = jsonObject.getString("agv1_current_loc");
                            agv2_current_loc = jsonObject.getString("agv2_current_loc");
                            int agv1_loc = Integer.parseInt(agv1_current_loc);
                            int agv2_loc = Integer.parseInt(agv2_current_loc);
                            if(agv1_loc>agv2_loc){
                                ivAgvBg.setBackgroundResource(imageBg[agv1_loc-1][agv2_loc-1]);
                            }else  if(agv1_loc<agv2_loc){
                                ivAgvBg.setBackgroundResource(imageBg[agv1_loc-1][agv2_loc - 2]);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            ToastUtil.showToast(AgvActivity.this, "服务器可能断开连接了！");
        }
    }

    private void findViews() {
        tvAgv1Status = (TextView)findViewById(R.id.tvAgv1Status);
        tvAgv2Status = (TextView)findViewById(R.id.tvAgv2Status);
        tvAgv1CurrentStation = (TextView)findViewById(R.id.tvAgv1CurrentStation);
        tvAgv2CurrentStation = (TextView)findViewById(R.id.tvAgv2CurrentStation);
        tvAgv1CurrentTask = (TextView)findViewById(R.id.tvAgv1CurrentTask);
        tvAgv2CurrentTask = (TextView)findViewById(R.id.tvAgv2CurrentTask);
        tvAgv1CurrentSingleTask = (TextView)findViewById(R.id.tvAgv1CurrentSingleTask);
        tvAgv2CurrentSingleTask = (TextView)findViewById(R.id.tvAgv2CurrentSingleTask);
        ivAgvBg = (ImageView)findViewById(R.id.ivAgvBg);
        slAgv = (SwipeRefreshLayout)findViewById(R.id.slAgv);
    }
}
