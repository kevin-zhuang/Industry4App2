package com.example.administrator.industry4app.activity;

import android.app.ActionBar;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.db.DBCurd;
import com.example.administrator.industry4app.entity.PlanBean;
import com.example.administrator.industry4app.entity.StockBean;
import com.example.administrator.industry4app.util.Constant;
import com.example.administrator.industry4app.util.ReceiveDataCallback;
import com.example.administrator.industry4app.util.StockAdapter;
import com.example.administrator.industry4app.util.TcpHelper;
import com.example.administrator.industry4app.util.ToastUtil;
import com.example.administrator.industry4app.util.WaitDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StockActivity extends AppCompatActivity {

    private ListView lvStock;
    private Button btnAddPlan;
    private StockAdapter stockAdapter;
    private List<StockBean> list = new ArrayList<>();
    private int selectedPosition = -1;
    private boolean isSelectedPosition = true;
    private int isSelected = -1;
    private TcpHelper tcpHelper;
    private static final String TAG = "StockActivity";

    private DBCurd dbCurd;

    private SwipeRefreshLayout slStock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ActionBar actionBar ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle("库存管理");
            }
        }
        slStock = (SwipeRefreshLayout)findViewById(R.id.slStock);
        dbCurd = DBCurd.getInstance(StockActivity.this);
        tcpHelper = TcpHelper.getInstance();
        dbCurd.delAllStockData();
//        try{
//            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_STOCK,Constant.CMD_STOCK,Constant.KEY_GET_STOCK_DATA,"1").getBytes());
//            WaitDialog.showDialog(StockActivity.this, "请稍候", 5000);
//            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
//                @Override
//                public void onReceiveData(String receiveData) {
//                    WaitDialog.immediatelyDismiss();
//
////                    {"stock":"cmd_stock", "id":"id","groupnum":"groupnum", "rownum":"rownum", "columnum":"columnum", "name":"name", "type":"type", "num":"num"}
//
//                    String cmd_stock = null;
//                    String id = null;
//                    String groupnum = null;
//                    String rownum = null;
//                    String columnum = null;
//                    String name = null;
//                    String type = null;
//                    String num = null;
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(receiveData);
//                        cmd_stock = jsonObject.getString("stock");
//                        id = jsonObject.getString("id");
//                        groupnum = jsonObject.getString("groupnum");
//                        rownum = jsonObject.getString("rownum");
//                        columnum = jsonObject.getString("columnum");
//                        name = jsonObject.getString("name");
//                        type = jsonObject.getString("type");
//                        num = jsonObject.getString("num");
//
//                    } catch (JSONException e) {
//                        Log.e(TAG,"eee="+e.toString());
//                        e.printStackTrace();
//                    }
//                    if (cmd_stock != null && cmd_stock.equalsIgnoreCase(Constant.CMD_STOCK)) {
//                        StockBean stcckBean = new StockBean();
//                        stcckBean.setName(name);
//                        stcckBean.setNum(num);
//                        stcckBean.setColumNum(columnum);
//                        stcckBean.setType(type);
//                        stcckBean.setRowNum(rownum);
//                        stcckBean.setGroupNum(groupnum);
//                        stcckBean.setStockId(id);
//                        dbCurd.addStockData(stcckBean);
//                        list = dbCurd.selectAllStockData();
//                        stockAdapter = new StockAdapter(StockActivity.this,list);
//                        lvStock.setAdapter(stockAdapter);
//
//                    }
//                }
//            });
//        }catch (Exception e){
//            ToastUtil.showToast(StockActivity.this, "服务器可能断开连接了！");
//        }
        sendAndSetting();
        WaitDialog.showDialog(StockActivity.this, "请稍候", 5000);
        stockAdapter = new StockAdapter(StockActivity.this,list);
        lvStock = (ListView)findViewById(R.id.lvStock);
        btnAddPlan = (Button)findViewById(R.id.btnAddPlan);
        lvStock.setAdapter(stockAdapter);


        lvStock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (selectedPosition == position) {
                    isSelectedPosition = !isSelectedPosition;
                    stockAdapter.setChangeId(position, isSelectedPosition);
                    stockAdapter.notifyDataSetChanged();
//                    btnAddPlan.setEnabled(isSelectedPosition);
                } else {
                    isSelectedPosition = true;
                    stockAdapter.setChangeId(position, true);
                    stockAdapter.notifyDataSetChanged();
//                    btnAddPlan.setEnabled(isSelectedPosition);
                }

                isSelected = isSelectedPosition ? position : -1;
                selectedPosition = position;
            }
        });

        btnAddPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected == -1) {
                    ToastUtil.customToast(StockActivity.this, "没有选择");
                } else {
                    isSelectedPosition = !isSelectedPosition;
                    stockAdapter.setChangeId(isSelected, isSelectedPosition);
                    stockAdapter.notifyDataSetChanged();
                    ToastUtil.customToast(StockActivity.this, "选择了第" + (isSelected + 1) + "项");

                    try {
                        tcpHelper.sendData(Constant.getJsonData(Constant.KEY_ADD_STOCK, Constant.CMD_ADD_STOCK, "stock_id", list.get(isSelected).getStockId()).getBytes());
                    } catch (Exception e) {
                        ToastUtil.showToast(StockActivity.this, "服务器可能断开连接了！");
                    }
                    isSelected = -1;
                }

            }
        });

        slStock.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dbCurd.delAllPlanData();

                sendAndSetting();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        slStock.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }

    private void sendAndSetting() {
        try{
            tcpHelper.sendData(Constant.getJsonData(Constant.KEY_STOCK, Constant.CMD_STOCK, Constant.KEY_GET_STOCK_DATA, "1").getBytes());
            tcpHelper.setReceiveDataCallback(new ReceiveDataCallback() {
                @Override
                public void onReceiveData(String receiveData) {
                    WaitDialog.immediatelyDismiss();

//                    {"stock":"cmd_stock", "id":"id","groupnum":"groupnum", "rownum":"rownum", "columnum":"columnum", "name":"name", "type":"type", "num":"num"}

                    String cmd_stock = null;
                    String id = null;
                    String groupnum = null;
                    String rownum = null;
                    String columnum = null;
                    String name = null;
                    String type = null;
                    String num = null;

                    try {
                        JSONObject jsonObject = new JSONObject(receiveData);
                        cmd_stock = jsonObject.getString("stock");
                        id = jsonObject.getString("id");
                        groupnum = jsonObject.getString("groupnum");
                        rownum = jsonObject.getString("rownum");
                        columnum = jsonObject.getString("columnum");
                        name = jsonObject.getString("name");
                        type = jsonObject.getString("type");
                        num = jsonObject.getString("num");

                    } catch (JSONException e) {
                        Log.e(TAG, "eee=" + e.toString());
                        e.printStackTrace();
                    }
                    if (cmd_stock != null && cmd_stock.equalsIgnoreCase(Constant.CMD_STOCK)) {
                        StockBean stcckBean = new StockBean();
                        stcckBean.setName(name);
                        stcckBean.setNum(num);
                        stcckBean.setColumNum(columnum);
                        stcckBean.setType(type);
                        stcckBean.setRowNum(rownum);
                        stcckBean.setGroupNum(groupnum);
                        stcckBean.setStockId(id);
                        dbCurd.addStockData(stcckBean);
                        list = dbCurd.selectAllStockData();
                        stockAdapter = new StockAdapter(StockActivity.this,list);
                        lvStock.setAdapter(stockAdapter);

                    }
                }
            });
        }catch (Exception e){
            ToastUtil.showToast(StockActivity.this, "服务器可能断开连接了！");
        }
    }
}
