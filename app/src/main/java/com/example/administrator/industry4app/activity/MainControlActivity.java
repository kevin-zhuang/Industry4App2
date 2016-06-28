package com.example.administrator.industry4app.activity;

import android.app.ActionBar;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.util.Constant;
import com.example.administrator.industry4app.util.ReceiveDataCallback;
import com.example.administrator.industry4app.util.TcpHelper;
import com.example.administrator.industry4app.util.ToastUtil;
import com.example.administrator.industry4app.util.WaitDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;

public class MainControlActivity extends AppCompatActivity {


    private ImageView ivAgvInit;
    private ImageView ivAgvPreop;
    private ImageView ivAgvOp;
    private ImageView ivAgvStop;

    private ImageView ivProcessInit;
    private ImageView ivProcessPreop;
    private ImageView ivProcessOp;
    private ImageView ivProcessStop;

    private ImageView ivAssemblyInit;
    private ImageView ivAssemblyPreop;
    private ImageView ivAssemblyOp;
    private ImageView ivAssemblyStop;

    private ImageView ivShuntInit;
    private ImageView ivShuntPreop;
    private ImageView ivShuntOp;
    private ImageView ivShuntStop;

    private ImageView ivWarehouseInit;
    private ImageView ivWarehousePreop;
    private ImageView ivWarehouseOp;
    private ImageView ivWarehouseStop;

    private TextView tvAgvError;
    private TextView tvProcessError;
    private TextView tvAssemblyError;
    private TextView tvShuntError;
    private TextView tvWarehouseError;
    private SwipeRefreshLayout slMain;


    private TcpHelper tcpHelper;
    private static final String TAG="MainControlActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_control);

        findViews();
        ActionBar actionBar ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("主控制界面");
            }
        }
        tcpHelper = TcpHelper.getInstance();
//        try {
//            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_MAIN, Constant.CMD_MAIN, Constant.KEY_GET_MAIN_DATA, "1").getBytes());
//            WaitDialog.showDialog(MainControlActivity.this, "请稍候", 5000);
//            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
//                @Override
//                public void onReceiveData(String receiveData) {
//                    WaitDialog.immediatelyDismiss();
//
//                    Log.e(TAG,"receiveData="+receiveData);
////                    {"main":"cmd_main", "agv_init":"1", "agv_preop":"1", "agv_op":"1", "agv_stop":"1", "agv_error":"错误信息", "process_init":"1", "process_preop":"1", "process_op":"1", "process_stop":"1", "process_error":"错误信息", "assembly_init":"1", "assembly_preop":"1", "assembly_op":"1", "assembly_stop":"1", "assembly_error":"错误信息", "shunt_init":"1", "shunt_preop":"1","shunt_op":"1", "shunt_stop":"1", "shunt_error":"错误信息", "warehouse_init":"1", "warehouse_preop":"1", "warehouse_op":"1", "warehouse_stop":"1", "warehouse_error":"错误信息"}
//
//                    String cmd_main;
//                    String agv_init;
//                    String agv_preop;
//                    String agv_op;
//                    String agv_stop;
//                    String agv_error;
//
//                    String process_init;
//                    String process_preop;
//                    String process_op;
//                    String process_stop;
//                    String process_error;
//
//                    String assembly_init;
//                    String assembly_preop;
//                    String assembly_op;
//                    String assembly_stop;
//                    String assembly_error;
//
//                    String shunt_init;
//                    String shunt_preop;
//                    String shunt_op;
//                    String shunt_stop;
//                    String shunt_error;
//
//                    String warehouse_init;
//                    String warehouse_preop;
//                    String warehouse_op;
//                    String warehouse_stop;
//                    String warehouse_error;
//
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(receiveData);
//                        cmd_main = jsonObject.getString(Constant.KEY_MAIN);
//
//
//                        if (cmd_main.equalsIgnoreCase(Constant.CMD_MAIN)) {
//                            agv_init = jsonObject.getString("agv_init");
//                            agv_preop = jsonObject.getString("agv_preop");
//                            agv_op = jsonObject.getString("agv_op");
//                            agv_stop = jsonObject.getString("agv_stop");
//                            agv_error = jsonObject.getString("agv_error");
//
//                            tvAgvError.setText(agv_error);
//
//                            int agvInit = Integer.parseInt(agv_init);
//                            if (agvInit == 0) {
//                                ivAgvInit.setBackgroundResource(R.drawable.green);
//                            } else if (agvInit == 1) {
//                                ivAgvInit.setBackgroundResource(R.drawable.red);
//                            }
//
//                            int agvPreop = Integer.parseInt(agv_preop);
//
//                            if (agvPreop == 0) {
//                                ivAgvPreop.setBackgroundResource(R.drawable.green);
//                            } else if (agvPreop == 1) {
//                                ivAgvPreop.setBackgroundResource(R.drawable.red);
//                            }
//
//                            int agvOp = Integer.parseInt(agv_op);
//                            if (agvOp == 0) {
//                                ivAgvOp.setBackgroundResource(R.drawable.green);
//                            } else if (agvOp == 1) {
//                                ivAgvOp.setBackgroundResource(R.drawable.red);
//                            }
//
//
//                            int agvStop = Integer.parseInt(agv_stop);
//                            if (agvStop == 0) {
//                                ivAgvStop.setBackgroundResource(R.drawable.green);
//                            } else if (agvStop == 1) {
//                                ivAgvStop.setBackgroundResource(R.drawable.red);
//                            }
//
//                            process_init = jsonObject.getString("process_init");
//                            process_preop = jsonObject.getString("process_preop");
//                            process_op = jsonObject.getString("process_op");
//                            process_stop = jsonObject.getString("process_stop");
//                            process_error = jsonObject.getString("process_error");
//
//                            tvProcessError.setText(process_error);
//
//
//                            int processInit = Integer.parseInt(process_init);
//                            if (processInit == 0) {
//                                ivProcessInit.setBackgroundResource(R.drawable.green);
//                            } else if (processInit == 1) {
//                                ivProcessInit.setBackgroundResource(R.drawable.red);
//                            }
//                            int processPreop = Integer.parseInt(process_preop);
//                            if (processPreop == 0) {
//                                ivProcessPreop.setBackgroundResource(R.drawable.green);
//                            } else if (processPreop == 1) {
//                                ivProcessPreop.setBackgroundResource(R.drawable.red);
//                            }
//                            int processOp = Integer.parseInt(process_op);
//                            if (processOp == 0) {
//                                ivProcessOp.setBackgroundResource(R.drawable.green);
//                            } else if (processOp == 1) {
//                                ivProcessOp.setBackgroundResource(R.drawable.red);
//                            }
//                            int processStop = Integer.parseInt(process_stop);
//                            if (processStop == 0) {
//                                ivProcessStop.setBackgroundResource(R.drawable.green);
//                            } else if (processStop == 1) {
//                                ivProcessStop.setBackgroundResource(R.drawable.red);
//                            }
//
//                            assembly_init = jsonObject.getString("assembly_init");
//                            assembly_preop = jsonObject.getString("assembly_preop");
//                            assembly_op = jsonObject.getString("assembly_op");
//                            assembly_stop = jsonObject.getString("assembly_stop");
//                            assembly_error = jsonObject.getString("assembly_error");
//
//                            tvAssemblyError.setText(assembly_error);
//
//
//                            int assemblyInit = Integer.parseInt(assembly_init);
//                            if (assemblyInit == 0) {
//                                ivAssemblyInit.setBackgroundResource(R.drawable.green);
//                            } else if (assemblyInit == 1) {
//                                ivAssemblyInit.setBackgroundResource(R.drawable.red);
//                            }
//                            int assemblyPreop = Integer.parseInt(assembly_preop);
//                            if (assemblyPreop == 0) {
//                                ivAssemblyPreop.setBackgroundResource(R.drawable.green);
//                            } else if (assemblyPreop == 1) {
//                                ivAssemblyPreop.setBackgroundResource(R.drawable.red);
//                            }
//                            int assemblyOp = Integer.parseInt(assembly_op);
//                            if (assemblyOp == 0) {
//                                ivAssemblyOp.setBackgroundResource(R.drawable.green);
//                            } else if (assemblyOp == 1) {
//                                ivAssemblyOp.setBackgroundResource(R.drawable.red);
//                            }
//                            int assemblyStop = Integer.parseInt(assembly_stop);
//                            if (assemblyStop == 0) {
//                                ivAssemblyStop.setBackgroundResource(R.drawable.green);
//                            } else if (assemblyStop == 1) {
//                                ivAssemblyStop.setBackgroundResource(R.drawable.red);
//                            }
//
//                            shunt_init = jsonObject.getString("shunt_init");
//                            shunt_preop = jsonObject.getString("shunt_preop");
//                            shunt_op = jsonObject.getString("shunt_op");
//                            shunt_stop = jsonObject.getString("shunt_stop");
//                            shunt_error = jsonObject.getString("shunt_error");
//
//                            tvShuntError.setText(shunt_error);
//
//                            int shuntInit = Integer.parseInt(shunt_init);
//                            if (shuntInit == 0) {
//                                ivShuntInit.setBackgroundResource(R.drawable.green);
//                            } else if (shuntInit == 1) {
//                                ivShuntInit.setBackgroundResource(R.drawable.red);
//                            }
//                            int shuntPreop = Integer.parseInt(shunt_preop);
//                            if (shuntPreop == 0) {
//                                ivShuntPreop.setBackgroundResource(R.drawable.green);
//                            } else if (shuntPreop == 1) {
//                                ivShuntPreop.setBackgroundResource(R.drawable.red);
//                            }
//                            int shuntOp = Integer.parseInt(shunt_op);
//                            if (shuntOp == 0) {
//                                ivShuntOp.setBackgroundResource(R.drawable.green);
//                            } else if (shuntOp == 1) {
//                                ivShuntOp.setBackgroundResource(R.drawable.red);
//                            }
//                            int shuntStop = Integer.parseInt(shunt_stop);
//                            if (shuntStop == 0) {
//                                ivShuntStop.setBackgroundResource(R.drawable.green);
//                            } else if (shuntStop == 1) {
//                                ivShuntStop.setBackgroundResource(R.drawable.red);
//                            }
//                            warehouse_init = jsonObject.getString("warehouse_init");
//                            warehouse_preop = jsonObject.getString("warehouse_preop");
//                            warehouse_op = jsonObject.getString("warehouse_op");
//                            warehouse_stop = jsonObject.getString("warehouse_stop");
//                            warehouse_error = jsonObject.getString("warehouse_error");
//
//                            tvWarehouseError.setText(warehouse_error);
//
//                            int warehouseInit = Integer.parseInt(warehouse_init);
//                            if (warehouseInit == 0) {
//                                ivWarehouseInit.setBackgroundResource(R.drawable.green);
//                            } else if (warehouseInit == 1) {
//                                ivWarehouseInit.setBackgroundResource(R.drawable.red);
//                            }
//                            int warehousePreop = Integer.parseInt(warehouse_preop);
//                            if (warehousePreop == 0) {
//                                ivWarehousePreop.setBackgroundResource(R.drawable.green);
//                            } else if (warehousePreop == 1) {
//                                ivWarehousePreop.setBackgroundResource(R.drawable.red);
//                            }
//                            int warehouseOp = Integer.parseInt(warehouse_op);
//                            if (warehouseOp == 0) {
//                                ivWarehouseOp.setBackgroundResource(R.drawable.green);
//                            } else if (warehouseOp == 1) {
//                                ivWarehouseOp.setBackgroundResource(R.drawable.red);
//                            }
//                            int warehouseStop = Integer.parseInt(warehouse_stop);
//                            if (warehouseStop == 0) {
//                                ivWarehouseStop.setBackgroundResource(R.drawable.green);
//                            } else if (warehouseStop == 1) {
//                                ivWarehouseStop.setBackgroundResource(R.drawable.red);
//                            }
//
//                        }
//
//
//                    } catch (JSONException e) {
//                        Log.e(TAG,"ee="+e.toString());
//                        e.printStackTrace();
//                    }
//
//
//                }
//            });
//        } catch (Exception e) {
//            ToastUtil.showToast(MainControlActivity.this, "服务器可能断开连接了！");
//        }
        sendAndSetting();
        WaitDialog.showDialog(MainControlActivity.this, "请稍候", 5000);
        initBackground();

        slMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendAndSetting();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slMain.setRefreshing(false);
                    }
                }, 2000);
            }
        });


    }

    private void sendAndSetting() {
        try {
            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_MAIN, Constant.CMD_MAIN, Constant.KEY_GET_MAIN_DATA, "1").getBytes());
            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
                @Override
                public void onReceiveData(String receiveData) {
                    WaitDialog.immediatelyDismiss();

                    Log.e(TAG, "receiveData=" + receiveData);
//                    {"main":"cmd_main", "agv_init":"1", "agv_preop":"1", "agv_op":"1", "agv_stop":"1", "agv_error":"错误信息", "process_init":"1", "process_preop":"1", "process_op":"1", "process_stop":"1", "process_error":"错误信息", "assembly_init":"1", "assembly_preop":"1", "assembly_op":"1", "assembly_stop":"1", "assembly_error":"错误信息", "shunt_init":"1", "shunt_preop":"1","shunt_op":"1", "shunt_stop":"1", "shunt_error":"错误信息", "warehouse_init":"1", "warehouse_preop":"1", "warehouse_op":"1", "warehouse_stop":"1", "warehouse_error":"错误信息"}

                    String cmd_main;
                    String agv_init;
                    String agv_preop;
                    String agv_op;
                    String agv_stop;
                    String agv_error;

                    String process_init;
                    String process_preop;
                    String process_op;
                    String process_stop;
                    String process_error;

                    String assembly_init;
                    String assembly_preop;
                    String assembly_op;
                    String assembly_stop;
                    String assembly_error;

                    String shunt_init;
                    String shunt_preop;
                    String shunt_op;
                    String shunt_stop;
                    String shunt_error;

                    String warehouse_init;
                    String warehouse_preop;
                    String warehouse_op;
                    String warehouse_stop;
                    String warehouse_error;


                    try {
                        JSONObject jsonObject = new JSONObject(receiveData);
                        cmd_main = jsonObject.getString(Constant.KEY_MAIN);


                        if (cmd_main.equalsIgnoreCase(Constant.CMD_MAIN)) {
                            agv_init = jsonObject.getString("agv_init");
                            agv_preop = jsonObject.getString("agv_preop");
                            agv_op = jsonObject.getString("agv_op");
                            agv_stop = jsonObject.getString("agv_stop");
                            agv_error = jsonObject.getString("agv_error");

                            tvAgvError.setText(agv_error);

                            int agvInit = Integer.parseInt(agv_init);
                            if (agvInit == 0) {
                                ivAgvInit.setBackgroundResource(R.drawable.green);
                            } else if (agvInit == 1) {
                                ivAgvInit.setBackgroundResource(R.drawable.red);
                            }

                            int agvPreop = Integer.parseInt(agv_preop);

                            if (agvPreop == 0) {
                                ivAgvPreop.setBackgroundResource(R.drawable.green);
                            } else if (agvPreop == 1) {
                                ivAgvPreop.setBackgroundResource(R.drawable.red);
                            }

                            int agvOp = Integer.parseInt(agv_op);
                            if (agvOp == 0) {
                                ivAgvOp.setBackgroundResource(R.drawable.green);
                            } else if (agvOp == 1) {
                                ivAgvOp.setBackgroundResource(R.drawable.red);
                            }


                            int agvStop = Integer.parseInt(agv_stop);
                            if (agvStop == 0) {
                                ivAgvStop.setBackgroundResource(R.drawable.green);
                            } else if (agvStop == 1) {
                                ivAgvStop.setBackgroundResource(R.drawable.red);
                            }

                            process_init = jsonObject.getString("process_init");
                            process_preop = jsonObject.getString("process_preop");
                            process_op = jsonObject.getString("process_op");
                            process_stop = jsonObject.getString("process_stop");
                            process_error = jsonObject.getString("process_error");

                            tvProcessError.setText(process_error);


                            int processInit = Integer.parseInt(process_init);
                            if (processInit == 0) {
                                ivProcessInit.setBackgroundResource(R.drawable.green);
                            } else if (processInit == 1) {
                                ivProcessInit.setBackgroundResource(R.drawable.red);
                            }
                            int processPreop = Integer.parseInt(process_preop);
                            if (processPreop == 0) {
                                ivProcessPreop.setBackgroundResource(R.drawable.green);
                            } else if (processPreop == 1) {
                                ivProcessPreop.setBackgroundResource(R.drawable.red);
                            }
                            int processOp = Integer.parseInt(process_op);
                            if (processOp == 0) {
                                ivProcessOp.setBackgroundResource(R.drawable.green);
                            } else if (processOp == 1) {
                                ivProcessOp.setBackgroundResource(R.drawable.red);
                            }
                            int processStop = Integer.parseInt(process_stop);
                            if (processStop == 0) {
                                ivProcessStop.setBackgroundResource(R.drawable.green);
                            } else if (processStop == 1) {
                                ivProcessStop.setBackgroundResource(R.drawable.red);
                            }

                            assembly_init = jsonObject.getString("assembly_init");
                            assembly_preop = jsonObject.getString("assembly_preop");
                            assembly_op = jsonObject.getString("assembly_op");
                            assembly_stop = jsonObject.getString("assembly_stop");
                            assembly_error = jsonObject.getString("assembly_error");

                            tvAssemblyError.setText(assembly_error);


                            int assemblyInit = Integer.parseInt(assembly_init);
                            if (assemblyInit == 0) {
                                ivAssemblyInit.setBackgroundResource(R.drawable.green);
                            } else if (assemblyInit == 1) {
                                ivAssemblyInit.setBackgroundResource(R.drawable.red);
                            }
                            int assemblyPreop = Integer.parseInt(assembly_preop);
                            if (assemblyPreop == 0) {
                                ivAssemblyPreop.setBackgroundResource(R.drawable.green);
                            } else if (assemblyPreop == 1) {
                                ivAssemblyPreop.setBackgroundResource(R.drawable.red);
                            }
                            int assemblyOp = Integer.parseInt(assembly_op);
                            if (assemblyOp == 0) {
                                ivAssemblyOp.setBackgroundResource(R.drawable.green);
                            } else if (assemblyOp == 1) {
                                ivAssemblyOp.setBackgroundResource(R.drawable.red);
                            }
                            int assemblyStop = Integer.parseInt(assembly_stop);
                            if (assemblyStop == 0) {
                                ivAssemblyStop.setBackgroundResource(R.drawable.green);
                            } else if (assemblyStop == 1) {
                                ivAssemblyStop.setBackgroundResource(R.drawable.red);
                            }

                            shunt_init = jsonObject.getString("shunt_init");
                            shunt_preop = jsonObject.getString("shunt_preop");
                            shunt_op = jsonObject.getString("shunt_op");
                            shunt_stop = jsonObject.getString("shunt_stop");
                            shunt_error = jsonObject.getString("shunt_error");

                            tvShuntError.setText(shunt_error);

                            int shuntInit = Integer.parseInt(shunt_init);
                            if (shuntInit == 0) {
                                ivShuntInit.setBackgroundResource(R.drawable.green);
                            } else if (shuntInit == 1) {
                                ivShuntInit.setBackgroundResource(R.drawable.red);
                            }
                            int shuntPreop = Integer.parseInt(shunt_preop);
                            if (shuntPreop == 0) {
                                ivShuntPreop.setBackgroundResource(R.drawable.green);
                            } else if (shuntPreop == 1) {
                                ivShuntPreop.setBackgroundResource(R.drawable.red);
                            }
                            int shuntOp = Integer.parseInt(shunt_op);
                            if (shuntOp == 0) {
                                ivShuntOp.setBackgroundResource(R.drawable.green);
                            } else if (shuntOp == 1) {
                                ivShuntOp.setBackgroundResource(R.drawable.red);
                            }
                            int shuntStop = Integer.parseInt(shunt_stop);
                            if (shuntStop == 0) {
                                ivShuntStop.setBackgroundResource(R.drawable.green);
                            } else if (shuntStop == 1) {
                                ivShuntStop.setBackgroundResource(R.drawable.red);
                            }
                            warehouse_init = jsonObject.getString("warehouse_init");
                            warehouse_preop = jsonObject.getString("warehouse_preop");
                            warehouse_op = jsonObject.getString("warehouse_op");
                            warehouse_stop = jsonObject.getString("warehouse_stop");
                            warehouse_error = jsonObject.getString("warehouse_error");

                            tvWarehouseError.setText(warehouse_error);

                            int warehouseInit = Integer.parseInt(warehouse_init);
                            if (warehouseInit == 0) {
                                ivWarehouseInit.setBackgroundResource(R.drawable.green);
                            } else if (warehouseInit == 1) {
                                ivWarehouseInit.setBackgroundResource(R.drawable.red);
                            }
                            int warehousePreop = Integer.parseInt(warehouse_preop);
                            if (warehousePreop == 0) {
                                ivWarehousePreop.setBackgroundResource(R.drawable.green);
                            } else if (warehousePreop == 1) {
                                ivWarehousePreop.setBackgroundResource(R.drawable.red);
                            }
                            int warehouseOp = Integer.parseInt(warehouse_op);
                            if (warehouseOp == 0) {
                                ivWarehouseOp.setBackgroundResource(R.drawable.green);
                            } else if (warehouseOp == 1) {
                                ivWarehouseOp.setBackgroundResource(R.drawable.red);
                            }
                            int warehouseStop = Integer.parseInt(warehouse_stop);
                            if (warehouseStop == 0) {
                                ivWarehouseStop.setBackgroundResource(R.drawable.green);
                            } else if (warehouseStop == 1) {
                                ivWarehouseStop.setBackgroundResource(R.drawable.red);
                            }

                        }


                    } catch (JSONException e) {
                        Log.e(TAG,"ee="+e.toString());
                        e.printStackTrace();
                    }


                }
            });
        } catch (Exception e) {
            ToastUtil.showToast(MainControlActivity.this, "服务器可能断开连接了！");
        }
    }

    private void initBackground() {
        ivAgvInit.setBackgroundResource(R.drawable.green);
        ivAgvPreop.setBackgroundResource(R.drawable.green);
        ivAgvOp.setBackgroundResource(R.drawable.green);
        ivAgvStop.setBackgroundResource(R.drawable.green);

        ivProcessInit.setBackgroundResource(R.drawable.green);
        ivProcessPreop.setBackgroundResource(R.drawable.green);
        ivProcessOp.setBackgroundResource(R.drawable.green);
        ivProcessStop.setBackgroundResource(R.drawable.green);

        ivAssemblyInit.setBackgroundResource(R.drawable.green);
        ivAssemblyPreop.setBackgroundResource(R.drawable.green);
        ivAssemblyOp.setBackgroundResource(R.drawable.green);
        ivAssemblyStop.setBackgroundResource(R.drawable.green);

        ivShuntInit.setBackgroundResource(R.drawable.green);
        ivShuntPreop.setBackgroundResource(R.drawable.green);
        ivShuntOp.setBackgroundResource(R.drawable.green);
        ivShuntStop.setBackgroundResource(R.drawable.green);

        ivWarehouseInit.setBackgroundResource(R.drawable.green);
        ivWarehousePreop.setBackgroundResource(R.drawable.green);
        ivWarehouseOp.setBackgroundResource(R.drawable.green);
        ivWarehouseStop.setBackgroundResource(R.drawable.green);
    }

    private void findViews() {
        ivAgvInit = (ImageView) findViewById(R.id.ivAgvInit);
        ivAgvPreop = (ImageView) findViewById(R.id.ivAgvPreop);
        ivAgvOp = (ImageView) findViewById(R.id.ivAgvOp);
        ivAgvStop = (ImageView) findViewById(R.id.ivAgvStop);

        ivProcessInit = (ImageView) findViewById(R.id.ivProcessInit);
        ivProcessPreop = (ImageView) findViewById(R.id.ivProcessPreop);
        ivProcessOp = (ImageView) findViewById(R.id.ivProcessOp);
        ivProcessStop = (ImageView) findViewById(R.id.ivProcessStop);

        ivAssemblyInit = (ImageView) findViewById(R.id.ivAssemblyInit);
        ivAssemblyPreop = (ImageView) findViewById(R.id.ivAssemblyPreop);
        ivAssemblyOp = (ImageView) findViewById(R.id.ivAssemblyOp);
        ivAssemblyStop = (ImageView) findViewById(R.id.ivAssemblyStop);

        ivShuntInit = (ImageView) findViewById(R.id.ivShuntInit);
        ivShuntPreop = (ImageView) findViewById(R.id.ivShuntPreop);
        ivShuntOp = (ImageView) findViewById(R.id.ivShuntOp);
        ivShuntStop = (ImageView) findViewById(R.id.ivShuntStop);

        ivWarehouseInit = (ImageView) findViewById(R.id.ivWarehouseInit);
        ivWarehousePreop = (ImageView) findViewById(R.id.ivWarehousePreop);
        ivWarehouseOp = (ImageView) findViewById(R.id.ivWarehouseOp);
        ivWarehouseStop = (ImageView) findViewById(R.id.ivWarehouseStop);

        tvAgvError = (TextView) findViewById(R.id.tvAgvError);
        tvProcessError = (TextView) findViewById(R.id.tvProcessError);
        tvAssemblyError = (TextView) findViewById(R.id.tvAssemblyError);
        tvShuntError = (TextView) findViewById(R.id.tvShuntError);
        tvWarehouseError = (TextView) findViewById(R.id.tvWarehouseError);
        slMain = (SwipeRefreshLayout)findViewById(R.id.slMain);

    }
}
