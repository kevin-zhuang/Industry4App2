package com.example.administrator.industry4app.db;

import com.example.administrator.industry4app.entity.PlanBean;
import com.example.administrator.industry4app.entity.StockBean;

import java.util.List;

/**
 * Created by Administrator
 * on 2016/6/22.
 */
interface DBCurdInterface {

    void addPlanData(PlanBean planBean);

    void delAllPlanData();

    List<PlanBean> selectAllPlanData();

//    boolean selectPlanDataById(String id);


    void addStockData(StockBean stockBean);

    void delAllStockData();

    List<StockBean> selectAllStockData();


}
