package com.example.administrator.industry4app.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.administrator.industry4app.R;
import com.example.administrator.industry4app.util.TcpHelper;
import com.example.administrator.industry4app.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ibMain;//主控制界面
    private ImageButton ibAgv;//agv调度
    private ImageView ibStock;//库存管理
    private ImageView ibPlan;//生产计划任务管理
    private ImageView ibError;//故障查询
    private TcpHelper tcpHelper;
    private long exitTime = 0;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        tcpHelper = TcpHelper.getInstance();
        ibMain.setOnClickListener(this);
        ibAgv.setOnClickListener(this);
        ibStock.setOnClickListener(this);
        ibPlan.setOnClickListener(this);
        ibError.setOnClickListener(this);

    }

    private void findView() {
        ibMain = (ImageView) findViewById(R.id.ibMain);
        ibAgv = (ImageButton) findViewById(R.id.ibAgv);
        ibStock = (ImageView) findViewById(R.id.ibStock);
        ibPlan = (ImageView) findViewById(R.id.ibPlan);
        ibError = (ImageView) findViewById(R.id.ibError);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMain:
                startActivity(new Intent(MainActivity.this,MainControlActivity.class));
                break;
            case R.id.ibAgv:
                startActivity(new Intent(MainActivity.this,AgvActivity.class));
                break;
            case R.id.ibStock:
                startActivity(new Intent(MainActivity.this,StockActivity.class));
                break;
            case R.id.ibPlan:
                startActivity(new Intent(MainActivity.this,PlanActivity.class));
                break;
            case R.id.ibError:
                startActivity(new Intent(MainActivity.this,OPCActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtil.customToast(MainActivity.this, "再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                if(tcpHelper!=null){
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
